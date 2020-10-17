package com.example.foody.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.foody.Adapter.AdapterRecyclerBinhLuan;
import com.example.foody.Controller.ChiTietQuanAnController;
import com.example.foody.Model.QuanAnModel;
import com.example.foody.Model.TienIchModel;
import com.example.foody.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChiTietQuanAnActivity extends AppCompatActivity implements OnMapReadyCallback {
    QuanAnModel quanAnModel;
    TextView txtTenQuanAnChiTiet, txtDiaChiQuanAnChiTiet, txtTongAnh, txtTongBinhLuan,
            txtThoiGianMoCua, txtDongMoCua, txtGioiHanGia, txtTenWifi, txtMatKhauWifi;
    ImageView imgHinhQuanAn;
    RecyclerView recyclerViewBinhLuan;
    NestedScrollView nestedScrollViewChiTiet;
    GoogleMap googleMap;
    LinearLayout khungWifi;
    SupportMapFragment mapFragment;
    Toolbar toolbar;
    List<String> maTienIchList;
    DatabaseReference dataRoot;
    long giatoida, giatoithieu;
    LinearLayout khungTienIch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_quan_an);
        quanAnModel = getIntent().getParcelableExtra("quanan");
        AnhXa();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void AnhXa() {
        khungWifi = findViewById(R.id.khungWifi);
        txtTenWifi = findViewById(R.id.tenWifi);
        txtMatKhauWifi = findViewById(R.id.matkhauwifi);
        khungTienIch = findViewById(R.id.khungTienTich);
        txtGioiHanGia = findViewById(R.id.txtGioiHanGia);
        maTienIchList = new ArrayList<>();
        toolbar = findViewById(R.id.toolbar);
        nestedScrollViewChiTiet = findViewById(R.id.nestScrollViewChiTiet);
        imgHinhQuanAn = findViewById(R.id.imgHinhQuanAn);
        txtTenQuanAnChiTiet = findViewById(R.id.txtTenQuanAnChiTiet);
        txtDiaChiQuanAnChiTiet = findViewById(R.id.txtDiaChiQuanAnChiTiet);
        txtTongAnh = findViewById(R.id.txtTongAnh);
        txtTongBinhLuan = findViewById(R.id.txtTongBinhLuan);
        txtThoiGianMoCua = findViewById(R.id.txtThoiGianMoCua);
        txtDongMoCua = findViewById(R.id.txtDongMoCua);
        recyclerViewBinhLuan = findViewById(R.id.recyclerViewBinhLuan);
        recyclerViewBinhLuan.setFocusable(false);
        toolbar.setTitle(quanAnModel.getTenquanan());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (quanAnModel.getGiatoida() != 0 && quanAnModel.getGiatoithieu() != 0) {
            txtGioiHanGia.setText(quanAnModel.getGiatoithieu() + " - " + quanAnModel.getGiatoida());
        } else {
            txtGioiHanGia.setText("");
        }
        //set Hinh tien ich
        DownloadHinhTienIch();
        //danh sách wifi
        ChiTietQuanAnController chiTietQuanAnController = new ChiTietQuanAnController();
        chiTietQuanAnController.HienThiDanhSachWifiQuanAn(quanAnModel.getMaquanan(), txtTenWifi, txtMatKhauWifi);
        khungWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iCapNhatWifi = new Intent(ChiTietQuanAnActivity.this, CapNhatDanhSachWifiActivity.class);
                iCapNhatWifi.putExtra("maquanan", quanAnModel.getMaquanan());
                iCapNhatWifi.putExtra("tenquanan", quanAnModel.getTenquanan());
                startActivity(iCapNhatWifi);
            }
        });


    }

    private void DownloadHinhTienIch() {
        final List<Bitmap> bitmaps = new ArrayList<>();

        for (String matienich : quanAnModel.getTienich()) {
            Log.d("matienich", matienich);
            DatabaseReference nodeTienIch = FirebaseDatabase.getInstance().getReference().child("quanlytienichs").child(matienich);
            nodeTienIch.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    TienIchModel tienIchModel = dataSnapshot.getValue(TienIchModel.class);

                    StorageReference storageHinhQuanAn = FirebaseStorage.getInstance().getReference().child("hinhtienich").child(tienIchModel.getHinhtienich());
                    long ONE_MEGABYTE = 1024 * 1024;
                    storageHinhQuanAn.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            ImageView imageTienIch = new ImageView(ChiTietQuanAnActivity.this);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50, 50);
                            layoutParams.setMargins(10, 10, 10, 10);
                            imageTienIch.setLayoutParams(layoutParams);
                            imageTienIch.setScaleType(ImageView.ScaleType.FIT_XY);
                            imageTienIch.setPadding(5, 5, 5, 5);


                            imageTienIch.setImageBitmap(bitmap);
                            khungTienIch.addView(imageTienIch);
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    private void init() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String giohientai = dateFormat.format(calendar.getTime());
        String giomocua = quanAnModel.getGiomocua();
        String giodongcua = quanAnModel.getGiodongcua();

        try {
            Date dateHienTai = dateFormat.parse(giohientai);
            Date dateMoCua = dateFormat.parse(giomocua);
            Date dateDongCua = dateFormat.parse(giodongcua);


            if (dateHienTai.after(dateMoCua) && dateHienTai.before(dateDongCua)) {
                txtDongMoCua.setText(getString(R.string.mocua));
                txtDongMoCua.setTextColor(getColor(R.color.colorGreen));
            } else {
                txtDongMoCua.setText(getString(R.string.dongcua));
                txtDongMoCua.setTextColor(getColor(R.color.colorPrimaryDark));
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

        txtTenQuanAnChiTiet.setText(quanAnModel.getTenquanan());
        txtDiaChiQuanAnChiTiet.setText(quanAnModel.getChiNhanhQuanAnModelList().get(0).getDiachi());
        txtTongAnh.setText(quanAnModel.getHinhquanan().size() + "");
        txtTongBinhLuan.setText(quanAnModel.getBinhLuanModelList().size() + "");
        txtThoiGianMoCua.setText(quanAnModel.getGiomocua() + " - " + quanAnModel.getGiodongcua());


        StorageReference storageHinhanh = FirebaseStorage.getInstance().getReference().child("hinhquanan").child(quanAnModel.getHinhquanan().get(0));
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhanh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imgHinhQuanAn.setImageBitmap(bitmap);

            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ChiTietQuanAnActivity.this);
        recyclerViewBinhLuan.setLayoutManager(layoutManager);
        AdapterRecyclerBinhLuan adapterRecyclerBinhLuan = new AdapterRecyclerBinhLuan(ChiTietQuanAnActivity.this, quanAnModel.getBinhLuanModelList());
        recyclerViewBinhLuan.setAdapter(adapterRecyclerBinhLuan);
        adapterRecyclerBinhLuan.notifyDataSetChanged();
        nestedScrollViewChiTiet.scrollTo(0, 0);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        // Add a marker in Sydney and move the camera
        Double latitude = quanAnModel.getChiNhanhQuanAnModelList().get(0).getLatitude();
        Double longitude = quanAnModel.getChiNhanhQuanAnModelList().get(0).getLongitude();
        String title = quanAnModel.getChiNhanhQuanAnModelList().get(0).getDiachi();
        LatLng sydney = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title(title));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14));

    }
}