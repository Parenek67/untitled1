package sample.Windows;

import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import sample.DatabaseConnection;
import sample.Elements.AllTablesElement;
import sample.Elements.PermissionsElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class InsertWindow extends Application {
    private DatabaseConnection dc;
    private String tableName;
    private HashMap<String, String> values =  new HashMap<>();
    public InsertWindow(DatabaseConnection dc, String tableName) {
        this.dc = dc;
        this.tableName = tableName;
    }

    @Override
    public void start(Stage stage) throws Exception {
        ObservableList<String> list = dc.getColumnList(tableName);
        list.removeIf(str -> str.contains("Id"));
        list.removeIf(str -> str.contains("Tipe_id"));
        Button select = new Button("Добавить");
        TableView<String[]> table = new TableView<>();
        //table.setPrefWidth(250);
        table.setEditable(true);
        //table.setMinHeight(90);
        //table.setMaxHeight(65);
        table.setPrefHeight(55);
        for(int i = 0; i < list.size();i++){
            values.put(list.get(i), "");
            final int index = i;

                TableColumn<String[], String> newColumn = new TableColumn<>(list.get(i));
                newColumn.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()[index]));
                newColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                newColumn.setOnEditCommit(
                        new EventHandler<CellEditEvent<String[], String>>() {
                            @Override
                            public void handle(CellEditEvent<String[], String> t) {
                                System.out.println(t.getTableColumn().getText());
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
        stage.setTitle("Добавить запись");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        select.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String str = dc.insertValue(values, tableName, list);
                if(str.equals("Запись успешно добавлена")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(str);
                    alert.showAndWait();
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    VBox vb = new VBox();
                    TextArea textArea = new TextArea();
                    textArea.setText(str);
                    vb.getChildren().add(textArea);
                    alert.getDialogPane().setContent(vb);
                    alert.setHeaderText("Ошибка при добавлении записи");
                    alert.showAndWait();
                }

            }
        });

        select.requestFocus();
        VBox vb = new VBox(table, select);
        stage.setScene(new Scene(vb,620,300));
        stage.show();
    }
}
