package com.tech4lyf.absolutesolutionscrm.Models;

public class ScheduledWorkList {
        String date,sno,status,title,key;

        public ScheduledWorkList()
        {
            date="";
            sno="";
            status="";
            title="";
        }

    public ScheduledWorkList(String key,String title,String date,String sno,String status) {
            this.key=key;
            this.title=title;
            this.date=date;
            this.sno=sno;
            this.status=status;
    }

        public ScheduledWorkList(String title,String date,String sno,String status)
        {
            this.title=title;
            this.date=date;
            this.sno=sno;
            this.status=status;
        }

        public String getTitle() {
            return title;
        }

    public String getStatus() {
        return status;
    }

    public String getSno() {
        return sno;
    }
    public String getKey() {
        return key;
    }

    public String getDate() {
        return date;
    }
}
