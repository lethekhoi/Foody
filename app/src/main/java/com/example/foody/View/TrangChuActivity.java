package com.example.foody.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.foody.Adapter.AdapterViewPagerTrangChu;
import com.example.foody.R;

public class TrangChuActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
    ViewPager viewPagerTrangChu;
    RadioButton rbAnGi, rbODau;
    RadioGroup rbGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        Anhxa();
        AdapterViewPagerTrangChu adapterViewPagerTrangChu = new AdapterViewPagerTrangChu(getSupportFragmentManager());
        viewPagerTrangChu.setAdapter(adapterViewPagerTrangChu);
        viewPagerTrangChu.addOnPageChangeListener(this);
        rbGroup.setOnCheckedChangeListener(this);
    }

    private void Anhxa() {
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
}