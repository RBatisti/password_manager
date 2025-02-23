package controller;

import Session.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.Main;
import util.UserRepository;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class CreateLoginController {

    @FXML
    private TextField serviceNameField;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextArea noteField;

    @FXML
    public void goToLogged() {
        Main.changeScreen("logged");
    }

    @FXML
    public void create() {
        if (Objects.equals(serviceNameField.getText(), "") || Objects.equals(loginField.getText(), "") || Objects.equals(passwordField.getText(), "")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Fields obrigatories cannot be empty");
            alert.showAndWait();
            return;
        }
        UserRepository.createLogin(SessionManager.getInstance().getUser().getKeyLogin(), serviceNameField.getText(), loginField.getText(), passwordField.getText(), noteField.getText(), SessionManager.getInstance().getUser().getID());
        SessionManager.getInstance().setUser(UserRepository.getUser(SessionManager.getInstance().getUser().getID(), SessionManager.getInstance().getUser().getKeyLogin()));
        serviceNameField.setText("");
        loginField.setText("");
        passwordField.setText("");
        noteField.setText("");
        goToLogged();
    }

    @FXML
    public void goToGitHub() {
        try {
            Desktop.getDesktop().browse(java.net.URI.create("https://github.com/RBatisti/password_manager"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
