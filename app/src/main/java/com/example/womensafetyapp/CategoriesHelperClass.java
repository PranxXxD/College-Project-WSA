package com.example.womensafetyapp;

public class CategoriesHelperClass {
    int Image;
    String Title;
    //RelativeLayout Gradient;

    ///int Gradient;

    public int getImage() {
        return Image;
    }

    public String getTitle() {
        return Title;
    }

    public CategoriesHelperClass(int image, String title) {
        Image = image;
        Title = title;

    }
}