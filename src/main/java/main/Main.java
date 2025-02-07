package main;

import controller.LoggedController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    private static Stage stage;

    private static Scene mainScene;
    private static Scene loggedScene;
    private static Scene createUserScene;
    private static Scene createLoginScene;


    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        // Carregando as telas
        Parent fxmlMain = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/mainView.fxml")));
        mainScene = new Scene(fxmlMain, 500, 600);
        Parent fxmlLogged = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/loggedView.fxml")));
        loggedScene = new Scene(fxmlLogged, 500, 600);
        Parent fxmlCreateUser = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/createView.fxml")));
        createUserScene = new Scene(fxmlCreateUser, 500, 600);
        Parent fxmlCreateLogin = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/createLoginView.fxml")));
        createLoginScene = new Scene(fxmlCreateLogin, 500, 600);

        // Título
        primaryStage.setTitle("Password Manager");

        // Adicionando o ícone
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/key.png")).toExternalForm());
        primaryStage.getIcons().add(image);

        // Configura a janela
        primaryStage.setScene(mainScene);

        // Define o tamanho mínimo da janela
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(600);
        primaryStage.setMaxWidth(500);
        primaryStage.setMaxHeight(600);

        // Exibe a janela
        primaryStage.show();

    }


    public static void changeScreen(String src) {
        switch (src) {
            case "main":
                stage.setScene(mainScene);
                break;

            case "logged":
                LoggedController.getInstance().setList();
                stage.setScene(loggedScene);
                break;

            case "create":
                stage.setScene(createUserScene);
                break;

            case "createLogin":
                stage.setScene(createLoginScene);
                break;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
