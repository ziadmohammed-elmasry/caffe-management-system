package com.example.demo.Controllers.Admin;

import com.example.demo.Controllers.Tables.ProductTable;
import com.example.demo.Controllers.Product.Product;
import com.example.demo.Models.ProductsModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class InventoryFormController {
    public AnchorPane inventoryPane;
    public TableView tblProduct;
    public TableColumn colProductId;
    public TableColumn colProductName;
    public TableColumn colProductType;
    public TableColumn colStock;
    public TableColumn colPrice;
    public TableColumn colUpdate;
    public TableColumn colDelete;
    public MFXTextField txtSearch;
    public TextField txtProductName;
    public TextField txtProductID;
    public TextField txtStock;
    public TextField txtPrice;
    public MFXButton btnAddImage;
    public ImageView btnImport;
    public MFXButton btnProductClear;
    public MFXButton btnAddProduct;
    public ImageView imgProduct;
    public MFXFilterComboBox cmbProductType;
    public MFXButton btnProductUpdate;

    public void initialize() {
        setCellValuesFactory();
        loadAllProducts();
        loadProductTypes();
        txtProductID.setText(generateNextProductId());
    }

    private void setCellValuesFactory() {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colProductType.setCellValueFactory(new PropertyValueFactory<>("productType"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("updateButton"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));

    }

    public void loadAllProducts() {
        ObservableList<ProductTable> productTables = FXCollections.observableArrayList();
        try {

            List<Product> allProducts = ProductsModel.getAllProducts();
            for (Product product : allProducts) {
                productTables.add(new ProductTable(product.getProductId(), product.getProductName(), product.getProductType(), product.getStock(), product.getPrice()));
            }
            for (int i = 0; i < productTables.size(); i++) {
                final int index = i;
                productTables.get(i).getUpdateButton().setOnAction(event -> {
                    try {
                        updateProduct(productTables.get(index).getProductId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                productTables.get(i).getDeleteButton().setOnAction(event -> {

                    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?", yes, no);

                    if (alert.showAndWait().orElse(no) == yes) {
                        // Delete the product
                        String productId = productTables.get(index).getProductId();
                        deleteProduct(productId);
                        loadAllProducts();
                    }
                });
            }
            tblProduct.setItems(productTables);
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            throwables.printStackTrace();

        }
    }

    private void updateProduct(String productId) {
        Product product = ProductsModel.searchProduct(productId);
        if (product != null) {
            txtProductID.setText(product.getProductId());
            txtProductName.setText(product.getProductName());
            txtStock.setText(String.valueOf(product.getStock()));
            txtPrice.setText(String.valueOf(product.getPrice()));
            byte[] imageBytes = product.getImage();
            if (imageBytes != null && imageBytes.length > 0) {
                Image image = new Image(new ByteArrayInputStream(imageBytes));
                imgProduct.setImage(image);
            } else {
                imgProduct.setImage(null); // or set a default image
            }
        }
    }


    private void deleteProduct(String productId) {
        boolean isDeleted = ProductsModel.deleteProduct(productId);
        if (isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Product Deleted").show();
        }
    }


    public void txtSearchOnAction(ActionEvent actionEvent) {
    }

    public void btnAddImageOnAction(ActionEvent actionEvent) {
        addImage();
    }

    private void addImage() {
        // Implement the logic to add an image
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(inventoryPane.getScene().getWindow());
        if (selectedFile != null) {
            imgProduct.setImage(new javafx.scene.image.Image(selectedFile.toURI().toString()));
        }
    }

    public void btnAddProductOnAction(ActionEvent actionEvent) {
        // Implement the logic to add a product
        if (!validateProductDetails()) {
            return;
        }
        String productId = generateNextProductId();
        String productName = txtProductName.getText();
        String productType = cmbProductType.getSelectionModel().getSelectedItem().toString();
        int stock = Integer.parseInt(txtStock.getText());
        double price = Double.parseDouble(txtPrice.getText());
        //load the image
        Image image = imgProduct.getImage();


        Product product = new Product(productId, productName, productType, stock, price);
        boolean isAdded = ProductsModel.addProduct(product, image);
        if (isAdded) {
            new Alert(Alert.AlertType.CONFIRMATION, "Product Added").show();
            loadAllProducts();
        }
    }

    public String generateNextProductId() {
        try {
            String productId = ProductsModel.generateNextProductId();
            return productId;
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return null;
        }
    }

    // This method will be used to validate the product details
    private boolean validateProductDetails() {
        String productId = txtProductID.getText();
        if (txtProductID.getText().isEmpty() || txtProductName.getText().isEmpty() || txtStock.getText().isEmpty() || txtPrice.getText().isEmpty() || imgProduct.getImage() == null) {
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").show();
            return false;
        } else if (productId.charAt(0) != 'P') {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid product ID").show();
            return false;
        } else if (cmbProductType.getSelectionModel().isBound()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").show();
            return false;
        } else if (!txtStock.getText().matches("[0-9]+") || !txtPrice.getText().matches("^[0-9]+(\\.[0-9]+)?$")) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid number").show();
            return false;
        }
        return true;
    }

    public void cmbProductTypeOnAction(ActionEvent actionEvent) {

    }

    private void loadProductTypes() {
        ObservableList<String> productTypes = FXCollections.observableArrayList(
                "Soft Drinks", "Snacks", "Meals", "Tea", "Coffee", "Juice", "Water"
        );
        cmbProductType.setItems(productTypes);
    }


    public void btnProductClearOnAction(ActionEvent actionEvent) {
        txtProductID.setText(generateNextProductId());
        txtProductName.clear();
        txtStock.clear();
        txtPrice.clear();
        imgProduct.setImage(loadDefaultImage());
    }

    private Image loadDefaultImage() {
        return new Image(getClass().getResource("/img/icons/imageAdd.png").toExternalForm());
    }

    public void btnProductUpdateOnAction(ActionEvent actionEvent) {
        // Implement the logic to update a product
        if (!validateProductDetails()) {
            return;
        }
        String productId = txtProductID.getText();
        String productName = txtProductName.getText();
        String productType = cmbProductType.getSelectionModel().getSelectedItem().toString();
        int stock = Integer.parseInt(txtStock.getText());
        double price = Double.parseDouble(txtPrice.getText());
        //load the image
        Image image = imgProduct.getImage();

        Product product = new Product(productId, productName, productType, stock, price);
        boolean isUpdated = ProductsModel.updateProduct(product, image);
        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "Product Updated").show();
            loadAllProducts();
        }
    }
}
