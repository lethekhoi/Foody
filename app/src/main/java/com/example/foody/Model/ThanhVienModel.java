package com.example.foody.Model;

import com.example.foody.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThanhVienModel {

    String hoten;
    String hinhanh;
    String mathanhvien;

    public String getMathanhvien() {
        return mathanhvien;
    }

    public void setMathanhvien(String mathanhvien) {
        this.mathanhvien = mathanhvien;
    }


    private DatabaseReference mDatabase;

    public ThanhVienModel() {

    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public ThanhVienModel(String hoten, String hinhanh) {
        this.hoten = hoten;
        this.hinhanh = hinhanh;
    }

    public void ThemThongTinThanhVien(ThanhVienModel thanhVienModel, String userID) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("thanhviens");
        mDatabase.child(userID).setValue(thanhVienModel);

    }
}
