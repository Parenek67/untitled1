package sample.Windows;


import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Pair;
import sample.DatabaseConnection;
import sample.Elements.AllTablesElement;
import javafx.scene.control.ComboBox;

import java.util.Arrays;
import java.util.HashMap;

public class SelectWindow extends Application {
    private DatabaseConnection dc;
    private String tableName;
    private HashMap<String, String> values =  new HashMap<>();
    public SelectWindow(DatabaseConnection dc, String tableName) {
        this.dc = dc;
        this.tableName = tableName;
    }

    @Override
    public void start(Stage stage) throws Exception {

        ObservableList<String> list = dc.getColumnList(tableName);

        //list.removeIf(str -> str.contains("Id"));
        //list.removeIf(str -> str.contains("Tipe_id"));
        Button select = new Button("Найти");
        TableView<String[]> table = new TableView<>();
        table.setEditable(true);
        table.setPrefHeight(55);
        for(int i = 0; i < list.size();i++){
            values.put(list.get(i), "");
            final int index = i;

            TableColumn<String[], String> newColumn = new TableColumn<>(list.get(i));
            newColumn.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()[index]));
            newColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            newColumn.setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<String[], String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<String[], String> t) {
                            //System.out.println(t.getTableColumn().getText());
                            values.put(t.getTableColumn().getText(), t.getNewValue());
                        }
                    }
            );
            table.getColumns().add(newColumn);
        }
        String[] mas = new String[list.size()];
        Arrays.fill(mas, "");

        table.getItems().add(mas);
        table.getStylesheets().add("tablestyle.css");
        table.setStyle("-fx-background-color: transparent;");
        stage.setTitle("Найти запись");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Label cb = new Label("Сортировать ");
            cb.setPadding(new Insets(4,0,0,4));
        ComboBox<String> combo = new ComboBox<>(list);
        ObservableList<String> descComboList = FXCollections.observableArrayList();
        descComboList.addAll("по возрастанию", "по убыванию");
        ComboBox<String> descCombo = new ComboBox<>(descComboList);
        HBox hb = new HBox(10);
        //hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(cb, combo, descCombo);

        Label usl = new Label("Условие: ");
            usl.setPadding(new Insets(4,22,0,4));
        ComboBox<String> combo2 = new ComboBox<>(list);
        ObservableList<String> uslComboList = FXCollections.observableArrayList();
        uslComboList.addAll(" > ", " >= ", " < ", " <= ", " = ");
        ComboBox<String> uslCombo = new ComboBox<>(uslComboList);
        HBox hb2 = new HBox(10);
        TextField tf = new TextField();
            tf.setMaxWidth(57);
        //hb.setAlignment(Pos.CENTER);
        hb2.getChildren().addAll(usl, combo2, uslCombo, tf);

        select.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String orderBy = "";
                if(combo.getValue() != null && descCombo.getValue() != null) {
                    if (descCombo.getValue().equals("по возрастанию")) {
                        orderBy = " order by " + combo.getValue();
                    } else
                        orderBy = " order by " + combo.getValue() + " desc";
                }

                Pair<String, ObservableList<AllTablesElement>> p= dc.selectValues(values, tableName, list, orderBy);
                ObservableList<AllTablesElement> str = p.getValue();
                if(str != null){
                    ResultWindow rw = new ResultWindow(list, str);
                    Stage s = new Stage();
                    try {
                        rw.start(s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    VBox vb = new VBox();
                    TextArea textArea = new TextArea();
                    textArea.setText(p.getKey());
                    vb.getChildren().add(textArea);
                    alert.getDialogPane().setContent(vb);
                    alert.setHeaderText("Ошибка при добавлении записи");
                    alert.showAndWait();
                }
            }
        });

        select.requestFocus();
        VBox vb = new VBox(10, table, hb, hb2, select);
        vb.setAlignment(Pos.TOP_LEFT);
        stage.setScene(new Scene(vb,620,300));
        stage.show();
    }
}
