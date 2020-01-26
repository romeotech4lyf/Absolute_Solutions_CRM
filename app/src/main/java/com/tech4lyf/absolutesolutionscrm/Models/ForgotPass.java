package com.tech4lyf.absolutesolutionscrm.Models;

public class ForgotPass {

    String mail,password;


    public ForgotPass()
    {
        mail = "";
        password="";
    }

    public ForgotPass(String mail,String password)
    {
        this.mail = mail;
        this.password=password;
    }

    public String getMail() {
        return mail;
    }

    public String getpassword() {
        return password;
    }
}
