package com.example.aalift;

import android.os.AsyncTask;
import android.util.Log;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NutritionData extends AsyncTask<Void, Void, Void> {

    private static DatabaseReference db;
    private static ArrayList<Nutrition>breakfastList, lunchList,dinnerList,snackList;
    private static Date dateToday;
    private static FirebaseCallBack firebaseCallBack;
    private ArrayList<String>breakfastIdList,lunchIdList,dinnerIdList,snackIdList;
    private ArrayList<Nutrition> nutritionList;

    private static float ParseFloat(String value) {
        float f = Float.parseFloat(value);
        return f;
    }

    public void AddFood(ArrayList<Nutrition> list, String TAG) {
        int counter = 0;
        db = FirebaseDatabase.getInstance().getReference().child("users").child(UserData.getUserId()).child("Food").child(dateToday.toString()).child(TAG).push();
        Map<String, Object> updates = new HashMap<>();

        try {
            for (Nutrition nutrition : list) {
                if (counter == 0) {
                    updates.put("ProductName", nutrition);
                } else {
                    updates.put(nutrition.getName(), nutrition);
                }
                counter++;
            }
            db.setValue(updates);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    private String getDate() {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        int counter = 0;
        try {
            dateToday = formatter.parse(formatter.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateToday.toString();
    }

    public void getLunchList() {
        lunchList = new ArrayList<>();
        lunchIdList = new ArrayList<>();

        db = FirebaseDatabase.getInstance().getReference();
        Query query = db.child("users").child(UserData.getUserId()).child("Food").child(getDate()).child("lunch");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.child("ProductName").child("name").getValue(String.class);
                    float value = snapshot.child("Energy").child("value").getValue(float.class);
                    String key = snapshot.getKey();
                    lunchList.add(new Nutrition(name, value));
                    lunchIdList.add(key);
                }
                firebaseCallBack.OnLunchListReady(lunchList,lunchIdList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getDinnerList() {
        dinnerList = new ArrayList<>();
        dinnerIdList = new ArrayList<>();

        db = FirebaseDatabase.getInstance().getReference();
        Query query = db.child("users").child(UserData.getUserId()).child("Food").child(getDate()).child("dinner");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.child("ProductName").child("name").getValue(String.class);
                    float value = snapshot.child("Energy").child("value").getValue(float.class);
                    String key = snapshot.getKey();
                    dinnerList.add(new Nutrition(name, value));
                    dinnerIdList.add(key);
                }
                firebaseCallBack.OnDinnerListReady(dinnerList,dinnerIdList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void getSnackList() {
        snackList = new ArrayList<>();
        snackIdList = new ArrayList<>();

        db = FirebaseDatabase.getInstance().getReference();
        Query query = db.child("users").child(UserData.getUserId()).child("Food").child(getDate()).child("snack");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.child("ProductName").child("name").getValue(String.class);
                    float value = snapshot.child("Energy").child("value").getValue(float.class);
                    String key = snapshot.getKey();
                    snackList.add(new Nutrition(name, value));
                    snackIdList.add(key);
                }
                firebaseCallBack.OnSnackListReady(snackList,snackIdList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void getProduct(String meal, String key, final NutritionCallBack nutritionCallBack){
         nutritionList = new ArrayList<Nutrition>();

        db = FirebaseDatabase.getInstance().getReference();
        final Query query = db.child("users").child(UserData.getUserId()).child("Food").child(getDate()).child(meal).child(key);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    nutritionList.add(new Nutrition(snapshot.child("name").getValue(String.class),snapshot.child("value").getValue(Float.class)));
                    Log.d("testingId",""+snapshot.child("value").getValue(Float.class));
                }

                nutritionCallBack.OnNutritionListReady(nutritionList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void remove(String id,String tag){
        Log.d("theid",""+id);
        db = FirebaseDatabase.getInstance().getReference().child("users").child(UserData.getUserId()).child("Food").child(getDate()).child(tag).child(id);
        db.removeValue();
    }
    public interface NutritionCallBack{
        void OnNutritionListReady(ArrayList<Nutrition>nutritionList);
    }



    public void getBreakList() {
        breakfastList = new ArrayList<>();
        breakfastIdList = new ArrayList<>();
        db = FirebaseDatabase.getInstance().getReference();
        Query query = db.child("users").child(UserData.getUserId()).child("Food").child(getDate()).child("breakfast");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    String name = snapshot.child("ProductName").child("name").getValue(String.class);
                    Log.d("nutritionTAG", "" + name);
                    float value = snapshot.child("Energy").child("value").getValue(float.class);
                    breakfastList.add(new Nutrition(name, value));
                    breakfastIdList.add(key);
                }

                firebaseCallBack.OnBreakfastListReady(breakfastList,breakfastIdList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setCustomListener(FirebaseCallBack callBack) {
        firebaseCallBack = callBack;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        getDinnerList();
        getSnackList();
        getLunchList();
        getBreakList();
        return null;
    }

    public interface FirebaseCallBack {

        void OnBreakfastListReady(ArrayList<Nutrition> list, ArrayList<String>keyList);

        void OnDinnerListReady(ArrayList<Nutrition> list, ArrayList<String>keyList);

        void OnSnackListReady(ArrayList<Nutrition> list, ArrayList<String>keyList);

        void OnLunchListReady(ArrayList<Nutrition> list, ArrayList<String>keyList);
    }

}
