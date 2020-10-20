package com.example.foody.Model;

public class MonAnModel {
    long giatien;
    String mamon, hinhanh, tenmon;

    public MonAnModel() {
    }

    public long getGiatien() {
        return giatien;
    }

    public void setGiatien(long giatien) {
        this.giatien = giatien;
    }

    public String getMamon() {
        return mamon;
    }

    public void setMamon(String mamon) {
        this.mamon = mamon;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }

    public MonAnModel(long giatien, String mamon, String hinhanh, String tenmon) {
        this.giatien = giatien;
        this.mamon = mamon;
        this.hinhanh = hinhanh;
        this.tenmon = tenmon;
    }
}
