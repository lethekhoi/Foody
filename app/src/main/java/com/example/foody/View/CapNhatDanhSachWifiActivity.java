package com.example.foody.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.foody.Controller.CapNhatDanhSachWifiController;
import com.example.foody.R;

public class CapNhatDanhSachWifiActivity extends AppCompatActivity {
    RecyclerView recyclerDanhSachWifi;
    Button btnCapNhat;
    String maquanan;
    boolean onPause = false;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_danh_sach_wifi);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("tenquanan"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        maquanan = getIntent().getStringExtra("maquanan");
        btnCapNhat = findViewById(R.id.btnCapNhatWifi);
        recyclerDanhSachWifi = findViewById(R.id.recyclerViewWifi);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerDanhSachWifi.setLayoutManager(layoutManager);

        CapNhatDanhSachWifiController capNhatDanhSachWifiController = new CapNhatDanhSachWifiController(CapNhatDanhSachWifiActivity.this);
        capNhatDanhSachWifiController.HienThiDanhSachWifiQuanAn(maquanan, recyclerDanhSachWifi);
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iCapNhatWifi = new Intent(CapNhatDanhSachWifiActivity.this, PopUpCapNhatWifiActivity.class);
                iCapNhatWifi.putExtra("maquanan", maquanan);
                startActivity(iCapNhatWifi);
            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        onPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (onPause) {
            CapNhatDanhSachWifiController capNhatDanhSachWifiController = new CapNhatDanhSachWifiController(CapNhatDanhSachWifiActivity.this);
            capNhatDanhSachWifiController.HienThiDanhSachWifiQuanAn(maquanan, recyclerDanhSachWifi);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}