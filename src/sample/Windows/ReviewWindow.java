package sample.Windows;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import sample.Elements.AllTablesElement;
import sample.DatabaseConnection;

import java.util.ArrayList;

public class ReviewWindow extends Application {
    private DatabaseConnection dc;
    private String tableName;
    public ReviewWindow(DatabaseConnection dc, String tableName){
        this.dc = dc;
        this.tableName = tableName;
    }

    @Override
    public void start(Stage stage){
        String query = "select * from " + tableName;
        Pair<ArrayList<String>, ObservableList<AllTablesElement>> p = dc.getTableReview(tableName, query);
        ObservableList<AllTablesElement> list = p.getValue();
        ArrayList<String> columnList = p.getKey();

        TableView<AllTablesElement> table = new TableView<>(list);
        table.setPrefWidth(250);
        table.setPrefHeight(200);
        for(int i = 0; i < columnList.size();i++){
            TableColumn<AllTablesElement, String> newColumn = new TableColumn<>(columnList.get(i));
            newColumn.setCellValueFactory(new PropertyValueFactory<>(columnList.get(i)));
            table.getColumns().add(newColumn);
        }
        stage.setTitle("Просмотр таблицы");
        VBox vb = new VBox(table);
        stage.setScene(new Scene(vb));
        stage.show();
    }
}
