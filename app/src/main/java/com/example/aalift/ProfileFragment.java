package com.example.aalift;


import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.nio.file.attribute.UserDefinedFileAttributeView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;



public class ProfileFragment extends Fragment {

    private TextView textViewGoal, textViewAge, textViewHeight,textViewEmail,textViewUserName,textViewWeight,textViewTdee;
    private Button signOutButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        textViewGoal = v.findViewById(R.id.textViewGoal);
        textViewAge = v.findViewById(R.id.textViewAge);
        textViewEmail = v.findViewById(R.id.textViewEmail);
        textViewUserName = v.findViewById(R.id.textViewUserName);
        textViewTdee = v.findViewById(R.id.textViewTdee);
        textViewHeight = v.findViewById(R.id.textViewHeight);
        signOutButton = v.findViewById(R.id.signOutBtn);
        textViewWeight = v.findViewById(R.id.textViewWeight);

        GoalData.getTdee(new GoalData.TdeeCallBack() {
            @Override
            public void onTdeeReady(int tdee) {
                textViewTdee.setText(Integer.toString(tdee));
            }
        });
        GoalData.getAge(new GoalData.AgeCallBack() {
            @Override
            public void onAgeReady(int age) {
                textViewAge.setText(Integer.toString(age));
            }
        });

        GoalData.getHeight(new GoalData.HeightCallBack() {
            @Override
            public void onHeightReady(int height) {
                textViewHeight.setText(Integer.toString(height));
            }
        });
      WeightData.getLatestWeight(new WeightData.LatestWeight() {
          @Override
          public void onLatestWeight(float weight) {
              textViewWeight.setText(Float.toString(weight));
          }
      });
      GoalData.getGoal(new GoalData.GoalCallBack() {
          @Override
          public void OnGoalReady(int goal) {
              viewGoal(goal);
          }
      });

        UserData.getUserData(new UserData.FirebaseCallBack() {
            @Override
            public void onEmailReady(String email) {
                textViewEmail.setText(email);
            }

            @Override
            public void onUserNameReady(String userName) {
                textViewUserName.setText(userName);
            }
        });
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    private void viewGoal(int goal){

        switch (goal){
            case 0 :
                textViewGoal.setText("Gain weight");
                break;
            case 1 :
                textViewGoal.setText("Maintain weight");
                break;
            case 2:
                textViewGoal.setText("Lose Weight");
                break;
                default:
                    break;
        }
    }

}
