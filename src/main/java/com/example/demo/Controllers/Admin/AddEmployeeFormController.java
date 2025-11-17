package com.example.demo.Controllers.Admin;

import com.example.demo.Controllers.Employee.Employee;
import com.example.demo.Models.Users;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Setter;

import java.util.regex.Pattern;

public class AddEmployeeFormController {


    public MFXButton btnCancel;
    public MFXTextField txtFirstName;
    public MFXButton btnAddEmployee;
    public Text txtEmployeeId;
    public MFXTextField txtLastName;
    public MFXTextField txtUserName;
    public MFXTextField txtMobileNo;
    public MFXTextField txtPassword;

    @Setter
    private EmployeeFormController employeeFormController;

    private final String empId=generateNextEmployeeId();

    public String generateNextEmployeeId() {
        try {
            return Users.generateNextEmployeeId();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return null;
        }
    }

    public void initialize() {
        txtEmployeeId.setText(empId);
    }

    public void btnAddEmployee(ActionEvent actionEvent) {
        boolean isEmployeeValidated = validateEmployee();
        System.out.println(isEmployeeValidated);

        if (!isEmployeeValidated) {
            return;
        }

        String empId = txtEmployeeId.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        String mobileNo = txtMobileNo.getText();

        try {
            boolean isUpdated = Users.addEmployee(new Employee(empId, firstName, lastName, userName, password, mobileNo));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Added").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        employeeFormController.loadAllEmployees();
    }


    public void btnCancelOnAction(ActionEvent actionEvent) {
        Stage btnEmployeeStage = (Stage) btnCancel.getScene().getWindow();
        btnEmployeeStage.close();

    }


    private boolean validateEmployee() {
        String firstName = txtFirstName.getText();
        boolean isFirstNameValid = Pattern.matches("[A-Za-z]{3,}", firstName);
        if (!isFirstNameValid) {
            txtFirstName.requestFocus();
            txtFirstName.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtFirstName.getStyleClass().removeAll("mfx-text-field-error");


        String lastName = txtLastName.getText();
        boolean isLastNameValid = Pattern.matches("[A-Za-z]{3,}", lastName);
        if (!isLastNameValid) {
            txtLastName.requestFocus();
            txtLastName.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtLastName.getStyleClass().removeAll("mfx-text-field-error");


        String userName = txtUserName.getText();
        boolean isUserNameValid = Pattern.matches("[A-Za-z0-9/ ]{3,}", userName);
        if (!isUserNameValid) {
            txtUserName.requestFocus();
            txtUserName.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtUserName.getStyleClass().removeAll("mfx-text-field-error");

        String password = txtPassword.getText();
        boolean isPasswordValid = Pattern.matches("[A-Za-z0-9/ ]{3,}", password);
        if (!isPasswordValid) {
            txtPassword.requestFocus();
            txtPassword.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtPassword.getStyleClass().removeAll("mfx-text-field-error");


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
    public void setCustomerFormController(EmployeeFormController employeeFormController) {
        this.employeeFormController=employeeFormController;
    }


}
