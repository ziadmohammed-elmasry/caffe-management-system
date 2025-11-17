package com.example.demo.Controllers.Tables;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ProductTable {
    @Setter
    private String productId;
    private String productName;
    private String productType;
    private int stock;
    private double price;


    private MFXButton updateButton;
    private MFXButton deleteButton;
    {
        ImageView update = new ImageView(new Image("/img/icons/edit.png"));
        ImageView delete = new ImageView(new Image("/img/icons/remove.png"));


        update.setFitHeight(30);
        update.setPreserveRatio(true);


        delete.setFitHeight(30);
        delete.setPreserveRatio(true);


        updateButton = new MFXButton("", update);
        deleteButton = new MFXButton("",delete);

        updateButton.setCursor(javafx.scene.Cursor.HAND);
        deleteButton.setCursor(javafx.scene.Cursor.HAND);

        updateButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white");
        deleteButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white");

        updateButton.setPrefHeight(30);
        updateButton.setPrefWidth(80);

        deleteButton.setPrefHeight(30);
        deleteButton.setPrefWidth(80);

    }

    public ProductTable(String productId, String productName, String productType, int stock, double price) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.stock = stock;
        this.price = price;
    }

}
