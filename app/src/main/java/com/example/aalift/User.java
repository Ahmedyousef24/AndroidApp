package com.example.aalift;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class User {


    private String userName;
    private String email;

    public User(String userName, String email){

        this.userName = userName;
        this.email = email;

    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }
}
