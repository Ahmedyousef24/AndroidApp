package com.example.aalift;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private Button mLogoutBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mLogoutBtn = (Button) findViewById(R.id.logout_btn);
        mAuth = FirebaseAuth.getInstance();
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                LoginManager.getInstance().logOut();
                updateUI();
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
            updateUI();
        }
    }

    private void updateUI() {

        Toast.makeText(HomeActivity.this,"logged out",Toast.LENGTH_LONG).show();
        // start intent if logged in
        Intent homeintent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(homeintent);
        finish();
    }
}
