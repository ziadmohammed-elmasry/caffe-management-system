package com.example.demo.Controllers.Tables;


import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerTable {
    private String cusId;
    private String Name;
    private String address;
    private String contact_Number;
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
    public CustomerTable(String cusId,String Name, String address, String contact_Number) {
        this.cusId = cusId;
        this.Name = Name;
        this.address = address;
        this.contact_Number = contact_Number;
    }

}

