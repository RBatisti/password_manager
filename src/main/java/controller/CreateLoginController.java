package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.Main;
import util.UserRepository;
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
        UserRepository.createLogin(Main.getKeyLogin(), serviceNameField.getText(), loginField.getText(), passwordField.getText(), noteField.getText(), Main.getID());
        LoggedController.getInstance().setUser(UserRepository.loadLogins(Main.getID(), Main.getKeyLogin()));
        serviceNameField.setText("");
        loginField.setText("");
        passwordField.setText("");
        noteField.setText("");
        goToLogged();
    }
}
