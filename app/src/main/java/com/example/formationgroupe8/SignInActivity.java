package com.example.formationgroupe8;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity {
    private TextView gotosignup;
    private EditText emailSignIn,passwordSignin;
    private CheckBox remembermeSignIn;
    private Button btnSignIn;
    private TextView forget;
    private String emailInput, passwordInput;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        emailSignIn=findViewById(R.id.emailSignIn);
        remembermeSignIn=findViewById(R.id.rememberMeSignIn);
        passwordSignin=findViewById(R.id.passwordSignIn);
        btnSignIn=findViewById(R.id.btnSignIn);
        forget = findViewById(R.id.forget);
        gotosignup = findViewById(R.id.gotoSignUp);

        progressDialog=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");

        if (checkbox.equals("true")) {
            Intent intent = new Intent(SignInActivity.this, ProfilActivity.class);
            startActivity(intent);
        } else if (checkbox.equals("false")) {
            Toast.makeText(this, "Please Sign In", Toast.LENGTH_SHORT).show();
        }

        forget.setOnClickListener(v -> {
            startActivity(new Intent(SignInActivity.this, ForgetPasswordActivity.class));
        });
        gotosignup.setOnClickListener(v -> {
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        });
        btnSignIn.setOnClickListener(v->{
            progressDialog.setMessage("please wait");
            progressDialog.show();
            if (validate()) {

                firebaseAuth.signInWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        checkEmailVerification();

                    } else {
                        Toast.makeText(this, "error !!!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
            }

        });
        remembermeSignIn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isChecked()) {
                SharedPreferences preferences1 = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences1.edit();
                editor.putString("remember", "true");
                editor.apply();
            } else if (!buttonView.isChecked()) {
                SharedPreferences preferences1 = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences1.edit();
                editor.putString("remember", "false");
                editor.apply();
            }
        });

    }

    private void checkEmailVerification() {
        FirebaseUser loggedUser = firebaseAuth.getCurrentUser();

        if (loggedUser != null) {
            if (loggedUser.isEmailVerified()) {
                startActivity(new Intent(this, ProfilActivity.class));
                progressDialog.dismiss();
                finish();
            } else {
                Toast.makeText(this, "Please verify your email !!", Toast.LENGTH_SHORT).show();
                loggedUser.sendEmailVerification();
                firebaseAuth.signOut();
                progressDialog.dismiss();
            }
        }
    }

    private boolean validate() {
        boolean result = false;
        String emailInput = emailSignIn.getText().toString().trim();
        String passwordInput = passwordSignin.getText().toString().trim();

        if (!isValidPattern(emailInput, EMAIL_PATTERN)) {
            emailSignIn.setError("Email is invalid");
        } else if (passwordInput.length() < 8) {
            passwordSignin.setError("Password must be at least 8 characters long. !!!");
        } else {
            result = true;
        }

        return result;
    }
    private boolean isValidPattern(String input, String patternStr) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

}