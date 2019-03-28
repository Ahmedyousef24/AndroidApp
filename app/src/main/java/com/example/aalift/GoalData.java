package com.example.aalift;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class GoalData {

    private static DatabaseReference mDatabase;

    public static void AddGoal(Goal goal){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(UserData.getUserId()).child("Goal").push();
        Map<String,Object> updates = new HashMap<String,Object>();
        try {
            updates.put("height",goal.getHeight());
            updates.put("activityLevel",goal.getActivityLevel());
            updates.put("goal",goal.getGoal());
            updates.put("gender",goal.getGender());
            updates.put("age",goal.getAge());
            updates.put("tdee",goal.getTdee());
            updates.put("goal",goal.getGoal());
            mDatabase.setValue(updates);
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }

    }

    public static void getTdee(final TdeeCallBack callBack) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("users").child(UserData.getUserId()).child("Goal");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int tdee = 0;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
             if( snapshot.child("tdee").getValue(int.class)!=null){
                  tdee =snapshot.child("tdee").getValue(int.class);
             }}
                callBack.onTdeeReady(tdee);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public static void getGoal(final GoalCallBack callBack) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("users").child(UserData.getUserId()).child("Goal");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int goal = 0;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(snapshot.child("goal").getValue(int.class) != null){
                        goal = snapshot.child("goal").getValue(int.class);
                    }}
                callBack.OnGoalReady(goal);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void getActivityLevel(final ActivityLevelCallBack callBack) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("users").child(UserData.getUserId()).child("Goal");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int activityLevel = 0;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                if(snapshot.child("ActivityLevel").getValue(int.class) != null){
                    activityLevel = snapshot.child("ActivityLevel").getValue(int.class);
                }}
                callBack.onActivityLevelReady(activityLevel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void getGender(final GenderCallBack callBack) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("users").child(UserData.getUserId()).child("Goal");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int gender = 0;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                if(snapshot.child("gender").getValue(int.class) != null){
                    gender = dataSnapshot.child("gender").getValue(int.class);
                }}
                callBack.onGenderReady(gender);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void getHeight(final HeightCallBack callBack) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("users").child(UserData.getUserId()).child("Goal");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int height = 0;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                if(snapshot.child("height").getValue(int.class) != null){
                    height = snapshot.child("height").getValue(int.class);
                }}
                callBack.onHeightReady(height);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public static void getAge(final AgeCallBack callBack) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("users").child(UserData.getUserId()).child("Goal");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int age = 0;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                if(snapshot.child("age").getValue(int.class) != null){
                    age = snapshot.child("age").getValue(int.class);
                }}
                callBack.onAgeReady(age);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public interface TdeeCallBack {
        void onTdeeReady(int tdee);

    }
    public  interface GenderCallBack{
        void onGenderReady(int gender);
    }
    public interface AgeCallBack{
        void onAgeReady(int age);
    }

    public interface HeightCallBack{
        void onHeightReady(int height);
    }
    public interface ActivityLevelCallBack{
        void onActivityLevelReady(int activityLevel);
    }
    public interface GoalCallBack{
        void OnGoalReady(int goal);
    }
}
