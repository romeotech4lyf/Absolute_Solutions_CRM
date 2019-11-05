package com.tech4lyf.absolutesolutionscrm.Models;

public class ForgotPass {

    String mail;


    public ForgotPass()
    {
        mail = "";
    }

    public ForgotPass(String mail)
    {
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

}
