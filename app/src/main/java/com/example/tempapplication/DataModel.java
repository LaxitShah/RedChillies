package com.example.tempapplication;

public class DataModel {

    String strLang;
    int imgLang;
    public DataModel(String strLang, int imgLang) {
        this.strLang=strLang;
        this.imgLang=imgLang;
    }

    public String getStrLang() {
        return strLang;
    }

    public void setStrLang(String strLang) {
        this.strLang = strLang;
    }

    public int getImgLang() {
        return imgLang;
    }

    public void setImgLang(int imgLang) {
        this.imgLang = imgLang;
    }
}
