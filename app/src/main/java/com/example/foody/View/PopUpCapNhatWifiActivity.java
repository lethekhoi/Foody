package com.example.foody.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foody.Controller.CapNhatDanhSachWifiController;
import com.example.foody.Model.WifiQuanAnModel;
import com.example.foody.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PopUpCapNhatWifiActivity extends AppCompatActivity {
    EditText edtTenWifi, edtMatKhauWifi;
    Button btnCapNhat;
    String tenWifi, matkhauWifi;
    CapNhatDanhSachWifiController capNhatDanhSachWifiController;
    String maquanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_cap_nhat_wifi);
        maquanan = getIntent().getStringExtra("maquanan");
        edtTenWifi = findViewById(R.id.nhaptenwifi);
        edtMatKhauWifi = findViewById(R.id.nhapmatkhauwifi);
        btnCapNhat = findViewById(R.id.btnCapNhat);
        capNhatDanhSachWifiController = new CapNhatDanhSachWifiController(PopUpCapNhatWifiActivity.this);
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tenWifi = edtTenWifi.getText().toString().trim();
                matkhauWifi = edtMatKhauWifi.getText().toString().trim();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String ngaydang = simpleDateFormat.format(calendar.getTime());
                if (tenWifi.length() > 0 && matkhauWifi.length() > 0) {

                    WifiQuanAnModel wifiQuanAnModel = new WifiQuanAnModel(tenWifi, matkhauWifi, ngaydang);
                    capNhatDanhSachWifiController.ThemWifi(PopUpCapNhatWifiActivity.this, wifiQuanAnModel, maquanan);
                    finish();

                } else {
                    Toast.makeText(PopUpCapNhatWifiActivity.this, "Chưa nhập đầy đủ", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}