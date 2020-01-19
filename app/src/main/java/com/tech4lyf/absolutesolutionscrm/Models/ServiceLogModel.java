package com.tech4lyf.absolutesolutionscrm.Models;

public class ServiceLogModel {

    String key,cName,date,phone,raw,aro,rejection,parts,price,handcash,workdone,sign,desc;

    public ServiceLogModel(String key, String cName, String date, String phone, String raw, String aro, String rejection, String parts, String price, String handcash, String workdone, String sign, String desc)
    {
        this.key=key;
        this.cName=cName;
        this.date=date;
        this.phone=phone;
        this.raw=raw;
        this.aro=aro;
        this.rejection=rejection;
        this.parts=parts;
        this.price=price;
        this.handcash=handcash;
        this.workdone=workdone;
        this.sign=sign;
        this.desc=desc;
    }
    public ServiceLogModel(){}

    public String getKey() {
        return key;
    }

    public String getcName() {
        return cName;
    }

    public String getDate() {
        return date;
    }

    public String getPhone() {
        return phone;
    }

    public String getRaw() {
        return raw;
    }

    public String getAro() {
        return aro;
    }

    public String getRejection() {
        return rejection;
    }

    public String getParts() {
        return parts;
    }

    public String getPrice() {
        return price;
    }

    public String getHandcash() {
        return handcash;
    }

    public String getWorkdone() {
        return workdone;
    }

    public String getSign() {
        return sign;
    }

    public String getDesc() {
        return desc;
    }
}
