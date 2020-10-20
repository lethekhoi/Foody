package com.example.foody.Model;

public class DatMon {
    MonAnModel monAnModel;
    int soluong;

    public DatMon() {
    }

    public MonAnModel getMonAnModel() {
        return monAnModel;
    }

    public void setMonAnModel(MonAnModel monAnModel) {
        this.monAnModel = monAnModel;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public DatMon(MonAnModel monAnModel, int soluong) {
        this.monAnModel = monAnModel;
        this.soluong = soluong;
    }
}
