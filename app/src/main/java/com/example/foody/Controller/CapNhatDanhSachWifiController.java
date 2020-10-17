package com.example.foody.Controller;

import android.content.Context;
import android.text.Html;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foody.Adapter.AdapterDanhSachWifi;
import com.example.foody.Controller.Interface.ChiTietQuanAnInterface;
import com.example.foody.Model.WifiQuanAnModel;
import com.example.foody.R;

import java.util.ArrayList;
import java.util.List;

public class CapNhatDanhSachWifiController {
    Context context;
    WifiQuanAnModel wifiQuanAnModel;
    List<WifiQuanAnModel> wifiQuanAnModelList;

    public CapNhatDanhSachWifiController(Context context) {
        this.context = context;
        wifiQuanAnModel = new WifiQuanAnModel();
        wifiQuanAnModelList = new ArrayList<>();
    }

    public void HienThiDanhSachWifiQuanAn(String maquanan, final RecyclerView recyclerView) {
        
        ChiTietQuanAnInterface chiTietQuanAnInterface = new ChiTietQuanAnInterface() {
            @Override
            public void HienThiDanhSachWifi(WifiQuanAnModel wifiQuanAnModel) {
                wifiQuanAnModelList.add(wifiQuanAnModel);
                AdapterDanhSachWifi adapterDanhSachWifi = new AdapterDanhSachWifi(wifiQuanAnModelList, R.layout.layout_list_wifi, context);
                recyclerView.setAdapter(adapterDanhSachWifi);
                adapterDanhSachWifi.notifyDataSetChanged();
            }

            @Override
            public void HienThiThemWifi() {

            }
        };
        wifiQuanAnModel.LayDanhSachWifiQuanAn(maquanan, chiTietQuanAnInterface);

    }


    public void ThemWifi(Context context, WifiQuanAnModel wifiQuanAnModel, String maquanan) {
        wifiQuanAnModel.ThemWifiQuanAn(context, wifiQuanAnModel, maquanan);

    }
}
