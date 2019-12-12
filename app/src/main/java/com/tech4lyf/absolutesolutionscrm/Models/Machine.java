package com.tech4lyf.absolutesolutionscrm.Models;

public class Machine {
    String key;
    String machName;



    public Machine(String key, String machName) {
        this.key = key;
        this.machName=machName;



    }

    public Machine(){}

    public String getKey() {
        return key;
    }

    public String getMachName() {
        return machName;
    }

}
