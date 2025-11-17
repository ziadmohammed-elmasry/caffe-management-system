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

import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Pattern;


public class UpdateEmployeeFormController {
    public MFXButton btnUpdateEmployee;
    public MFXButton btnCancel;
    public MFXTextField txtFirstName;
    public Text txtEmployeeId;
    public MFXTextField txtLastName;
    public MFXTextField txtMobileNo;
    public MFXTextField txtUserName;
    public MFXTextField txtPassword;

    private String empId;
    @Setter
    private EmployeeFormController employeeFormController;



    public void btnUpdateEmployee(ActionEvent actionEvent) {
        boolean isEmployeeValidated = validateEmployee();
        System.out.println(isEmployeeValidated);

        if (!isEmployeeValidated){
            return;
        }

        String empId = txtEmployeeId.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        String mobileNo = txtMobileNo.getText();

        try {
            boolean isUpdated = Users.updateEmployee(new Employee(empId,firstName,lastName,userName,password,mobileNo));
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Updated").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        employeeFormController.loadAllEmployees();
    }

    private boolean validateEmployee() {
        String firstName = txtFirstName.getText();
        boolean isFirstNameValid = Pattern.matches("[A-Za-z]{3,}",firstName);
        if (!isFirstNameValid){
            txtFirstName.requestFocus();
            txtFirstName.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtFirstName.getStyleClass().removeAll("mfx-text-field-error");


        String lastName = txtLastName.getText();
        boolean isLastNameValid = Pattern.matches("[A-Za-z]{3,}",lastName);
        if (!isLastNameValid){
            txtLastName.requestFocus();
            txtLastName.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtLastName.getStyleClass().removeAll("mfx-text-field-error");


        String userName = txtUserName.getText();
        boolean isUserNameValid = Pattern.matches("[A-Za-z0-9/ ]{3,}",userName);
        if (!isUserNameValid){
            txtUserName.requestFocus();
            txtUserName.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtUserName.getStyleClass().removeAll("mfx-text-field-error");

        String password = txtPassword.getText();
        boolean isPasswordValid = Pattern.matches("[A-Za-z0-9/ ]{3,}",password);
        if (!isPasswordValid){
            txtPassword.requestFocus();
            txtPassword.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtPassword.getStyleClass().removeAll("mfx-text-field-error");


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
            Stage btnEmployeeStage = (Stage) btnCancel.getScene().getWindow();
            btnEmployeeStage.close();

    }

    public void setEmployeeId(String empId) {
        this.empId = empId;
    }

    public void loadEmployeeDetails() {

        Employee employee = Users.searchEmployee(empId);
        setFields(employee);

    }

    private void setFields(Employee dto) {


        txtEmployeeId.setText(dto.getEmpId());
        txtFirstName.setText(dto.getFirstName());
        txtLastName.setText(dto.getLastName());
        txtUserName.setText(dto.getUserName());
        txtPassword.setText(dto.getPassword());
        txtMobileNo.setText(dto.getContact_Number());

    }
}
