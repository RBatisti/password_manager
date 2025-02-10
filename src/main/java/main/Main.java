package main;

import controller.LoggedController;
import dao.DB;
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
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;

            // Carregando as telas
            mainScene = loadScene("/view/mainView.fxml");
            loggedScene = loadScene("/view/loggedView.fxml");
            createUserScene = loadScene("/view/createView.fxml");
            createLoginScene = loadScene("/view/createLoginView.fxml");

            // Título
            primaryStage.setTitle("Password Manager");

            // Adicionando o ícone
            Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/key.png")).toExternalForm());
            primaryStage.getIcons().add(image);

            // Configura a janela
            primaryStage.setScene(mainScene);

            // Define o tamanho da janela
            primaryStage.setMinWidth(500);
            primaryStage.setMinHeight(600);
            primaryStage.setMaxWidth(500);
            primaryStage.setMaxHeight(600);

            // Exibe a janela
            primaryStage.show();

            // Encerrar a conexão com o banco de dados
            Runtime.getRuntime().addShutdownHook(new Thread(DB::closeConnection));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Scene loadScene(String path) throws Exception {
        Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
        return new Scene(fxml, 500, 600);
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
