package com.example.foody.View;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.foody.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class SplashScreenActivity extends AppCompatActivity {
    TextView txtVersion, txtTenCongTy, txtLoading;
    SharedPreferences sharedPreferences;
    FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splashscreen);
        AnhXa();
        init();
        sharedPreferences = getSharedPreferences("toado", MODE_PRIVATE);
        client = LocationServices.getFusedLocationProviderClient(SplashScreenActivity.this);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {

                } finally {
                    Intent iDangNhap = new Intent(SplashScreenActivity.this, DangNhapActivity.class);
                    startActivity(iDangNhap);
                    //xin quyền lấy location
                    if (ActivityCompat.checkSelfPermission(SplashScreenActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        getCurrentLocation();

                    } else {

                        ActivityCompat.requestPermissions(SplashScreenActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                    }
                    finish();
                }

            }
        });
        thread.start();

    }

    private void getCurrentLocation() {
        @SuppressLint("MissingPermission") Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Log.d("toado", location.getLatitude() + "");
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("latitude", String.valueOf(location.getLatitude()));
                    editor.putString("longitude", String.valueOf(location.getLongitude()));
                    editor.commit();


                }
            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }

    private void init() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            txtVersion.setText(getString(R.string.phienban) + " " + packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        txtLoading.setText(getString(R.string.dangtai));
        txtTenCongTy.setText(getString(R.string.tencongty));
    }

    private void AnhXa() {
        txtVersion = findViewById(R.id.txtVersion);
        txtLoading = findViewById(R.id.txtLoading);
        txtTenCongTy = findViewById(R.id.txtTenCongTy);
    }
}