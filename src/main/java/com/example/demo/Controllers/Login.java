package com.example.demo.Controllers;

import com.example.demo.Controllers.Admin.Admin;
import com.example.demo.Models.Users;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Login {
    public TextField usernameField;
    public PasswordField passwordField;
    public Button loginButton;
    public Label error_msg;

    public void handleLogin(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();


        try {
            int result = Users.checkAdminLogin(username, password);
            if (result == 1) {
                System.out.println("Admin Login Successful");
                Admin admin = new Admin(username, password);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Admin/DashBoardControl.fxml"));
                Parent parent = fxmlLoader.load();
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();

                Stage loginStage = (Stage) usernameField.getScene().getWindow();
                loginStage.close();

            } else if (result == 2) {
                System.out.println("Incorrect Admin Password");
                error_msg.setText("Incorrect Password");
            } else if (result == 0) {
                result = Users.checkEmployeeLogin(username, password);
                if (result == 1) {
                    System.out.println("Employee Login Successful");
                    Users.username = username;
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Employee/DashBoardControl.fxml"));
                    Parent parent = fxmlLoader.load();
                    Scene scene = new Scene(parent);
                    Stage stage = new Stage();
                    MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.show();

                    Stage loginStage = (Stage) usernameField.getScene().getWindow();
                    loginStage.close();

                } else if (result == 2) {
                    System.out.println("Incorrect Employee Password");
                    error_msg.setText("Incorrect Password");
                } else {
                    System.out.println("User not found");
                    error_msg.setText("User not found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            error_msg.setText("An error occurred");
        }
    }

    public void handleForgotPassword(ActionEvent actionEvent) {
        System.out.println("Forgot Password");
    }
}