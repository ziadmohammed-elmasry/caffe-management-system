package com.example.demo.Controllers.Admin;

import com.example.demo.Controllers.Sale.Sale;
import com.example.demo.Models.SalesModel;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class SalesFormController {

    public AnchorPane salesPane;
    public TableView tableSales;
    public TableColumn colSaleID;
    public TableColumn colCustomerName;
    public TableColumn colContactNo;
    public TableColumn colTotal;
    public TableColumn colDate;
    public MFXTextField txtSearch;



    public void initialize() {
        loadAllSales();
        setCellValuesFactory();

    }
    private void setCellValuesFactory() {
        colSaleID.setCellValueFactory(new PropertyValueFactory<>("saleId"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("cusName"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("cusContact"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

    }

    public void loadAllSales() {
        ObservableList<Sale> salesTable = FXCollections.observableArrayList();
        try {
            List<Sale> allSales =  SalesModel.getAllSales();
            salesTable.addAll(allSales);
            tableSales.setItems(salesTable);
        } catch (Exception e) {
            e.printStackTrace();
        };
    }


    public void txtSearchOnAction(ActionEvent actionEvent) {
    }
}
