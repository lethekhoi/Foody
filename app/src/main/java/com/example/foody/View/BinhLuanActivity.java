package com.example.foody.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foody.Adapter.AdapterPhoto;
import com.example.foody.Controller.BinhLuanController;
import com.example.foody.Model.BinhLuanModel;
import com.example.foody.Model.QuanAnModel;
import com.example.foody.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;


public class BinhLuanActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView btnPost;
    QuanAnModel quanAnModel;
    TextView txtTenQuanAn, txtDiaChiQuanAn;
    ImageButton btnHinhAnh;
    final int REQUEST_CHONHINH = 11;
    RecyclerView recyclerViewHinhDuocChon;
    AdapterPhoto adapterPhoto;
    List<Uri> listUri;
    BinhLuanModel binhLuanModel;
    BinhLuanController binhLuanController;
    EditText edtTieuDe, edtNoiDung;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binh_luan);
        quanAnModel = getIntent().getParcelableExtra("quanan");
        AnhXa();
        init();
        toolbar.setTitle(R.string.vietbinhluan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void init() {
        txtTenQuanAn.setText(quanAnModel.getTenquanan());
        txtDiaChiQuanAn.setText(quanAnModel.getChiNhanhQuanAnModelList().get(0).getDiachi());

        adapterPhoto = new AdapterPhoto(this);
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHinhDuocChon.setLayoutManager(linearLayoutManager);
        recyclerViewHinhDuocChon.setFocusable(false);
        recyclerViewHinhDuocChon.setAdapter(adapterPhoto);
        btnHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mauser = sharedPreferences.getString("mauser", "");
                binhLuanModel.setTieude(edtTieuDe.getText().toString().trim());
                binhLuanModel.setChamdiem(0);
                binhLuanModel.setNoidung(edtNoiDung.getText().toString().trim());
                binhLuanModel.setMauser(mauser);
                binhLuanModel.setLuotthich(0);

                binhLuanController.ThemBinhLuan(quanAnModel.getMaquanan(), binhLuanModel, listUri);
            }
        });

    }

    private void requestPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openBottomPicker();
                Toast.makeText(BinhLuanActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(BinhLuanActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private void openBottomPicker() {
        TedBottomPicker.OnMultiImageSelectedListener listener = new TedBottomPicker.OnMultiImageSelectedListener() {
            @Override
            public void onImagesSelected(ArrayList<Uri> uriList) {

                adapterPhoto.setData(uriList);
                listUri = uriList;
            }
        };
        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(BinhLuanActivity.this)
                .setOnMultiImageSelectedListener(listener)
                .setCompleteButtonText(R.string.done)
                .showTitle(true)
                .setGalleryTileBackgroundResId(R.color.colorPrimaryDark)
                .setEmptySelectionText("Chưa chọn hình")
                .create();
        tedBottomPicker.show(getSupportFragmentManager());
    }

    private void AnhXa() {
        sharedPreferences = getSharedPreferences("luudangnhap", MODE_PRIVATE);
        edtTieuDe = findViewById(R.id.edtTieuDe);
        edtNoiDung = findViewById(R.id.edtNoiDung);
        binhLuanController = new BinhLuanController();
        binhLuanModel = new BinhLuanModel();
        btnPost = findViewById(R.id.btnPost);
        listUri = new ArrayList<>();
        recyclerViewHinhDuocChon = findViewById(R.id.recyclerViewHinhAnhDuocChon);
        btnHinhAnh = findViewById(R.id.btnHinhAnh);
        toolbar = findViewById(R.id.toolbar);
        txtTenQuanAn = findViewById(R.id.txtTenQuanAn);
        txtDiaChiQuanAn = findViewById(R.id.txtDiaChiQuanAn);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}