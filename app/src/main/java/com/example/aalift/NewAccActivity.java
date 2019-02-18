package com.example.aalift;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewAccActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputName;
    private String name, email, password;
    private Button createBtn;
    private static  final String TAG = "FACELOG";
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private DatabaseReference mDatabase;
    private  boolean userExists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_acc);

        // get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        inputEmail = (EditText) findViewById(R.id.email);
        inputName = (EditText) findViewById(R.id.name);
        inputPassword = (EditText) findViewById(R.id.password);
        createBtn = (Button) findViewById(R.id.create_btn);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  email  = inputEmail.getText().toString();
                  name = inputName.getText().toString();
                  password = inputPassword.getText().toString();

                 if(TextUtils.isEmpty(name)){
                     Toast.makeText(getApplicationContext(),"Enter Name",Toast.LENGTH_SHORT).show();
                     return;
                 }

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }

                //progressBar.setVisibility(View.VISIBLE);

                 // lägg user i firebase
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(NewAccActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else{
                            final String userId = auth.getCurrentUser().getUid();
                            Data.CreateNewUser(userId,email,name);
                            startActivity(new Intent(NewAccActivity.this,HomeActivity.class));
                            finish();
                        }
                    }
                });
            }
        });
    }
    /*@Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }*/


}
//Comeee