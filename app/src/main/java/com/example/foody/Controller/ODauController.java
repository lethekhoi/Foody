package com.example.foody.Controller;

import android.content.Context;
import android.location.Location;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody.Adapter.AdapterRecycleODau;
import com.example.foody.Controller.Interface.ODauInterface;
import com.example.foody.Model.QuanAnModel;
import com.example.foody.R;

import java.util.ArrayList;
import java.util.List;

public class ODauController {
    Location vitrihientai;
    Context context;
    QuanAnModel quanAnModel;
    AdapterRecycleODau adapterRecycleODau;

    public ODauController(Context context, Location vitrihientai) {
        this.vitrihientai = vitrihientai;
        this.context = context;
        quanAnModel = new QuanAnModel();
    }

    public void getDanhSachQuanAnController(RecyclerView recyclerViewODau, final ProgressBar progressBar) {
        final List<QuanAnModel> quanAnModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerViewODau.setLayoutManager(layoutManager);
        adapterRecycleODau = new AdapterRecycleODau(quanAnModelList, R.layout.custom_layoyt_recyclerview_odau);
        recyclerViewODau.setAdapter(adapterRecycleODau);
        ODauInterface oDauInterface = new ODauInterface() {
            @Override
            public void getDanhSachQuanAnModel(QuanAnModel quanAnModel) {
                quanAnModelList.add(quanAnModel);
                adapterRecycleODau.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        };
        quanAnModel.getDanhSachQuanAn(oDauInterface, vitrihientai);
    }
}
