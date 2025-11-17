package com.example.demo.Controllers.Admin;

import com.example.demo.Controllers.Tables.EmployeeTable;
import com.example.demo.Controllers.Employee.Employee;
import com.example.demo.Models.Users;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeeFormController {
    public MFXTextField txtSearch;
    public MFXButton btnSalary;
    public MFXButton btnAttendance;
    public MFXButton btnAddEmployee;
    public TableColumn colEmployeeId;
    public TableColumn colFirstName;
    public TableColumn colLastName;
    public TableColumn colMobileNo;
    public TableColumn colUpdate;
    public TableColumn colDelete;
    public TableView tblEmployee;
    public AnchorPane employeePane;
    public TableColumn colUserName;
    public TableColumn colPassword;

    public void initialize() {
        setCellValuesFactory();
        loadAllEmployees();
    }

    //TODO: Implement the functionality of the EmployeeFormController
    //Display the employee details in the table
    private void setCellValuesFactory() {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colMobileNo.setCellValueFactory(new PropertyValueFactory<>("contact_Number"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("updateButton"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));

    }

    public void loadAllEmployees() {

        ObservableList<EmployeeTable> obList = FXCollections.observableArrayList();
        try {

            List<Employee> allEmployees = Users.getAllEmployees();

            for (Employee employee : allEmployees) {
                obList.add(new EmployeeTable(
                        employee.getEmpId(),
                        employee.getUserName(),
                        employee.getPassword(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getContact_Number()));
            }

            for (int i = 0; i < obList.size(); i++) {
                final int index = i;

                obList.get(i).getUpdateButton().setOnAction(event -> {
                    try {
                        updateEmployee(allEmployees.get(index).getEmpId());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });


                obList.get(i).getDeleteButton().setOnAction(event ->{

                    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this employee?", yes, no).showAndWait();

                    if(type.orElse(no) == yes){

                        String empId = allEmployees.get(index).getEmpId();
                        deleteEmployee(empId);
                        loadAllEmployees();
                    }
                });

            }
            tblEmployee.setItems(obList);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateEmployee(String empId) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Admin/UpdateEmployeeForm.fxml"));
        Parent rootNode = loader.load();

        UpdateEmployeeFormController updateEmployeeFormController = loader.getController();

        updateEmployeeFormController.setEmployeeFormController(this);
        updateEmployeeFormController.setEmployeeId(empId);
        updateEmployeeFormController.loadEmployeeDetails();

        Scene scene = new Scene(rootNode);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Update Employee");
        stage.show();

    }

    private void deleteEmployee(String empId) {

        boolean isDeleted = Users.deleteEmployee(empId);

        if (isDeleted){
            new Alert(Alert.AlertType.CONFIRMATION, "Employee Deleted").show();
        }

    }

    public void btnAddEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Admin/addEmployeeForm.fxml"));
        Parent rootNode = loader.load();

        // Get a reference to the AddCustomerFormController
        AddEmployeeFormController addEmployeeFormController = loader.getController();

        // Pass a reference to this CustomerFormController
        addEmployeeFormController.setCustomerFormController(this);

        Scene scene = new Scene(rootNode);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Add Employee");
        stage.show();
    }


    public void btnAttendanceOnAction(ActionEvent actionEvent) {
    }

    public void btnSalaryOnAction(ActionEvent actionEvent) {
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
    }
}
