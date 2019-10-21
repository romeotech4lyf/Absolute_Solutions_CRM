package com.tech4lyf.absolutesolutionscrm.Models;

public class User {
    String key;

    String username;
    String  password;

    public User(String key, String username, String password) {
        this.key = key;
        this.username= username;

        this.password= password;
    }

    public User() {
    }



    public String getKey() {
        return key;
    }

    public String getUsername() {
        return username;
    }



    public String getPassword() {
        return password;
    }
}