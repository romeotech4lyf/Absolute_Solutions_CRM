package com.tech4lyf.absolutesolutionscrm.Models;

public class LoginUser {
    String username;
    String password;

    public LoginUser()
    {
        username = "";
        password = "";
    }

    public LoginUser(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
