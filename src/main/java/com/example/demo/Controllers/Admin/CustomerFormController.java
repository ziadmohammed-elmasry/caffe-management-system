package com.example.demo.Controllers.Admin;

import com.example.demo.Controllers.Tables.CustomerTable;
import com.example.demo.Controllers.Customer.Customer;
import com.example.demo.Models.CustomerModel;
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

public class CustomerFormController {
    public MFXTextField txtSearch;
    public TableColumn colCustomerId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colMobileNo;
    public TableColumn colUpdate;
    public TableColumn colDelete;
    public AnchorPane employeePane;
    public MFXButton btnAddCustomer;
    public TableView tblCustomer;


    public void initialize() {
        setCellValuesFactory();
        loadAllCustomers();
    }

    //TODO: Implement the functionality of the EmployeeFormController
    //Display the employee details in the table
    private void setCellValuesFactory() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colMobileNo.setCellValueFactory(new PropertyValueFactory<>("contact_Number"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("updateButton"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));

    }

    public void loadAllCustomers() {

        ObservableList<CustomerTable> obList = FXCollections.observableArrayList();
        try {

            List<Customer> allCustomers = CustomerModel.getAllCustomers();

            for (Customer customer : allCustomers) {
                obList.add(new CustomerTable(
                        customer.getCusId(),
                        customer.getName(),
                        customer.getAddress(),
                        customer.getContact_Number()));
            }

            for (int i = 0; i < obList.size(); i++) {
                final int index = i;

                obList.get(i).getUpdateButton().setOnAction(event -> {
                    try {
                        updateCustomer(allCustomers.get(index).getCusId());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });


                obList.get(i).getDeleteButton().setOnAction(event ->{

                    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this customer?", yes, no).showAndWait();

                    if(type.orElse(no) == yes){

                        String cusId = allCustomers.get(index).getCusId();
                        deleteCustomer(cusId);
                        loadAllCustomers();
                    }
                });

            }
            tblCustomer.setItems(obList);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateCustomer(String cusId) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Admin/UpdateCustomerForm.fxml"));
        Parent rootNode = loader.load();

        // Get a reference to the UpdateCustomerFormController
        UpdateCustomerFormController updateCustomerFormController = loader.getController();

        // Pass a reference to this CustomerFormController
        updateCustomerFormController.setCustomerFormController(this);

        // Set the customer id to the UpdateCustomerFormController
        updateCustomerFormController.setCusId(cusId);

        // Load the customer details
        updateCustomerFormController.loadCustomerDetails();

        System.out.println("akaya");


        Scene scene = new Scene(rootNode);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Update Customer");
        stage.show();

    }

    private void deleteCustomer(String cusId) {

        boolean isDeleted = CustomerModel.deleteCustomer(cusId);

        if (isDeleted){
            new Alert(Alert.AlertType.CONFIRMATION, "Customer Deleted").show();
        }

    }

    public void btnAddCustomerOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Admin/AddCustomerForm.fxml"));
        Parent rootNode = loader.load();

        // Get a reference to the AddCustomerFormController
        AddCustomerFormController addCustomerFormController = loader.getController();

        // Pass a reference to this CustomerFormController
        addCustomerFormController.setCustomerFormController(this);


        Scene scene = new Scene(rootNode);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Add Customer");
        stage.show();
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {

    }
}

