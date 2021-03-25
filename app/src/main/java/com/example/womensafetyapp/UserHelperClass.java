package com.example.womensafetyapp;

public class UserHelperClass {
    String fullname, username, email, password,phoneNo;


    public UserHelperClass(String fullname, String username, String email, String password, String phoneNo) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
    }




    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}