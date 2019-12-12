package com.tech4lyf.absolutesolutionscrm.Models;

public class Customer {

    String key,id,name,date,phone,ref,loc,addr,romac,parts,price,handcash,customerpic,macpic;

    public Customer(String key,String id,String name,String date,String phone,String ref,String loc,String addr,String romac,String parts,String price,String handcash,String customerpic,String macpic)
    {
        this.key=key;
        this.id=id;
        this.name=name;
        this.date=date;
        this.phone=phone;
        this.ref=ref;
        this.loc=loc;
        this.addr=addr;
        this.romac=romac;
        this.parts=parts;
        this.price=price;
        this.handcash=handcash;
        this.customerpic=customerpic;
        this.macpic=macpic;
    }
    public Customer(){}

    public String getKey() {
        return key;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLoc() {
        return loc;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddr() {
        return addr;
    }

    public String getRef() {
        return ref;
    }

    public String getCustomerpic() {
        return customerpic;
    }

    public String getHandcash() {
        return handcash;
    }

    public String getMacpic() {
        return macpic;
    }

    public String getParts() {
        return parts;
    }

    public String getPrice() {
        return price;
    }

    public String getRomac() {
        return romac;
    }
}
