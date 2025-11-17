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
public class EmployeeTable {
    private String empId;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
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
    public EmployeeTable(String empId,String firstName, String lastName, String userName, String password, String contactNumber) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.contact_Number = contactNumber;
    }

}
