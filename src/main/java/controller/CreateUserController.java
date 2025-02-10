package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.Main;
import util.DataBaseUtil;
import util.UserRepository;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class CreateUserController {

    @FXML
    private TextField email;

    @FXML
    private PasswordField passwordField1;

    @FXML
    private PasswordField passwordField2;

    @FXML
    private void goToMain() {
        Main.changeScreen("main");
    }

    @FXML
    private void create() {
        if (Objects.equals(passwordField1.getText(), "") || Objects.equals(passwordField2.getText(), "") || Objects.equals(email.getText(), "")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("fields cannot be empty");
            alert.showAndWait();
        } else if (!passwordField1.getText().equals(passwordField2.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The passwords not are iquals");
            alert.showAndWait();
        } else if (DataBaseUtil.existEmail(email.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The email already exist");
            alert.showAndWait();
        } else {
            UserRepository.createUser(email.getText(), passwordField1.getText());
            email.setText("");
            passwordField1.setText("");
            passwordField2.setText("");
            goToMain();
        }
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
