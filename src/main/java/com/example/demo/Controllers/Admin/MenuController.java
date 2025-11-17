package com.example.demo.Controllers.Admin;

import com.example.demo.Controllers.Customer.Customer;
import com.example.demo.Controllers.Product.Product;
import com.example.demo.Controllers.Sale.Sale;
import com.example.demo.Models.CustomerModel;
import com.example.demo.Models.ProductsModel;
import com.example.demo.Models.SalesModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuController {

    public Pane MenuPane;
    public AnchorPane menu_form;
    public ScrollPane menu_scrollpane;
    public GridPane menu_gridpane;
    public ChoiceBox menu_customer;
    public MFXButton btnCancel;
    public MFXButton btnPay;
    @FXML
    private TableView<Product> menu_tableview;

    @FXML
    private TableColumn<Product, String> menu_col_productname;

    @FXML
    private TableColumn<Product, Integer> menu_col_quantity;

    @FXML
    private TableColumn<Product, Double> menu_col_price; // Unit price for each product

    @FXML
    private Label menu_total; // Total of all products in Rs.

    @FXML
    private TextField menu_amount; // Customer payment input

    @FXML
    private Label menu_change; // Change displayed here

    private ObservableList<Product> productList = FXCollections.observableArrayList();
    private ObservableList<String> customerList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        loadProductCards();
        // Map columns to Product fields
        menu_col_productname.setCellValueFactory(new PropertyValueFactory<>("productName"));
        menu_col_quantity.setCellValueFactory(new PropertyValueFactory<>("stock")); // stock represents quantity here
        menu_col_price.setCellValueFactory(new PropertyValueFactory<>("price")); // price represents unit price

        menu_tableview.setItems(productList);
        menu_amount.textProperty().addListener((observable, oldValue, newValue) -> calculateChange());
        menu_customer.setOnMouseClicked(event -> {
            customerList.clear();
            loadCustomers();
            menu_customer.setItems(customerList);
        });
    }

    private void loadCustomers() {
        ArrayList <String> customerContactNo = new ArrayList<>();
        try {
            List<Customer> allCustomers = CustomerModel.getAllCustomers();
            for (Customer customer : allCustomers) {
                customerContactNo.add(customer.getContact_Number());
            }
            customerList.addAll(customerContactNo);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        public void addProductToTable(String productName, int quantity, double unitPrice) {
        Product product = new Product(null, productName, null, quantity, unitPrice);

        productList.add(product);
        calculateTotal();
    }

    private void calculateTotal() {
        double total = productList.stream()
                .mapToDouble(product -> product.getStock() * product.getPrice()) // Calculate total as qty * unit price
                .sum();
        menu_total.setText("Rs. " + String.format("%.2f", total));
        calculateChange();
    }

    private void calculateChange() {
        try {
            double amount = Double.parseDouble(menu_amount.getText());
            double total = productList.stream()
                    .mapToDouble(product -> product.getStock() * product.getPrice())
                    .sum();
            double change = amount - total;
            menu_change.setText("Rs. " + String.format("%.2f", change));
        } catch (NumberFormatException e) {
            menu_change.setText("Rs. 0.00");
        }
    }

    public void loadProductCards() {
        List<Product> products = ProductsModel.getAllProductsWithImages();
        int column = 0;
        int row = 0;
        for (Product product : products) {
            System.out.println("Product: " + product);
            addProductCard(product, column, row);
            column++;
            if (column == 2) {
                column = 0;
                row++;
            }
        }
    }

    private void addProductCard(Product product, int column, int row) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Admin/MenuProductCard.fxml"));
            AnchorPane cardPane = loader.load();
            MenuProductCardController controller = loader.getController();
            controller.setProductDetails(product, this);
            menu_gridpane.add(cardPane, column, row);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        productList.clear();
        menu_total.setText("Rs. 0.00");
        menu_change.setText("Rs. 0.00");
        menu_amount.clear();
    }

    public void btnPayOnAction(ActionEvent actionEvent) {
        String customerContactNo = (String) menu_customer.getValue();
        if (customerContactNo == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a customer").show();
            return;
        }
        Customer customer = CustomerModel.searchCustomerByContactNo(customerContactNo);
        if (customer == null) {
            new Alert(Alert.AlertType.ERROR, "Customer not found").show();
            return;
        }
        String saleId = SalesModel.generateNextSaleId();
        double total = productList.stream()
                .mapToDouble(product -> product.getStock() * product.getPrice())
                .sum();
        Sale sale = new Sale(saleId, customer.getName(), customer.getContact_Number(), total, new java.util.Date());
        boolean isAdded = SalesModel.addSale(sale);
        if (isAdded) {
            for (Product product : productList) {
                ProductsModel.updateStock(product.getProductName(), product.getStock());
            }
            new Alert(Alert.AlertType.CONFIRMATION, "Order added").show();
            productList.clear();
            menu_total.setText("Rs. 0.00");
            menu_change.setText("Rs. 0.00");
            menu_amount.clear();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to add order").show();
        }
    }
}
