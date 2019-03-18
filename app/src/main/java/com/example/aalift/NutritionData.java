package com.example.aalift;

import android.service.autofill.Dataset;
import android.util.Log;

import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NutritionData {

    private static DatabaseReference db;
    private static ArrayList<Nutrition>nutritionList;

    public void AddFood(ArrayList<Nutrition>list, String TAG){
        db = FirebaseDatabase.getInstance().getReference().child("users").child(UserData.getUserId()).child("Food").child(TAG).push();
        Map<String, Object> updates = new HashMap<>();

        try{
            for(Nutrition nutrition : list){
                updates.put(nutrition.getName(),nutrition);
            }
            db.setValue(updates);
        }
        catch (DatabaseException e) {
            Log.d(TAG,"error:",e);
        }
    }

    public static void GetScannedProduct(final FirebaseCallBack firebaseCallBack){
        db = FirebaseDatabase.getInstance().getReference();
        Query query = db.child("users").child(UserData.getUserId()).child("Food").child("Breakfast").orderByKey().limitToLast(1);
        nutritionList = new ArrayList<>();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                nutritionList.add(new Nutrition("energy",dataSnapshot.child("Energy").getValue(Float.class)));
           /*     nutritionList.add(new Nutrition("Fat",ParseFloat(dataSnapshot.child("Fat").toString())));
                nutritionList.add(new Nutrition("Saturated fat",ParseFloat(dataSnapshot.child("Saturated fat").toString())));
                nutritionList.add(new Nutrition("Carbohydrate",ParseFloat(dataSnapshot.child("Carbohydrate").toString())));
                nutritionList.add(new Nutrition("Sugars",ParseFloat(dataSnapshot.child("Sugars").toString())));
                nutritionList.add(new Nutrition("Fiber",ParseFloat(dataSnapshot.child("Fiber").toString())));
                nutritionList.add(new Nutrition("Protein",ParseFloat(dataSnapshot.child("Protein").toString())));
                nutritionList.add(new Nutrition("Salt",ParseFloat(dataSnapshot.child("Salt").toString())));
                nutritionList.add(new Nutrition("Sodium",ParseFloat(dataSnapshot.child("Sodium").toString())));*/


                Log.d("nutritionTAG",""+nutritionList.size());
                firebaseCallBack.OnCallBack(nutritionList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    public interface FirebaseCallBack{
        void OnCallBack(ArrayList<Nutrition>list);
    }

    private static float ParseFloat(String value) {
        float f = Float.parseFloat(value);
        return f;
    }
}
