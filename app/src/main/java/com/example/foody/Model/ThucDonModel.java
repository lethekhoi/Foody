package com.example.foody.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foody.Controller.Interface.ThucDonInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ThucDonModel {
    List<MonAnModel> monAnModelList;

    public List<MonAnModel> getMonAnModelList() {
        return monAnModelList;
    }

    public void setMonAnModelList(List<MonAnModel> monAnModelList) {
        this.monAnModelList = monAnModelList;
    }

    String maThucDon, tenThucDon;

    public String getMaThucDon() {
        return maThucDon;
    }

    public void setMaThucDon(String maThucDon) {
        this.maThucDon = maThucDon;
    }

    public String getTenThucDon() {
        return tenThucDon;
    }

    public void setTenThucDon(String tenThucDon) {
        this.tenThucDon = tenThucDon;
    }

    public ThucDonModel() {
    }

    public ThucDonModel(String maThucDon, String tenThucDon) {
        this.maThucDon = maThucDon;
        this.tenThucDon = tenThucDon;
    }

    public void GetDanhSachThucDonTheoMaQuanAn(String maquanan, ThucDonInterface thucDonInterface) {

        DatabaseReference nodeThucDonQuanAn = FirebaseDatabase.getInstance().getReference().child("thucdonquanans").child(maquanan);
        nodeThucDonQuanAn.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ThucDonModel> thucDonModelList = new ArrayList<>();
                for (DataSnapshot dataThucDon : snapshot.getChildren()) {
                    ThucDonModel thucDonModel = new ThucDonModel();

                    //lấy tên thực đơn và mã thực đơn
                    DatabaseReference nodeThucDon = FirebaseDatabase.getInstance().getReference().child("thucdons").child(dataThucDon.getKey());
                    nodeThucDon.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshotThucDon) {

                            thucDonModel.setTenThucDon(snapshotThucDon.getValue() + "");
                            thucDonModel.setMaThucDon(snapshotThucDon.getKey() + "");
                            List<MonAnModel> monAnModelList = new ArrayList<>();
                            for (DataSnapshot dataMonAn : snapshot.child(snapshotThucDon.getKey()).getChildren()) {
                                MonAnModel monAnModel = dataMonAn.getValue(MonAnModel.class);
                                monAnModel.setMamon(dataMonAn.getKey());
                                Log.d("kiemtra", monAnModel.getTenmon());
                                monAnModelList.add(monAnModel);
                            }
                            thucDonModel.setMonAnModelList(monAnModelList);
                            thucDonModelList.add(thucDonModel);
                            thucDonInterface.GetThucDonThanhCong(thucDonModelList);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}

