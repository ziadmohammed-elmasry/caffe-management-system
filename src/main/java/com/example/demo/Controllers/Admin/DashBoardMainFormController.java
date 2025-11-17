package com.example.demo.Controllers.Admin;

import com.example.demo.Controllers.Sale.Sale;
import com.example.demo.Models.ProductsModel;
import com.example.demo.Models.SalesModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class DashBoardMainFormController {
    public PieChart chartProduction;
    public Text txtUser;
    public AnchorPane mainPane;
    public Text txtOrders;
    public Text txtDate;
    public Text txtSales;
    public Text txtCustomers;
    public AreaChart chartInventory;
    public PieChart chartSales;
    @FXML
    private Text txtTime;

    public void initialize() {
        updateTime();
        sentName(Admin.getName());
        updateDate();
        loadSalesAmount();
        loadOrders();
        loadCustomers();
        loadInventoryChart();
        loadSalesChart();

    }
    protected void loadInventoryChart() {
        ArrayList<String> months = new ArrayList<>();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");

        ArrayList<Integer> inventory = new ArrayList<>();
        inventory.add(100);
        inventory.add(200);
        inventory.add(300);
        inventory.add(400);
        inventory.add(500);
        inventory.add(600);
        inventory.add(500);
        inventory.add(400);
        inventory.add(300);
        inventory.add(1000);
        inventory.add(800);
        inventory.add(700);
        chartInventory.getData().add(SalesModel.getInventoryChartData(months, inventory));
    }
    public void loadSalesChart() {
        ArrayList<Sale> sales = (ArrayList<Sale>) SalesModel.getAllSales();
        for (PieChart.Data data : SalesModel.getSalesChartData(sales)) {
            chartSales.getData().add(data);
        }
    }

    protected void loadSalesAmount() {
        txtSales.setText("Rs."+String.valueOf(SalesModel.getAllSalesAmount())+"0");
    }
    protected void loadOrders() {
        txtOrders.setText(String.valueOf(SalesModel.getAllSalesCount()));
    }
    protected void loadCustomers() {
        txtCustomers.setText(String.valueOf(SalesModel.getAllCustomersCount()));
    }
    private void sentName(String name) {
        txtUser.setText(name);
    }
    protected void updateDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(formatter);
        txtDate.setText(formattedDate);
    }

    protected void updateTime() {
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            txtTime.setText(timeNow());

        }),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private String timeNow() {
        SimpleDateFormat dateFormat=new SimpleDateFormat("hh:mm:ss a");
        return dateFormat.format(new Date()) ;
    }




}
