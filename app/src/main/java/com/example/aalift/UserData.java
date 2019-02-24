package com.example.aalift;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserData {
    private static DatabaseReference mDatabase;
    private static boolean userExists;
    private static  final String TAG = "FACELOG";


    public static void CreateNewUser(String userId, String email , String userName) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if (!UserExists(userId)) {
            User user = new User(userName, email);
            try {
                mDatabase.child("users").child(userId).setValue(user);
            } catch (DatabaseException e) {
                throw e;
            }
        }

    }

    public static boolean UserExists(String userId){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = mDatabase.child("users").child(userId);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    userExists = false;
                }
                else{
                    userExists = true;
                    Log.d(TAG,"user finns");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        };
        userRef.addListenerForSingleValueEvent(eventListener);
        return userExists;
    }

    public static String getUserId(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("userid",""+user.getUid());
        return user.getUid();
    }
}
