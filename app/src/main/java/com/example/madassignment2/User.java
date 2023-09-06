package com.example.madassignment2;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String password;

    public User(String id,String name, String phone, String email, String password) {
        this.id=id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }
    public String getId(){return id;}

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
