package com.example.foody.Controller;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody.Adapter.AdapterThucDon;
import com.example.foody.Controller.Interface.ThucDonInterface;
import com.example.foody.Model.MonAnModel;
import com.example.foody.Model.ThucDonModel;

import java.util.List;

public class ThucDonController {
    ThucDonModel thucDonModel;

    public ThucDonController() {
        thucDonModel = new ThucDonModel();
    }


    public void GetDanhSachThucDonTheoMaQuanAn(Context context, String maquanan, RecyclerView recyclerViewThucDon) {

        recyclerViewThucDon.setLayoutManager(new LinearLayoutManager(context));
        ThucDonInterface thucDonInterface = new ThucDonInterface() {
            @Override
            public void GetThucDonThanhCong(List<ThucDonModel> thucDonModelList) {

                AdapterThucDon adapterThucDon = new AdapterThucDon(context, thucDonModelList);
                recyclerViewThucDon.setAdapter(adapterThucDon);
                adapterThucDon.notifyDataSetChanged();
            }
        };
        thucDonModel.GetDanhSachThucDonTheoMaQuanAn(maquanan, thucDonInterface);
    }
}
