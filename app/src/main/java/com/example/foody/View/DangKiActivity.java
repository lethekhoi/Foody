package com.example.foody.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foody.Controller.DangKiController;
import com.example.foody.Model.ThanhVienModel;
import com.example.foody.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class DangKiActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth firebaseAuth;
    EditText edtEmailDK, edtMatKhau, edtNhapLaiMatKhau;
    Button btnDangKi;
    String email, matkhau, nhaplaimatkhau;
    ProgressDialog progressDialog;
    DangKiController dangKiController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        firebaseAuth = FirebaseAuth.getInstance();

        Anhxa();
        btnDangKi.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

    }

    private void Anhxa() {
        edtEmailDK = findViewById(R.id.edEmailDK);
        edtMatKhau = findViewById(R.id.edPasswordDK);
        edtNhapLaiMatKhau = findViewById(R.id.edRewritePasswordDK);
        btnDangKi = findViewById(R.id.btnDangKi);
    }

    @Override
    public void onClick(View view) {

        email = edtEmailDK.getText().toString();
        matkhau = edtMatKhau.getText().toString();
        nhaplaimatkhau = edtNhapLaiMatKhau.getText().toString();
        if (!validateEmail(email) | !validatePassword(matkhau) | !validateNhapLaiPassword(nhaplaimatkhau, matkhau)) {
            return;

        } else {
            progressDialog.setMessage(getString(R.string.dangxuli));
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email, matkhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    ThanhVienModel thanhVienModel = new ThanhVienModel(email, "user.png");
                    String userID = task.getResult().getUser().getUid();
                    dangKiController = new DangKiController();
                    dangKiController.ThemThongTinThanhVienController(thanhVienModel, userID);
                    progressDialog.dismiss();
                    Toast.makeText(DangKiActivity.this, getString(R.string.dangkithanhcong), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            edtEmailDK.setError(getString(R.string.emailempty));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmailDK.setError(getString(R.string.emailwrong));
            return false;
        } else {
            edtEmailDK.setError(null);
            return true;
        }
    }

    private boolean validatePassword(String matkhau) {
        if (matkhau.isEmpty()) {
            edtMatKhau.setError(getString(R.string.matkhauempty));
            return false;
        } else if (matkhau.trim().length() < 6) {
            edtMatKhau.setError(getString(R.string.matkhaungan));
            return false;
        } else {
            edtMatKhau.setError(null);
            return true;
        }
    }

    private boolean validateNhapLaiPassword(String nhaplaimatkhau, String matkhau) {
        if (nhaplaimatkhau.isEmpty()) {
            edtNhapLaiMatKhau.setError(getString(R.string.matkhauempty));
            return false;
        } else if (!matkhau.trim().equals(nhaplaimatkhau.trim())) {
            edtNhapLaiMatKhau.setError(getString(R.string.matkhaunhapnotequal));
            return false;
        } else {
            edtMatKhau.setError(null);
            return true;
        }
    }

}
