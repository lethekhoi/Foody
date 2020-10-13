package com.example.foody.Model;

import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foody.Controller.Interface.ODauInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class QuanAnModel {
    DatabaseReference dataRoot;
    long luotthich;
    boolean giaohang;
    String giodongcua, giomocua, videogioithieu, maquanan, tenquanan;
    List<String> tienich;
    List<BinhLuanModel> binhLuanModelList;
    List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList;

    public List<ChiNhanhQuanAnModel> getChiNhanhQuanAnModelList() {
        return chiNhanhQuanAnModelList;
    }

    public void setChiNhanhQuanAnModelList(List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList) {
        this.chiNhanhQuanAnModelList = chiNhanhQuanAnModelList;
    }

    public List<BinhLuanModel> getBinhLuanModelList() {
        return binhLuanModelList;
    }

    public void setBinhLuanModelList(List<BinhLuanModel> binhLuanModelList) {
        this.binhLuanModelList = binhLuanModelList;
    }

    List<String> hinhquanan;

    public List<String> getHinhquanan() {
        return hinhquanan;
    }

    public void setHinhquanan(List<String> hinhquanan) {
        this.hinhquanan = hinhquanan;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public String getTenquanan() {
        return tenquanan;
    }

    public void setTenquanan(String tenquanan) {
        this.tenquanan = tenquanan;
    }


    public QuanAnModel() {
        dataRoot = FirebaseDatabase.getInstance().getReference();
    }

    public boolean isGiaohang() {
        return giaohang;
    }

    public void setGiaohang(boolean giaohang) {
        this.giaohang = giaohang;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getVideogioithieu() {
        return videogioithieu;
    }

    public void setVideogioithieu(String videogioithieu) {
        this.videogioithieu = videogioithieu;
    }

    public String getMaquanan() {
        return maquanan;
    }

    public void setMaquanan(String maquanan) {
        this.maquanan = maquanan;
    }

    public void getDanhSachQuanAn(final ODauInterface oDauInterface, final Location vitrihientai) {

        final ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                DataSnapshot dataSnapshotQuanAn = snapshot.child("quanans");
                //lấy danh sách quán ăn
                for (DataSnapshot valueQuanAn : dataSnapshotQuanAn.getChildren()) {
                    QuanAnModel quanAnModel = valueQuanAn.getValue(QuanAnModel.class);
                    quanAnModel.setMaquanan(valueQuanAn.getKey());
                    //lấy danh sách hình ảnh của quán ăn theo mã
                    DataSnapshot dataSnapshotHinhQuanAn = snapshot.child("hinhanhquanans").child(valueQuanAn.getKey());
                    List<String> hinhQuanAn = new ArrayList<>();

                    for (DataSnapshot valueHinhQuanAn : dataSnapshotHinhQuanAn.getChildren()) {
                        hinhQuanAn.add(valueHinhQuanAn.getValue().toString());

                    }
                    //lấy danh sách bình luận theo mã quán ăn
                    DataSnapshot dataSnapshotBinhLuanQuanAn = snapshot.child("binhluans").child(valueQuanAn.getKey());
                    List<BinhLuanModel> binhLuanModelList1 = new ArrayList<>();

                    for (DataSnapshot valueBinhLuan : dataSnapshotBinhLuanQuanAn.getChildren()) {
                        //set thông tin bình luận
                        BinhLuanModel binhLuanModel = valueBinhLuan.getValue(BinhLuanModel.class);
                        //set mã bình luận
                        binhLuanModel.setMabinhluan(valueBinhLuan.getKey());
                        //set thành viên bình luận
                        ThanhVienModel thanhVienModel = snapshot.child("thanhviens").child(binhLuanModel.getMauser()).getValue(ThanhVienModel.class);
                        //set  list hình ảnh bình luận
                        List<String> hinhanhBinhLuanList = new ArrayList<>();
                        DataSnapshot snapshotNodeHinhAnh = snapshot.child("hinhanhbinhluans").child(binhLuanModel.getMabinhluan());

                        for (DataSnapshot valueHinhBinhLuan : snapshotNodeHinhAnh.getChildren()) {
                            hinhanhBinhLuanList.add(valueHinhBinhLuan.getValue(String.class));

                        }

                        binhLuanModel.setListHinhBinhLuan(hinhanhBinhLuanList);
                        Log.d("binhluanne", binhLuanModel.toString());
                        binhLuanModel.setThanhVienModel(thanhVienModel);
                        binhLuanModelList1.add(binhLuanModel);
                    }


                    //lấy list chi nhánh quán ăn theo mã quán ăn

                    DataSnapshot dataChiNhanhQuanAn = snapshot.child("chinhanhquanans").child(valueQuanAn.getKey());
                    List<ChiNhanhQuanAnModel> chiNhanhQuanAnModels = new ArrayList<>();
                    for (DataSnapshot valueChiNhanh : dataChiNhanhQuanAn.getChildren()) {
                        ChiNhanhQuanAnModel chiNhanhQuanAnModel = valueChiNhanh.getValue(ChiNhanhQuanAnModel.class);
                        Location vitriquanan = new Location("");
                        vitriquanan.setLatitude(chiNhanhQuanAnModel.getLatitude());
                        vitriquanan.setLongitude(chiNhanhQuanAnModel.getLongitude());
                        double khoangcach = vitrihientai.distanceTo(vitriquanan) / 1000;
                        chiNhanhQuanAnModel.setKhoangcach(khoangcach);
                        chiNhanhQuanAnModels.add(chiNhanhQuanAnModel);
                    }


                    //set hình cho quán ăn
                    quanAnModel.setHinhquanan(hinhQuanAn);
                    //set list chi nhánh quán ăn
                    quanAnModel.setChiNhanhQuanAnModelList(chiNhanhQuanAnModels);
                    //set binh luận cho quán ăn
                    quanAnModel.setBinhLuanModelList(binhLuanModelList1);
                    Log.d("kiemtraquanan", quanAnModel.getMaquanan().toString());
                    oDauInterface.getDanhSachQuanAnModel(quanAnModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        dataRoot.addListenerForSingleValueEvent(valueEventListener);
    }
}
