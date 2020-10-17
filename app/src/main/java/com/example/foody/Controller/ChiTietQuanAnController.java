package com.example.foody.Controller;

import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.example.foody.Controller.Interface.ChiTietQuanAnInterface;
import com.example.foody.Model.WifiQuanAnModel;
import com.example.foody.R;

import java.util.ArrayList;
import java.util.List;

public class ChiTietQuanAnController {
    WifiQuanAnModel wifiQuanAnModel;
    List<WifiQuanAnModel> wifiQuanAnModelList;

    public ChiTietQuanAnController() {
        wifiQuanAnModel = new WifiQuanAnModel();
        wifiQuanAnModelList = new ArrayList<>();
    }


    public void HienThiDanhSachWifiQuanAn(String maquanan, final TextView txtTenWifi, final TextView txtMatKhauWifi) {
        ChiTietQuanAnInterface chiTietQuanAnInterface = new ChiTietQuanAnInterface() {
            @Override
            public void HienThiDanhSachWifi(WifiQuanAnModel wifiQuanAnModel) {
                wifiQuanAnModelList.add(wifiQuanAnModel);

                txtTenWifi.setText(wifiQuanAnModel.getTen());
                String matkhauText = getColoredSpanned("Mật khẩu", "#9c9a9a");
                String matkhau = getColoredSpanned(wifiQuanAnModel.getMatkhau(), "#030bfc");
                txtMatKhauWifi.setText(Html.fromHtml(matkhauText + " - " + matkhau));


            }

            @Override
            public void HienThiThemWifi() {
                txtTenWifi.setText(R.string.themwifi);
            }
        };
        wifiQuanAnModel.LayDanhSachWifiQuanAn(maquanan, chiTietQuanAnInterface);

    }

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }
}
