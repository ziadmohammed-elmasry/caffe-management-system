package com.example.demo.Controllers.Admin;


import com.example.demo.Controllers.Product.Product;
import com.example.demo.Models.ProductsModel;
import com.example.demo.Models.SalesModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import lombok.Setter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MenuProductCardController {

    public AnchorPane menu_prod_card;
    public ImageView prod_imageview;
    @FXML
    Label prod_name;
    @FXML
    private GridPane menu_gridpane;

    @FXML
    Label prod_price;

    @FXML
    private Spinner<Integer> prod_spinner;

    @FXML
    private Button prod_addbtn;

    @Setter
    MenuController menuController;


    @FXML
    private void initialize() {
        prod_spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1));
        prod_addbtn.setOnAction(event -> {

            String name = prod_name.getText();
            int quantity = prod_spinner.getValue();
            double price = Double.parseDouble(prod_price.getText());
            if (menuController != null) {
                menuController.addProductToTable(name, quantity, price);
            }


        });
    }

    @Override
    public String toString() {
        return "MenuProductCardController{" +
                "prod_name=" + prod_name +
                ", prod_price=" + prod_price +
                ", prod_spinner=" + prod_spinner +
                ", prod_addbtn=" + prod_addbtn +
                ", menuController=" + menuController +
                '}';
    }


    public void setProductDetails(Product product, MenuController menuController) {
        this.menuController = menuController;
        prod_spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, product.getStock(), 1));
        prod_name.setText(product.getProductName());
        prod_price.setText(String.valueOf(product.getPrice()));
        prod_imageview.setImage(new Image(new ByteArrayInputStream(product.getImage())));
    }
    public void btnAddProductOnAction(ActionEvent actionEvent) {
        String name = prod_name.getText();
        int quantity = prod_spinner.getValue();
        double price = Double.parseDouble(prod_price.getText());
        if (menuController != null) {
            menuController.addProductToTable(name, quantity, price);
        }
    }
}
