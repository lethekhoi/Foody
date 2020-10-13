package com.example.foody.Model;

public class ChiNhanhQuanAnModel {
    String diachi;
    double latitude, longitude, khoangcach;

    public ChiNhanhQuanAnModel() {
    }

    public ChiNhanhQuanAnModel(String diachi, double latitude, double longitude, double khoangcach) {
        this.diachi = diachi;
        this.latitude = latitude;
        this.longitude = longitude;
        this.khoangcach = khoangcach;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getKhoangcach() {
        return khoangcach;
    }

    public void setKhoangcach(double khoangcach) {
        this.khoangcach = khoangcach;
    }
}
