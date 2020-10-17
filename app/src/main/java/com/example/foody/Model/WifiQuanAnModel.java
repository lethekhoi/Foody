package com.example.foody.Model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foody.Controller.Interface.ChiTietQuanAnInterface;
import com.example.foody.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WifiQuanAnModel {
    String ten, matkhau, ngaydang;

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getNgaydang() {
        return ngaydang;
    }

    public void setNgaydang(String ngaydang) {
        this.ngaydang = ngaydang;
    }

    public WifiQuanAnModel() {
    }

    public WifiQuanAnModel(String ten, String matkhau, String ngaydang) {
        this.ten = ten;
        this.matkhau = matkhau;
        this.ngaydang = ngaydang;
    }


    public void LayDanhSachWifiQuanAn(String maquanan, final ChiTietQuanAnInterface chiTietQuanAnInterface) {

        DatabaseReference dataWifiQuanAn = FirebaseDatabase.getInstance().getReference().child("wifiquanans").child(maquanan);


        dataWifiQuanAn.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataWifi : snapshot.getChildren()) {
                        WifiQuanAnModel wifiQuanAnModel = dataWifi.getValue(WifiQuanAnModel.class);
                        chiTietQuanAnInterface.HienThiDanhSachWifi(wifiQuanAnModel);
                    }


                } else {
                    Log.d("Khooi", "khooi");
                    chiTietQuanAnInterface.HienThiThemWifi();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void ThemWifiQuanAn(final Context context, WifiQuanAnModel wifiQuanAnModel, String maquanan) {

        DatabaseReference dataWifiQuanAn = FirebaseDatabase.getInstance().getReference().child("wifiquanans").child(maquanan);
        dataWifiQuanAn.push().setValue(wifiQuanAnModel, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(context, context.getResources().getString(R.string.themwifithanhcong), Toast.LENGTH_SHORT).show();
            }
        });


    }

}
