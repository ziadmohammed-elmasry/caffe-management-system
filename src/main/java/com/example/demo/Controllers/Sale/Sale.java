package com.example.demo.Controllers.Sale;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
public class Sale {
    private String saleId;
    private String cusName;
    private String cusContact;
    private Double total;
    private Date date;


    @Override
    public String toString() {
        return "Sale{" +
                "saleId='" + saleId + '\'' +
                ", cusName='" + cusName + '\'' +
                ", cusContact='" + cusContact + '\'' +
                ", total=" + total +
                ", date=" + date +
                '}';
    }

    public Sale(String saleId, String cusName, String cusContact, Double total, Date date) {
        this.saleId = saleId;
        this.cusName = cusName;
        this.cusContact = cusContact;
        this.total = total;
        this.date = date;
    }


}
