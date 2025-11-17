package com.example.demo.Controllers.Customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Customer {
    private String cusId;
    private String Name;
    private String address;
    private String contact_Number;

    public Customer(String cusId, String name, String address, String contact_Number) {
        this.cusId = cusId;
        Name = name;
        this.address = address;
        this.contact_Number = contact_Number;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cusId='" + cusId + '\'' +
                ", Name='" + Name + '\'' +
                ", address='" + address + '\'' +
                ", contact_Number='" + contact_Number + '\'' +
                '}';
    }
}
