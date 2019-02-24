package com.example.aalift;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.FirebaseException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightData {

    private static DatabaseReference db;
    private static String TAG = "DATABASE";
    public static List<Weight> listItem;

    public static void AddWeight(Weight weight){
        db = FirebaseDatabase.getInstance().getReference().child("users").child(UserData.getUserId()).child("Weight").push();
        Map<String,Object> updates = new HashMap<String,Object>();
        try {
            updates.put("Weight",weight.getWeight());
            updates.put("Date",weight.getDate());
            db.setValue(updates);
        }
        catch (DatabaseException e) {
            Log.d(TAG,"error:",e);
        }
        String w = String.valueOf(weight.getWeight());
        Log.d(TAG,w);
    }
}
