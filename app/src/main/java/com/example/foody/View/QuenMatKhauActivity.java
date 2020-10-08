package com.example.foody.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foody.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class QuenMatKhauActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edEmail;
    TextView txtDangKiMoi;
    Button btnKhoiPhuc;
    FirebaseAuth firebaseAuth;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);
        edEmail = findViewById(R.id.edEmailKhoiPhuc);
        btnKhoiPhuc = findViewById(R.id.btnKhoiPhuc);
        txtDangKiMoi = findViewById(R.id.txtDangKiMoi);
        firebaseAuth = FirebaseAuth.getInstance();
        txtDangKiMoi.setOnClickListener(this);
        btnKhoiPhuc.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnKhoiPhuc:
                KhoiPhucMatKhau();
                break;
            case R.id.txtDangKiMoi:
                Intent iDangKi = new Intent(QuenMatKhauActivity.this, DangKiActivity.class);
                startActivity(iDangKi);
                break;
        }
    }

    private void KhoiPhucMatKhau() {
        email = edEmail.getText().toString().trim();
        if (!validateEmail(email)) {
            return;
        } else {
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(QuenMatKhauActivity.this, getString(R.string.thongbaoguiemailthanhcong), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            edEmail.setError(getString(R.string.emailempty));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edEmail.setError(getString(R.string.emailwrong));
            return false;
        } else {
            edEmail.setError(null);
            return true;
        }
    }
}