package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import javafx.fxml.FXML;
import util.DataBaseUtil;
import util.SCryptManager;
import util.UserRepository;

import java.util.Objects;


public class MainController {

    private Stage stage;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;


    @FXML
    public void goToLogged() {
        if (Objects.equals(emailField.getText(), "") || Objects.equals(passwordField.getText(), "")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Fields cannot be empty");
            alert.showAndWait();
            return;
        }

        int id = DataBaseUtil.getID(emailField.getText());
        if (id != -1) {
            if (DataBaseUtil.checkLogin(id, Objects.requireNonNull(UserRepository.passwordHash(passwordField.getText(), id)))) {
                byte[] keyLogin = SCryptManager.generateLogin(passwordField.getText(), DataBaseUtil.getSaltLogin(id));
                Main.setUser(id, DataBaseUtil.getLogins(id), keyLogin);

                LoggedController.getInstance().setUser(UserRepository.loadLogins(id, keyLogin));
                Main.changeScreen("logged");
                emailField.setText("");
                passwordField.setText("");
                return;
            }
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Credentials are invalid");
        alert.showAndWait();
    }

    @FXML
    public void goToCreate() {
        Main.changeScreen("create");
    }
}