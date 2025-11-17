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


public class UpdateCustomerFormController {
    public MFXButton btnUpdateCustomer;
    public MFXButton btnCancel;
    public MFXTextField txtName;
    public MFXTextField txtAddress;
    public Text txtCustomerId;
    public MFXTextField txtMobileNo;

    @Setter
    private String cusId;
    @Setter
    private CustomerFormController customerFormController;


    public void btnUpdateCustomer(ActionEvent actionEvent) {
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
            boolean isUpdated = CustomerModel.updateCustomer(new Customer(cusId,name,address,mobileNo));
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        customerFormController.loadAllCustomers();
    }

    private boolean validateCustomer() {
        String Name = txtName.getText();
        boolean isNameValid = Pattern.matches("[A-Za-z]{3,}",Name);
        if (!isNameValid){
            txtName.requestFocus();
            txtName.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtName.getStyleClass().removeAll("mfx-text-field-error");


        String Address = txtAddress.getText();
        boolean isAddressValid = Pattern.matches("[A-Za-z]{3,}",Address);
        if (!isAddressValid){
            txtAddress.requestFocus();
            txtAddress.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtAddress.getStyleClass().removeAll("mfx-text-field-error");


        String mobileNo = txtMobileNo.getText();
        boolean isMobileNoValid = Pattern.matches("\\d{10}",mobileNo);
        if (!isMobileNoValid){
            txtMobileNo.requestFocus();
            txtMobileNo.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtMobileNo.getStyleClass().removeAll("mfx-text-field-error");

        return true;

    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        Stage btnCustomerStage = (Stage) btnCancel.getScene().getWindow();
        btnCustomerStage.close();

    }

    public void loadCustomerDetails() {

        Customer customer = CustomerModel.searchCustomer(cusId);
        setFields(customer);

    }

    private void setFields(Customer dto) {
        System.out.println(dto);

        txtCustomerId.setText(dto.getCusId());
        txtName.setText(dto.getName());
        txtAddress.setText(dto.getAddress());
        txtMobileNo.setText(dto.getContact_Number());

    }

}
