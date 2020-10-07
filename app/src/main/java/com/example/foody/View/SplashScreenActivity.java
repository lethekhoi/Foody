package com.example.foody.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.foody.R;

public class SplashScreenActivity extends AppCompatActivity {
    TextView txtVersion, txtTenCongTy, txtLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splashscreen);
        AnhXa();
        init();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {

                } finally {
                    Intent iDangNhap = new Intent(SplashScreenActivity.this, DangNhapActivity.class);
                    startActivity(iDangNhap);
                    finish();
                }
                ;
            }
        });
        thread.start();

    }

    private void init() {
        try {
            PackageInfo packageInfo= getPackageManager().getPackageInfo(getPackageName(), 0);
            txtVersion.setText(getString(R.string.phienban)+ " "+ packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        txtLoading.setText(getString(R.string.dangtai));
        txtTenCongTy.setText(getString(R.string.tencongty));
    }

    private void AnhXa() {
        txtVersion= findViewById(R.id.txtVersion);
        txtLoading= findViewById(R.id.txtLoading);
        txtTenCongTy= findViewById(R.id.txtTenCongTy);
    }
}