package com.example.expensemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemanager.dal.SQLiteHelper;

public class SignupActivity extends AppCompatActivity {
    private SQLiteHelper dbHelper;
    private EditText usernameEditText, passwordEditText, repasswordEditText;
    private Button dangky;
    private TextView returnDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        dbHelper = new SQLiteHelper(this);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        repasswordEditText = findViewById(R.id.repassword);
        dangky = findViewById(R.id.registerButton);
        returnDangNhap=findViewById(R.id.loginText);
        returnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        });
        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String repassword = repasswordEditText.getText().toString();

                if (username.isEmpty() || password.isEmpty() || repassword.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Vui lòng điền đầy đủ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(repassword)) {
                    Toast.makeText(SignupActivity.this, "Mật khẩu sai", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isInserted = dbHelper.insertUser(username, password);
                if (isInserted==true) {
                    Toast.makeText(SignupActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    // Chuyển đến màn hình đăng nhập hoặc màn hình khác
                } else {
                    Toast.makeText(SignupActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }}