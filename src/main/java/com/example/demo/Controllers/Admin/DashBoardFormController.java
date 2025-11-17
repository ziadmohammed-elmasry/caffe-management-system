package com.example.demo.Controllers.Admin;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardFormController {
    public MFXButton btnDashboard;
    public MFXButton btnMenuItems;
    public MFXButton btnCustomer;
    public MFXButton btnSales;
    public MFXButton btnInventory;
    public MFXButton btnLogout;
    public AnchorPane mainPane;
    public MFXButton btnEmployee;

    public void initialize() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Admin/dashBoardMainForm.fxml"));

        Pane registerPane = (Pane) fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().add(registerPane);

        //To Activate dashboard Button
        setButtonActive(btnDashboard);
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        setButtonActive(btnDashboard);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Admin/dashBoardMainForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().add(registerPane);

    }


    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        setButtonActive(btnEmployee);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Admin/EmployeeForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().add(registerPane);
    }

    public void btnMenuItemsOnAction(ActionEvent actionEvent) throws IOException {
        setButtonActive(btnMenuItems);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Admin/MenuLast.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().add(registerPane);

    }

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        setButtonActive(btnCustomer);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Admin/CustomerManagement.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().add(registerPane);
    }

    public void btnSalesOnAction(ActionEvent actionEvent) throws IOException {
        setButtonActive(btnSales);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Admin/SalesForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().add(registerPane);
    }

    public void btnInventoryOnAction(ActionEvent actionEvent) throws IOException {
        setButtonActive(btnInventory);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Admin/InventoryForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().add(registerPane);
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/Login.fxml"));
        Scene scene = new Scene(rootNode);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login");
        stage.show();

        //Close the Current Window
        Stage dashboard= (Stage) btnLogout.getScene().getWindow();
        dashboard.close();

    }
    private void setButtonActive(MFXButton activeButton) {

        //Remove Style
        btnDashboard.getStyleClass().removeAll("mfx-active-button","mfx-button");



        btnSales.getStyleClass().removeAll("mfx-active-button","mfx-button");
        btnEmployee.getStyleClass().removeAll("mfx-active-button","mfx-button");
        btnCustomer.getStyleClass().removeAll("mfx-active-button","mfx-button");
        btnLogout.getStyleClass().removeAll("mfx-active-button","mfx-button");
        btnMenuItems.getStyleClass().removeAll("mfx-active-button","mfx-button");
        btnInventory.getStyleClass().removeAll("mfx-active-button","mfx-button");

        //Add Style
        activeButton.getStyleClass().add("mfx-active-button");
    }
}
