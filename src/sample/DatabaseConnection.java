package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import sample.Elements.AllTablesElement;
import sample.Elements.PermissionsElement;
import sample.Elements.SWElement;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.IntFunction;

public class DatabaseConnection {
    private int connectionSuccess = 0;
    private Connection _connection;
    String connectionUrl = "";
    private String name;
    private String password;
    public void connect(String name, String password){
        this.name = name;
        this.password = password;
        connectionUrl =
                "jdbc:sqlserver://MSI\\SQLEXPRESS;"
                        + "database=Newspaper;"
                        + "user="+name+";"
                        + "password="+password+";"
                        + "encrypt=false;"
                        + "trusted_connection=true;"
                        + "trustServerCertificate=true;"
                        + "loginTimeout=30;";

        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            _connection = connection;
            connectionSuccess = 1;
            aaa();
        }
        catch (SQLException e) {
            //e.printStackTrace();
            connectionSuccess = e.getErrorCode();
        }
    }

    public int getConnectionSuccess() {
        return connectionSuccess;
    }

    public ObservableList<String> getTables() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            Statement statement = connection.createStatement();
            {
                String selectSql = "SELECT Name FROM dbo.sysobjects WHERE (xtype = 'U')";
                ResultSet resultSet = statement.executeQuery(selectSql);

                // Print results from select statement
                while (resultSet.next()) {
                    list.add(resultSet.getString("Name"));
                }
            }
            return list;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<SWElement> getTableStructure(String tableName){
        ObservableList<SWElement> list = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            Statement statement = connection.createStatement();
            {
                String selectSql = "SELECT COLUMN_NAME AS [Имя столбца]," +
                        "DATA_TYPE AS [Тип данных столбца]," +
                        "IS_NULLABLE AS [Значения NULL] " +
                        "FROM INFORMATION_SCHEMA.COLUMNS " +
                        "WHERE table_name='"+tableName+"'";
                ResultSet resultSet = statement.executeQuery(selectSql);

                // Print results from select statement
                while (resultSet.next()) {
                    SWElement swe = new SWElement(resultSet.getString("Имя столбца"),
                            resultSet.getString("Тип данных столбца"),
                            resultSet.getString("Значения NULL"));
                    list.add(swe);
                }
            }
            return list;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Pair<ArrayList<String>, ObservableList<AllTablesElement>> getTableReview(String tableName, String query){
        ObservableList<AllTablesElement> list;
        ArrayList<String> columnList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            Statement statement = connection.createStatement();
            {
                String selectSql = "select COLUMN_NAME " +
                        "from INFORMATION_SCHEMA.COLUMNS " +
                        "where TABLE_NAME='"+tableName+"'";
                ResultSet resultSet = statement.executeQuery(selectSql);

                while (resultSet.next()) {
                    columnList.add(resultSet.getString("COLUMN_NAME"));
                }

                selectSql = query;
                resultSet = statement.executeQuery(selectSql);
                list = getSelectedTableElements(resultSet, tableName, columnList);
            }
            return new Pair<>(columnList, list);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<PermissionsElement> getUserPermissions(String username){
        ObservableList<PermissionsElement> list = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            Statement statement = connection.createStatement();
            {
                String selectSql = "execute as Login = '" + username + "';" +
                        "select * from sys.fn_my_permissions(NULL, NULL)";
                ResultSet resultSet = statement.executeQuery(selectSql);

                while (resultSet.next()) {
                    PermissionsElement pe = new PermissionsElement(resultSet.getString("permission_name"),
                            resultSet.getString("entity_name"),
                            resultSet.getString("subentity_name"));
                    list.add(pe);
                }
            }
            return list;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<String> getColumnList(String tableName) throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            Statement statement = connection.createStatement();
            {
                String selectSql = "select COLUMN_NAME " +
                        "from INFORMATION_SCHEMA.COLUMNS " +
                        "where TABLE_NAME='" + tableName + "'";
                ResultSet resultSet = statement.executeQuery(selectSql);

                while (resultSet.next()) {
                    list.add(resultSet.getString("COLUMN_NAME"));
                }
            }
            return list;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String insertValue(HashMap<String, String> values, String tableName, ObservableList<String> list){
        StringBuffer str = new StringBuffer();

        for(int i = 0;i < list.size()-1;i++){
            if(values.get(list.get(i)).matches("\\d+")){
                str.append(values.get(list.get(i))).append(", ");
            }
            else
                str.append("'").append(values.get(list.get(i))).append("', ");
        }
        if(values.get(list.get(list.size()-1)).matches("\\d+")) {
            str.append(values.get(list.get(list.size() - 1)));
        }
        else
            str.append("'").append(values.get(list.get(list.size() - 1))).append("'");
        System.out.println(str);
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            Statement statement = connection.createStatement();
            {
                String selectSql = "INSERT "+tableName+" VALUES ("+str+")";
                int resultSet = statement.executeUpdate(selectSql);
                System.out.println(resultSet);
            }
            return "Запись успешно добавлена";
        }
        catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public Pair<String,ObservableList<AllTablesElement>> selectValues(HashMap<String, String> values, String tableName,
                                                         ObservableList<String> list, String orderBy){
        Pair<String, ObservableList<AllTablesElement>> resultList;
        ObservableList<AllTablesElement> ate;
        StringBuffer str = new StringBuffer();
        for(int i = 0;i < list.size()-1;i++){
            str.append(list.get(i)).append(", ");
        }
        str.append(list.get(list.size()-1));
        //System.out.println(str);

        StringBuffer str2 = new StringBuffer();
        for(int i = 0;i < list.size()-1;i++){
            if(values.get(list.get(i)).matches("\\d+") && !values.get(list.get(i)).isEmpty() ){
                str2.append(list.get(i)).append(" = ").append(values.get(list.get(i))).append(", ");
            }
            else if (!values.get(list.get(i)).isEmpty())
            str2.append(list.get(i)).append(" = '").append(values.get(list.get(i))).append("', ");
        }
        if(values.get(list.get(list.size()-1)).matches("\\d+") && !values.get(list.get(list.size()-1)).isEmpty()) {
            str2.append(list.get(list.size() - 1)).append(" = ").append(values.get(list.get(list.size() - 1)));
        }
        else if(!values.get(list.get(list.size()-1)).isEmpty())
            str2.append(list.get(list.size() - 1)).append(" = '").append(values.get(list.get(list.size() - 1))).append("'");
        //System.out.println(str2);
        String query = "select "+str+" from "+tableName+" where "+str2 + orderBy;
        //System.out.println(query);

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            Statement statement = connection.createStatement();
            {
                //String selectSql = "INSERT "+tableName+" VALUES ("+str+")";
                ResultSet resultSet = statement.executeQuery(query);
                ArrayList<String> columns = new ArrayList<>();
                columns.addAll(list);
                ate = getSelectedTableElements(resultSet, tableName, columns);
            }
            //return resultList;
            return new Pair<>(null, ate);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return new Pair<>(e.getMessage(), null);
        }
    }


    public void aaa() throws SQLException {
        Statement statement = _connection.createStatement();
        {
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * from Position";
            ResultSet resultSet = statement.executeQuery(selectSql);

            // Print results from select statement
            while (resultSet.next()) {
                System.out.println(resultSet.getString(2) + " " + resultSet.getString(3));
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public ObservableList<AllTablesElement> getSelectedTableElements(ResultSet resultSet, String tableName,
                                                    ArrayList<String> columnList) throws SQLException {
        ObservableList<AllTablesElement> list = FXCollections.observableArrayList();
        while (resultSet.next()) {
            AllTablesElement ate = new AllTablesElement();
            switch (tableName){
                case "Article":
                    ate.createArticle(resultSet.getString(columnList.get(0)),
                            resultSet.getString(columnList.get(1)),
                            resultSet.getString(columnList.get(2)),
                            resultSet.getString(columnList.get(3)));
                    break;
                case "DistributionPoint":
                    ate.createDistributionPoint(resultSet.getString(columnList.get(0)),
                            resultSet.getString(columnList.get(1)),
                            resultSet.getString(columnList.get(2)),
                            resultSet.getString(columnList.get(3)));
                    break;
                case "Employee":
                    ate.createEmployee(resultSet.getString(columnList.get(0)),
                            resultSet.getString(columnList.get(1)),
                            resultSet.getString(columnList.get(2)),
                            resultSet.getString(columnList.get(3)),
                            resultSet.getString(columnList.get(4)),
                            resultSet.getString(columnList.get(5)));
                    break;
                case "Machine":
                    ate.createMachine(resultSet.getString(columnList.get(0)),
                            resultSet.getString(columnList.get(1)),
                            resultSet.getString(columnList.get(2)),
                            resultSet.getString(columnList.get(3)),
                            resultSet.getString(columnList.get(4)));
                    break;
                case "Plant":
                    ate.createPlant(resultSet.getString(columnList.get(0)),
                            resultSet.getString(columnList.get(1)),
                            resultSet.getString(columnList.get(2)),
                            resultSet.getString(columnList.get(3)));
                    break;
                case "Position":
                    ate.createPosition(resultSet.getString(columnList.get(0)),
                            resultSet.getString(columnList.get(1)),
                            resultSet.getString(columnList.get(2)),
                            resultSet.getString(columnList.get(3)));
                    break;
                case "Product":
                    ate.createProduct(resultSet.getString(columnList.get(0)),
                            resultSet.getString(columnList.get(1)),
                            resultSet.getString(columnList.get(2)),
                            resultSet.getString(columnList.get(3)),
                            resultSet.getString(columnList.get(4)));
                    break;
                case "Production":
                    ate.createProduction(resultSet.getString(columnList.get(0)),
                            resultSet.getString(columnList.get(1)),
                            resultSet.getString(columnList.get(2)),
                            resultSet.getString(columnList.get(3)));
                    break;
                case "ProductType":
                    ate.createProductType(resultSet.getString(columnList.get(0)),
                            resultSet.getString(columnList.get(1)),
                            resultSet.getString(columnList.get(2)),
                            resultSet.getString(columnList.get(3)),
                            resultSet.getString(columnList.get(4)));
                    break;
                case "Provider":
                    ate.createProvider(resultSet.getString(columnList.get(0)),
                            resultSet.getString(columnList.get(1)),
                            resultSet.getString(columnList.get(2)),
                            resultSet.getString(columnList.get(3)),
                            resultSet.getString(columnList.get(4)));
                    break;
                case "Region":
                    ate.createRegion(resultSet.getString(columnList.get(0)),
                            resultSet.getString(columnList.get(1)));
                    break;
            }
            list.add(ate);
        }
        return list;
    }
}
