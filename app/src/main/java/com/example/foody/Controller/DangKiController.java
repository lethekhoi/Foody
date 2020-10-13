package com.example.foody.Controller;

import com.example.foody.Model.ThanhVienModel;

public class DangKiController {
    ThanhVienModel thanhVienModel;


    public DangKiController() {
        thanhVienModel = new ThanhVienModel();
    }

    public void ThemThongTinThanhVienController(ThanhVienModel thanhVienModel, String userID) {
        thanhVienModel.ThemThongTinThanhVien(thanhVienModel, userID);
    }
}
