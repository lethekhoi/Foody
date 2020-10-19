package com.example.foody.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.foody.Adapter.AdapterViewPagerTrangChu;
import com.example.foody.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TrangChuActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
    ViewPager viewPagerTrangChu;
    RadioButton rbAnGi, rbODau;
    RadioGroup rbGroup;
    Menu menu;
    Toolbar toolbar;
    FirebaseAuth auth;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        Anhxa();
        sharedPreferences = getSharedPreferences("luudangnhap", MODE_PRIVATE);
        auth = FirebaseAuth.getInstance();
        AdapterViewPagerTrangChu adapterViewPagerTrangChu = new AdapterViewPagerTrangChu(getSupportFragmentManager());
        viewPagerTrangChu.setAdapter(adapterViewPagerTrangChu);
        viewPagerTrangChu.addOnPageChangeListener(this);
        rbGroup.setOnCheckedChangeListener(this);
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPagerTrangChu = findViewById(R.id.viewPagerTrangChu);
        rbAnGi = findViewById(R.id.rb_angi);
        rbODau = findViewById(R.id.rb_odau);
        rbGroup = findViewById(R.id.rbGroup);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                rbODau.setChecked(true);
                break;
            case 1:
                rbAnGi.setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        switch (id) {
            case R.id.rb_odau:
                viewPagerTrangChu.setCurrentItem(0);
                break;
            case R.id.rb_angi:
                viewPagerTrangChu.setCurrentItem(1);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trang_chu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itDangXuat:
                auth.signOut();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("mauser", "");
                editor.commit();
                Intent iDangNhap = new Intent(TrangChuActivity.this, DangNhapActivity.class);
                startActivity(iDangNhap);
                break;
        }
        return true;
    }
}