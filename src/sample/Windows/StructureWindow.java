package sample.Windows;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.DatabaseConnection;
import sample.Elements.SWElement;


public class StructureWindow extends Application {
    private DatabaseConnection dc;
    private String tableName;
    public StructureWindow(DatabaseConnection dc, String tableName){
        this.dc = dc;
        this.tableName = tableName;
    }

    @Override
    public void start(Stage stage){
        ObservableList<SWElement> list = dc.getTableStructure(tableName);
        TableView<SWElement> table = new TableView<>(list);
        table.setPrefWidth(250);
        table.setPrefHeight(200);

        TableColumn<SWElement, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        table.getColumns().add(nameColumn);

        TableColumn<SWElement, String> typeColumn = new TableColumn<>("Data type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        table.getColumns().add(typeColumn);

        TableColumn<SWElement, String> isNullColumn = new TableColumn<>("Null");
        isNullColumn.setCellValueFactory(new PropertyValueFactory<>("isNull"));
        table.getColumns().add(isNullColumn);
        stage.setTitle("Структура таблицы");
        VBox vb = new VBox(table);
        stage.setScene(new Scene(vb));
        stage.show();
        stage.sizeToScene();
    }
}
