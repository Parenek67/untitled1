package sample.Windows;


import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.DatabaseConnection;
import sample.Elements.PermissionsElement;


public class ProfileWindow extends Application {
    private DatabaseConnection dc;

    public ProfileWindow(DatabaseConnection dc){
        this.dc = dc;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Label lbl = new Label("Пользователь: " + dc.getName());
            lbl.setFont(new Font(14));
        Label lbl2 = new Label("Пароль:");
            lbl2.setFont(new Font(14));
        Label lbl3 = new Label("Разрешения:");
            lbl3.setFont(new Font(14));
        PasswordField pf = new PasswordField();
            pf.setMaxWidth(200);
            pf.setText(dc.getPassword());
            pf.setEditable(false);
        TextField passLbl = new TextField(dc.getPassword());
            passLbl.setMaxWidth(200);
            passLbl.setVisible(false);
            passLbl.setEditable(false);
        CheckBox cb = new CheckBox("Показать пароль");
            cb.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if(!passLbl.isVisible()){
                        passLbl.setVisible(true);
                        pf.setVisible(false);
                    }
                    else{
                        passLbl.setVisible(false);
                        pf.setVisible(true);
                    }
                }
            });

        ObservableList<PermissionsElement> list = dc.getUserPermissions(dc.getName());
        TableView<PermissionsElement> table = new TableView<>(list);
        table.setPrefWidth(350);
        table.setEditable(true);
        table.setPrefHeight(200);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<PermissionsElement, String> entityColumn = new TableColumn<>("Сущность");
        entityColumn.setCellFactory(TextFieldTableCell.<PermissionsElement>forTableColumn());
        entityColumn.setMaxWidth(70);
        entityColumn.setMinWidth(70);
        entityColumn.setCellValueFactory(new PropertyValueFactory<>("entity_name"));
        table.getColumns().add(entityColumn);
        TableColumn<PermissionsElement, String> subentityColumn = new TableColumn<>("Подсущность");
        subentityColumn.setMaxWidth(90);
        subentityColumn.setMinWidth(90);
        subentityColumn.setCellValueFactory(new PropertyValueFactory<>("subentity_name"));
        table.getColumns().add(subentityColumn);
        TableColumn<PermissionsElement, String> nameColumn = new TableColumn<>("Название");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("permission_name"));
        table.getColumns().add(nameColumn);

        StackPane sp = new StackPane(pf, passLbl);
        stage.setTitle("Аккаунт");
        HBox hb = new HBox(10, lbl2, sp);
        VBox vb2 = new VBox(2, lbl3, table);
        VBox vb = new VBox(10,lbl,hb, cb, vb2);
        vb.setPadding(new Insets(10));
        stage.setScene(new Scene(vb));
        stage.setResizable(false);
        stage.show();
    }
}
