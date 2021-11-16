package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.Windows.*;

import java.io.IOException;

public class Main extends Application {
    private final DatabaseConnection dc = new DatabaseConnection();
    Label tableName = new Label("Article");
    @Override
    public void start(Stage primaryStage) throws Exception{
        loginDialog(primaryStage);
        ObservableList<String> langs = dc.getTables();
        //ObservableList<String> langs = FXCollections.observableArrayList("Java", "JavaScript", "C#", "Python");
        ListView<String> list = new ListView<>(langs);
        MultipleSelectionModel<String> langsSelectionModel = list.getSelectionModel();
        // устанавливаем слушатель для отслеживания изменений
        GridPane gp = createGP();
        langsSelectionModel.selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                tableName.setText(t1);
            }
        });
        MenuBar menuBar = new MenuBar();
        Menu profile = new Menu("Аккаунт");
        MenuItem about = new MenuItem("Подробнее");
        MenuItem exit = new MenuItem("Выйти");
        profile.getItems().addAll(about, exit);
        menuBar.getMenus().add(profile);
        about.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage s = new Stage();
                ProfileWindow pw = new ProfileWindow(dc);
                try {
                    pw.start(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.close();
                try {
                    loginDialog(primaryStage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        BorderPane bp = new BorderPane(gp, menuBar,null,null,list);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(bp, 900, 500));
    }

    private void loginDialog(Stage stage) throws IOException {
        Stage newWindow = new Stage();
            newWindow.setTitle("Название приложения");
        Button btn = new Button("ОК");
        Label lbl = new Label("Авторизация");
            lbl.setFont(new Font(18));
        TextField name = new TextField();
            name.setMaxSize(150, 50);
            name.setPromptText("Имя пользователя");
        PasswordField pf = new PasswordField();
            pf.setMaxSize(150, 50);
            pf.setPromptText("Пароль");
        TextField passLbl = new TextField();
            passLbl.setEditable(false);
            passLbl.setMaxSize(150, 50);
            passLbl.setVisible(false);
        StackPane sp = new StackPane(pf, passLbl);
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
        pf.textProperty().addListener( (ov,oldV,newV) -> {
            passLbl.setText(newV);
        } );


        VBox vb = new VBox(10, lbl, name, sp, cb, btn);
            vb.setAlignment(Pos.CENTER);
        newWindow.setScene(new Scene(vb, 300, 275));
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!name.getText().isEmpty() && !pf.getText().isEmpty()) {
                    dc.connect(name.getText(), pf.getText());
                    int res = dc.getConnectionSuccess();
                    if (res == 1) {
                        newWindow.close();
                        stage.show();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Ошибка!");
                        alert.setHeaderText("Ошибка при авторизациии");
                        alert.setContentText("Код ошибки: " + res);
                        alert.showAndWait();
                    }
                }
            }
        });
        newWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
            }
        });
        newWindow.showAndWait();
    }

    private GridPane createGP(){
        GridPane gp = new GridPane();
        gp.setPadding(new Insets(10));
            tableName.setFont(new Font(20));
            tableName.setLineSpacing(300);
        Button structure = new Button("Структура");
        structure.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                StructureWindow sw = new StructureWindow(dc, tableName.getText());
                Stage newWindow = new Stage();
                try {
                    sw.start(newWindow);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Button review = new Button("Просмотр");
        review.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ReviewWindow rw = new ReviewWindow(dc, tableName.getText());
                Stage s = new Stage();
                try {
                    rw.start(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Label options = new Label("Опции");
            options.setFont(new Font(20));
        Button insert = new Button("Добавить запись");
        insert.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage newWindow = new Stage();
                InsertWindow iw = new InsertWindow(dc, tableName.getText());
                try {
                    iw.start(newWindow);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Button select = new Button("Найти запись");
        select.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage newWindow = new Stage();
                SelectWindow uw = new SelectWindow(dc, tableName.getText());
                try {
                    uw.start(newWindow);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Button join = new Button("Соединить с...");
        gp.add(tableName,0,0);
        gp.add(structure,0,1);
        gp.add(review,1,1);
        gp.add(options,0,2);
        gp.add(insert,0,3);
        gp.add(select,1,3);
        gp.add(join,2,3);
        gp.setHgap(20);
        gp.setVgap(20);
        return gp;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
