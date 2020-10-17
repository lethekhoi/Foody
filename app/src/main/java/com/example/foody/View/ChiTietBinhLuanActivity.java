package com.example.foody.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.foody.Adapter.AdapterRecyclerViewHinhBinhLuan;
import com.example.foody.Model.BinhLuanModel;
import com.example.foody.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChiTietBinhLuanActivity extends AppCompatActivity {
    CircleImageView cicleImageUser;
    TextView txtTieuDeBinhLuan, txtNoiDungBinhLuan, txtDiemBinhLuan;
    RecyclerView recyclerViewHinhBinhLuan;
    List<Bitmap> bitmapList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_binh_luan);
        Anhxa();
        final BinhLuanModel binhLuanModel = getIntent().getParcelableExtra("binhluan");
        bitmapList = new ArrayList<>();
        txtTieuDeBinhLuan.setText(binhLuanModel.getTieude());
        txtNoiDungBinhLuan.setText(binhLuanModel.getNoidung());
        txtDiemBinhLuan.setText(binhLuanModel.getChamdiem() + "");
        setHinhAnhBinhLuan(cicleImageUser, binhLuanModel.getThanhVienModel().getHinhanh());


        for (String linkhinh : binhLuanModel.getListHinhBinhLuan()) {
            StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("hinhquanan").child(linkhinh);
            long ONE_MEGABYTE = 1024 * 1024;
            storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    bitmapList.add(bitmap);
                    if (bitmapList.size() == binhLuanModel.getListHinhBinhLuan().size()) {
                        AdapterRecyclerViewHinhBinhLuan adapterRecyclerHinhBinhLuan = new AdapterRecyclerViewHinhBinhLuan(ChiTietBinhLuanActivity.this, bitmapList, R.layout.custom_layout_hinhbinhluan, binhLuanModel, true );
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ChiTietBinhLuanActivity.this, 2);
                        recyclerViewHinhBinhLuan.setLayoutManager(layoutManager);
                        recyclerViewHinhBinhLuan.setAdapter(adapterRecyclerHinhBinhLuan);
                        adapterRecyclerHinhBinhLuan.notifyDataSetChanged();
                    }
                }
            });
        }


    }

    private void Anhxa() {
        cicleImageUser = findViewById(R.id.cicleImageUser);
        txtTieuDeBinhLuan = findViewById(R.id.txtTieuDeBinhLuan);
        txtNoiDungBinhLuan = findViewById(R.id.txtNoiDungBinhLuan);
        txtDiemBinhLuan = findViewById(R.id.txtDiemBinhLuan);
        recyclerViewHinhBinhLuan = findViewById(R.id.recyclerViewHinhBinhLuan);
    }

    private void setHinhAnhBinhLuan(final CircleImageView circleImageView, String linkhinh) {
        StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("thanhvien").child(linkhinh);
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        });
    }
}