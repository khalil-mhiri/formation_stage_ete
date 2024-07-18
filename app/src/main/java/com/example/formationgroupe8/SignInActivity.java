package com.example.formationgroupe8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {

    //Declaration des variables
    private TextView forget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Affectation des views

        //First case
        forget = findViewById(R.id.forget);

        forget.setOnClickListener(v -> {
            startActivity(new Intent(SignInActivity.this, ForgetPasswordActivity.class));
        });

    }
}