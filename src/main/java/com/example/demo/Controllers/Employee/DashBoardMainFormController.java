package com.example.demo.Controllers.Employee;

import com.example.demo.Controllers.Admin.Admin;
import com.example.demo.Controllers.Admin.DashBoardFormController;
import com.example.demo.Models.SalesModel;
import com.example.demo.Models.Users;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DashBoardMainFormController extends com.example.demo.Controllers.Admin.DashBoardMainFormController {

    public Text txtUser;
    public AnchorPane mainPane;
    public Text txtOrders;
    public Text txtDate;
    public Text txtSales;
    public Text txtCustomers;
    @FXML


    public void initialize() {
        updateTime();
        sentName(Users.username);
        System.out.println("Name: " + Users.username);
        updateDate();
        loadSalesAmount();
        loadOrders();
        loadCustomers();
        loadInventoryChart();
        loadSalesChart();
    }
    private void sentName(String name) {

        txtUser.setText(name);
    }





}
