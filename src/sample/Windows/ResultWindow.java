package sample.Windows;


import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Elements.AllTablesElement;

import java.util.ArrayList;

public class ResultWindow extends Application {
    ObservableList<String> columnList;
    ObservableList<AllTablesElement> list;
    public ResultWindow(ObservableList<String> columnList, ObservableList<AllTablesElement> list){
        this.columnList = columnList;
        this.list = list;
    }
    @Override
    public void start(Stage stage) throws Exception {
        TableView<AllTablesElement> table = new TableView<>(list);
        //table.setPrefHeight(55);
        //table.getStylesheets().add("tablestyle.css");
        table.setStyle("-fx-background-color: transparent;");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        for(int i = 0; i < columnList.size();i++){
            TableColumn<AllTablesElement, String> newColumn = new TableColumn<>(columnList.get(i));
            newColumn.setCellValueFactory(new PropertyValueFactory<>(columnList.get(i)));
            table.getColumns().add(newColumn);
        }
        stage.setTitle("Просмотр таблицы");
        VBox vb = new VBox(table);
        stage.setScene(new Scene(vb,620,300));
        stage.show();
    }
}
