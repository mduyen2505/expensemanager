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
import com.example.expensemanager.model.MainActivity;

public class LoginActivity extends AppCompatActivity {
    TextView registerText;
    Button loginButton;
    private SQLiteHelper dbHelper;
    private EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize database helper
        dbHelper = new SQLiteHelper(this);

        // Initialize views
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerText = findViewById(R.id.registerText);

        // Set on click listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng điền đầy đủ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dbHelper.checkUser(username, password)) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set on click listener for register text
        registerText.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }
}
