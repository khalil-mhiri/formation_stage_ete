package com.example.formationgroupe8;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    private TextView email;
    private Button logoutbtn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        email = findViewById(R.id.test);
        logoutbtn = findViewById(R.id.btnlogout);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser loggedUser = firebaseAuth.getCurrentUser();
        email.setText(loggedUser.getEmail());
        logoutbtn.setOnClickListener(v -> {
            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("remember", "false");
            editor.apply();
            firebaseAuth.signOut();
            startActivity(new Intent(HomeActivity.this, SignInActivity.class));
            Toast.makeText(getApplicationContext(), "Log out successfully !!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}