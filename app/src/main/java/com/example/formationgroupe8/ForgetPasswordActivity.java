package com.example.formationgroupe8;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgetPasswordActivity extends AppCompatActivity {
    private Button backtosignup,btnSend;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private Button btnback, btnsend;
    private EditText emailForgetPass;
    private String emailInput;
    private final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        backtosignup = findViewById(R.id.backtosignup);
        progressDialog=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        btnSend=findViewById(R.id.btnSend);

        backtosignup.setOnClickListener(v -> {
            startActivity(new Intent(ForgetPasswordActivity.this,SignInActivity.class));
        });
        btnSend.setOnClickListener(v -> {
            progressDialog.setMessage("Please wait ... ");
            if (validate()) {
                progressDialog.show();
                firebaseAuth.sendPasswordResetEmail(emailInput).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Password reset email has been sent", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ForgetPasswordActivity.this, SignInActivity.class));
                        progressDialog.dismiss();
                        finish();
                    } else {
                        Toast.makeText(this, "Server error !!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }
    private boolean validate() {
        boolean result = false;

        emailInput = emailForgetPass.getText().toString().trim();
        if (!isValidPattern(emailInput, EMAIL_PATTERN)) {
            emailForgetPass.setError("email is invalide");
        } else {
            result = true;
        }
        return result;

    }
    private boolean isValidPattern(String mot, String patternn) {
        Pattern pattern = Pattern.compile(patternn);
        Matcher matcher = pattern.matcher(mot);
        return matcher.matches();
    }
}