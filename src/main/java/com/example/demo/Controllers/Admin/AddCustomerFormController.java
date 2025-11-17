package com.example.demo.Controllers.Admin;

import com.example.demo.Controllers.Customer.Customer;
import com.example.demo.Models.CustomerModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Setter;

import java.util.regex.Pattern;

import static com.example.demo.Models.CustomerModel.generateNextCustomerId;


public class AddCustomerFormController {


    public MFXButton btnCancel;
    public MFXTextField txtName;
    public MFXButton btnAddCustomer;
    public Text txtCustomerId;
    public MFXTextField txtAddress;
    public MFXTextField txtMobileNo;
    public MFXTextField txtPassword;

    @Setter
    private CustomerFormController customerFormController;

    private String cusId=generateNextCustomerId();


    public void initialize() {
        txtCustomerId.setText(cusId);
    }

    public void btnAddCustomer(ActionEvent actionEvent) {
        boolean isCustomerValidated = validateCustomer();
        System.out.println(isCustomerValidated);

        if (!isCustomerValidated) {
            return;
        }

        String cusId = txtCustomerId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String mobileNo = txtMobileNo.getText();

        try {
            boolean isUpdated = CustomerModel.addCustomer(new Customer(cusId, name, address, mobileNo));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Added").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        customerFormController.loadAllCustomers();
    }


    public void btnCancelOnAction(ActionEvent actionEvent) {
        Stage btnCustomerStage = (Stage) btnCancel.getScene().getWindow();
        btnCustomerStage.close();

    }


    private boolean validateCustomer() {

        String name = txtName.getText();
        boolean isNameValid = Pattern.matches("[A-Za-z]{3,}", name);
        if (!isNameValid) {
            txtName.requestFocus();
            txtName.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtName.getStyleClass().removeAll("mfx-text-field-error");

        String address = txtAddress.getText();
        boolean isAddressValid = Pattern.matches("[A-Za-z]{3,}", address);
        if (!isAddressValid) {
            txtAddress.requestFocus();
            txtAddress.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtAddress.getStyleClass().removeAll("mfx-text-field-error");

        String mobileNo = txtMobileNo.getText();
        boolean isMobileNoValid = Pattern.matches("\\d{10}", mobileNo);
        if (!isMobileNoValid) {
            txtMobileNo.requestFocus();
            txtMobileNo.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtMobileNo.getStyleClass().removeAll("mfx-text-field-error");

        return true;

    }



}
