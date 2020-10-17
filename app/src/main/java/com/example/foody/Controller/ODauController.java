package com.example.foody.Controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody.Adapter.AdapterRecycleODau;
import com.example.foody.Controller.Interface.ODauInterface;
import com.example.foody.Model.QuanAnModel;
import com.example.foody.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.core.widget.NestedScrollView;

import java.util.ArrayList;
import java.util.List;

public class ODauController {

    Location vitrihientai;
    Context context;
    QuanAnModel quanAnModel;
    AdapterRecycleODau adapterRecycleODau;
    int itemdaco = 3;

    public ODauController(Context context, Location vitrihientai) {
        this.vitrihientai = vitrihientai;
        this.context = context;
        quanAnModel = new QuanAnModel();
    }

    public void getDanhSachQuanAnController(Context context, NestedScrollView nestedScrollView, RecyclerView recyclerViewODau, final ProgressBar progressBar) {
        final List<QuanAnModel> quanAnModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerViewODau.setLayoutManager(layoutManager);
        adapterRecycleODau = new AdapterRecycleODau(context, quanAnModelList, R.layout.custom_layoyt_recyclerview_odau);
        recyclerViewODau.setAdapter(adapterRecycleODau);
        progressBar.setVisibility(View.VISIBLE);
        final ODauInterface oDauInterface = new ODauInterface() {
            @Override
            public void getDanhSachQuanAnModel(final QuanAnModel quanAnModel) {
                final List<Bitmap> bitmaps = new ArrayList<>();
                for (String linkhinh : quanAnModel.getHinhquanan()) {
                    StorageReference storageHinhanh = FirebaseStorage.getInstance().getReference().child("hinhquanan").child(linkhinh);
                    long ONE_MEGABYTE = 1024 * 1024;
                    storageHinhanh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            bitmaps.add(bitmap);
                            quanAnModel.setBitmapList(bitmaps);
                            if (quanAnModel.getBitmapList().size() == quanAnModel.getHinhquanan().size()) {
                                quanAnModelList.add(quanAnModel);
                                adapterRecycleODau.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    });
                }

            }
        };
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if (scrollY >= (v.getChildAt(v.getChildCount() - 1)).getMeasuredHeight() - v.getMeasuredHeight()) {
                        itemdaco += 3;
                        quanAnModel.getDanhSachQuanAn(oDauInterface, vitrihientai, itemdaco, itemdaco - 3);
                    }

                }
            }
        });


        quanAnModel.getDanhSachQuanAn(oDauInterface, vitrihientai, itemdaco, 0);
    }
}
