package controller;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import main.Main;
import model.User;
import util.DataBaseUtil;
import util.UserRepository;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoggedController implements Initializable {

    private static LoggedController instance;

    private User user;
    private ArrayList<ArrayList<String>> logins;

    public void setUser(User user) {
        this.user = user;
        this.logins = user.getLogins();
        setList();
    }

    @FXML
    private AnchorPane anchorInfo;

    @FXML
    private TextField serviceNameField;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextArea noteField;

    @FXML
    private ListView<String> listView;


    @Override
    public void initialize(URL arg0, ResourceBundle agr1) {
        instance = this;
        // Hide all
        showElements(false);
        listView.getSelectionModel().selectedItemProperty().addListener(this::changed);
    }

    public static LoggedController getInstance() {
        return instance;
    }

    @FXML
    private void goToMain() {
        showElements(false);
        serviceNameField.setText("");
        loginField.setText("");
        passwordField.setText("");
        noteField.setText("");
        Main.changeScreen("main");
    }

    @FXML
    private void goToCreateLogin() {
        showElements(false);
        Main.changeScreen("createLogin");
    }

    private int currentAcess;

    @FXML
    private void showElements(boolean show) {
        anchorInfo.setVisible(show);
        anchorInfo.setManaged(show);
    }


    public void setList() {
        listView.getItems().clear();
        for (ArrayList<String> arrayListLogin : logins) {
            listView.getItems().add(arrayListLogin.getFirst());
        }
    }

    public void setElement(int index) {
        showElements(true);

        serviceNameField.setText(logins.get(index).get(0));
        loginField.setText(logins.get(index).get(1));
        passwordField.setText(logins.get(index).get(2));
        noteField.setText(logins.get(index).get(3));
    }

    public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
        if (listView.getSelectionModel().getSelectedIndex() != -1) {
            notEditable();
            currentAcess = listView.getSelectionModel().getSelectedIndex();
            setElement(currentAcess);
        }
    }

    public void notEditable() {
        serviceNameField.setEditable(false);
        loginField.setEditable(false);
        passwordField.setEditable(false);
        noteField.setEditable(false);
    }

    @FXML
    private void editServiceName() {
        serviceNameField.setEditable(!serviceNameField.isEditable());
    }
    @FXML
    private void editLogin() {
        loginField.setEditable(!loginField.isEditable());
    }
    @FXML
    private void editPassword() {
        passwordField.setEditable(!passwordField.isEditable());
    }
    @FXML
    private void editNote() {
        noteField.setEditable(!noteField.isEditable());
    }

    private int getIdLogin() {
        return Integer.parseInt(logins.get(listView.getSelectionModel().getSelectedIndex()).get(4));
    }

    @FXML
    private void save() {
        boolean changed = false;
        if (Objects.equals(serviceNameField.getText(), "") || Objects.equals(loginField.getText(), "") || Objects.equals(passwordField.getText(), "")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The fields cannot are empty");
            alert.showAndWait();
        } else if (listView.getSelectionModel().getSelectedIndex() != -1) {
            if (!logins.get(listView.getSelectionModel().getSelectedIndex()).get(0).equals(serviceNameField.getText())) {
                DataBaseUtil.update(user.getKeyLogin(), serviceNameField.getText(), "service_name", getIdLogin());
                changed = true;
            }
            if (!logins.get(listView.getSelectionModel().getSelectedIndex()).get(1).equals(loginField.getText())) {
                DataBaseUtil.update(user.getKeyLogin(), loginField.getText(), "login", getIdLogin());
                changed = true;
            }
            if (!logins.get(listView.getSelectionModel().getSelectedIndex()).get(2).equals(passwordField.getText())) {
                DataBaseUtil.update(user.getKeyLogin(), passwordField.getText(), "password", getIdLogin());
                changed = true;
            }
            if (!logins.get(listView.getSelectionModel().getSelectedIndex()).get(3).equals(noteField.getText())) {
                DataBaseUtil.update(user.getKeyLogin(), noteField.getText(), "note", getIdLogin());
                changed = true;
            }
            if (changed) {
                notEditable();
                setUser(UserRepository.loadLogins(user.getID(), user.getKeyLogin()));
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Sucess");
                alert.setHeaderText("The data was updated successfully");
                alert.showAndWait();
                showElements(false);
            }
        }
    }

    @FXML
    private void delete() {
        DataBaseUtil.deleteLogin(getIdLogin());
        setUser(UserRepository.loadLogins(user.getID(), user.getKeyLogin()));
        showElements(false);
    }

    @FXML
    private void hide() {
        if (!Objects.equals(serviceNameField.getText(), "")) {
            showElements(!anchorInfo.isVisible());
        }
    }
}
