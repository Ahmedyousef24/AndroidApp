package com.example.aalift;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private Button mLogoutBtn;
    private FirebaseAuth mAuth;
   public static FrameLayout frame;
    private BottomNavigationView navView;

    private HomeFragment homeFragment;
    private SearchActivity searchActivity;
    private ProgressFragment progressFragment;
    private ProfileFragment profileFragment;
    private ScanCodeActivity scanCodeActivity;
    private int position;
    private FragmentManager fragmentManager;

    private final String homeTag = "home";
    private final String searchTag = "search";
    private final String progressTag = "progress";
    private final String profileTag = "profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState != null) {
            int pos = savedInstanceState.getInt("position");
        }


        homeFragment = new HomeFragment();
        searchActivity = new SearchActivity();
        progressFragment = new ProgressFragment();
        profileFragment = new ProfileFragment();


        mAuth = FirebaseAuth.getInstance();

        frame = (FrameLayout) findViewById(R.id.frame);
        navView = (BottomNavigationView) findViewById(R.id.bottomNavView);
        navView.setSelectedItemId(R.id.home);
        SetFragment(homeFragment, progressFragment, profileFragment, homeTag);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        SetFragment(homeFragment, progressFragment, profileFragment, homeTag);
                        return true;
                    case R.id.progress:
                        SetFragment(progressFragment, profileFragment, homeFragment, progressTag);
                        return true;
                    case R.id.profile:
                        SetFragment(profileFragment,  progressFragment, homeFragment ,profileTag);
                        return true;
                    default:
                        return false;
                }
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", position);
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            updateUI();
        }
    }

    private void updateUI() {

        Toast.makeText(HomeActivity.this, "logged out", Toast.LENGTH_LONG).show();
        // start intent if logged in
        Intent homeintent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(homeintent);
        finish();
    }

    private void SetFragment(Fragment fragmentToShow, Fragment fragmentToHide1, Fragment fragmentToHide2, String tag) {


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).show(fragmentToShow);

        if (!fragmentToShow.isAdded()) {
            fragmentTransaction.add(R.id.frame, fragmentToShow, tag);
            fragmentTransaction.hide(fragmentToHide1);
            fragmentTransaction.hide(fragmentToHide2);


            fragmentTransaction.commit();
            return;
        }
        else{
            fragmentTransaction.show(fragmentToShow);
            fragmentTransaction.hide(fragmentToHide1);
            fragmentTransaction.hide(fragmentToHide2);
            fragmentTransaction.commit();
        }

    }

}
