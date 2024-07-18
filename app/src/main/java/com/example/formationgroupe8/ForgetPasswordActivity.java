package com.example.formationgroupe8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ForgetPasswordActivity extends AppCompatActivity {
    private Button backtosignup1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        backtosignup1 = findViewById(R.id.backtosignup);

        backtosignup1.setOnClickListener(v -> {
            startActivity(new Intent(ForgetPasswordActivity.this,SignUpActivity.class));
        });

    }
}