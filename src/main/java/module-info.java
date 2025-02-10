module PasswordManagerInterface {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.bouncycastle.provider;
    requires java.sql;
    requires java.desktop;

    opens main;
    opens controller to javafx.fxml;
    exports controller;
    exports main;
}
