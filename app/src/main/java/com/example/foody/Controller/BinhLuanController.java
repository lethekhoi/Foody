package com.example.foody.Controller;

import android.net.Uri;

import com.example.foody.Model.BinhLuanModel;

import java.util.List;

public class BinhLuanController {

    BinhLuanModel binhLuanModel;

    public BinhLuanController() {
        binhLuanModel = new BinhLuanModel();
    }

    public void ThemBinhLuan(String maquanan, BinhLuanModel binhLuanModel, List<Uri> uriList) {
        binhLuanModel.ThemBinhLuan(maquanan, binhLuanModel, uriList);
    }
}
