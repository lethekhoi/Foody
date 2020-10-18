package com.example.foody.View;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.LinearGradient;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foody.R;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.List;

public class DangNhapActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, FirebaseAuth.AuthStateListener {

    SignInButton btnDangNhapGoogle;

    Button btnDangNhap, btnFacebook;
    TextView txtDangKyMoi, txtQuenMatKhau;
    EditText edEmail, edPassword;
    String email, matkhau;
    GoogleApiClient apiClient;
    public static int REQUESTCODE_DANGNHAP_GOOGLE = 99;
    public static int KIEMTRA_PROVIDER_DANGNHAP = 0;
    FirebaseAuth firebaseAuth;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        txtDangKyMoi = findViewById(R.id.txtDangKiMoi);
        txtQuenMatKhau = findViewById(R.id.txtQuenMatKhau);
        firebaseAuth = FirebaseAuth.getInstance();
        btnDangNhap = findViewById(R.id.buttonDangNhap);
        btnDangNhapGoogle = findViewById(R.id.sign_in_button);
        edEmail = findViewById(R.id.edEmailDN);
        edPassword = findViewById(R.id.edPasswordDN);
        btnDangNhapGoogle.setOnClickListener(this);
        txtDangKyMoi.setOnClickListener(this);
        txtQuenMatKhau.setOnClickListener(this);
        btnDangNhap.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("luudangnhap", MODE_PRIVATE);
        TaoClientDangNhapGoogle();


    }


    //Khởi tạo client cho đăng nhập google
    private void TaoClientDangNhapGoogle() {
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build();

    }

    //end hởi tạo client cho đăng nhập google

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }

    //Mở form đăng nhập bằng google
    private void DangNhapGoogle(GoogleApiClient apiClient) {
        KIEMTRA_PROVIDER_DANGNHAP = 1;
        Intent iDNGoogle = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        startActivityForResult(iDNGoogle, REQUESTCODE_DANGNHAP_GOOGLE);
    }

    //end mở form đăng nhập bằng google

    //Lấy stokenID đã đăng nhập bằng google để đăng nhập trên Firebase
    private void ChungThucDangNhapFireBase(String tokenID) {
        if (KIEMTRA_PROVIDER_DANGNHAP == 1) {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(tokenID, null);
            firebaseAuth.signInWithCredential(authCredential);
        }

    }
    //end Lấy stokenID đã đăng nhập bằng google để đăng nhập trên Firebase

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE_DANGNHAP_GOOGLE) {
            if (resultCode == RESULT_OK) {
                GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                GoogleSignInAccount account = signInResult.getSignInAccount();
                String tokenID = account.getIdToken();
                ChungThucDangNhapFireBase(tokenID);
            }
        } else {
            //mCallbackFacebook.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    //Lắng nghe sự kiện user click vào button đăng nhập google, facebook và email account
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.sign_in_button:
                DangNhapGoogle(apiClient);
                break;
            case R.id.txtDangKiMoi:
                Intent iDangKi = new Intent(DangNhapActivity.this, DangKiActivity.class);
                startActivity(iDangKi);
                break;
            case R.id.buttonDangNhap:
                DangNhap();
                break;
            case R.id.txtQuenMatKhau:
                Intent iQuenMatKhau = new Intent(DangNhapActivity.this, QuenMatKhauActivity.class);
                startActivity(iQuenMatKhau);
                break;
        }
    }

    private void DangNhap() {
        email = edEmail.getText().toString().trim();
        matkhau = edPassword.getText().toString().trim();

        if (!validateEmail(email) | !validatePassword(matkhau)) {
            return;
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, matkhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(DangNhapActivity.this, getString(R.string.dangnhapthatbai), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    //end


    //Sự kiện kiểm tra xem người dùng đã nhập thành công hay đăng xuất
    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("mauser", user.getUid());
            editor.commit();
            Intent iTrangChu = new Intent(this, TrangChuActivity.class);
            startActivity(iTrangChu);
        } else {

        }
    }

    //End
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

    private boolean validatePassword(String matkhau) {
        if (matkhau.isEmpty()) {
            edPassword.setError(getString(R.string.matkhauempty));
            return false;
        } else if (matkhau.trim().length() < 6) {
            edPassword.setError(getString(R.string.matkhaungan));
            return false;
        } else {
            edPassword.setError(null);
            return true;
        }
    }
}
