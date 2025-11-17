package com.example.demo.Controllers.Admin;

import lombok.Getter;

public class Admin {
    @Getter
    private static String name;
    private String password;

    public Admin(String name, String password) {
        Admin.name = name;
        this.password = password;
    }

    public void setName(String name) {
        Admin.name = name;
    }

}
