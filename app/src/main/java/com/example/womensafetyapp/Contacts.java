package com.example.womensafetyapp;

public class Contacts {

    String contactname;
    String contactnumber;

    public Contacts(){

    }

    public Contacts(String contactname, String contactnumber) {
        this.contactname = contactname;
        this.contactnumber = contactnumber;
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }
}
