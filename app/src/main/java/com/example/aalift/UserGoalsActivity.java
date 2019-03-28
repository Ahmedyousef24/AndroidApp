package com.example.aalift;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class UserGoalsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_goals);
        textView = (TextView) this.findViewById(R.id.result);

        Spinner activityLevel = findViewById(R.id.activity_level);
        ArrayAdapter<CharSequence> adapter_activity = ArrayAdapter.createFromResource(this,
                R.array.activity_level, android.R.layout.simple_spinner_item);
        adapter_activity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityLevel.setAdapter(adapter_activity);
        activityLevel.setOnItemSelectedListener(this);


        Spinner goal =  findViewById(R.id.goal);
        ArrayAdapter<CharSequence> adapter_goal = ArrayAdapter.createFromResource(this,
                R.array.goal, android.R.layout.simple_spinner_item);
        adapter_goal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goal.setAdapter(adapter_goal);
        goal.setOnItemSelectedListener(this);

        Spinner gender =  findViewById(R.id.gender);
        ArrayAdapter<CharSequence> adapter_gender = ArrayAdapter.createFromResource(this,
                R.array.gender, android.R.layout.simple_spinner_item);
        adapter_goal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapter_gender);
        gender.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void saveAction(View view) {
        if (((EditText) findViewById(R.id.editText)).getText().toString().isEmpty() ||
                ((EditText) findViewById(R.id.height)).getText().toString().isEmpty() ||
                ((EditText) findViewById(R.id.editText3)).getText().toString().isEmpty()) {
            AlertDialog alertDialog = new AlertDialog.Builder(UserGoalsActivity.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Please complete the information.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return;
        }
        int height, weight, activityLevel, goal, gender, age;

        height = Integer.parseInt(((EditText) findViewById(R.id.height)).getText().toString());
        weight = Integer.parseInt(((EditText) findViewById(R.id.editText)).getText().toString());
        activityLevel = ((Spinner) findViewById(R.id.activity_level)).getSelectedItemPosition();
        goal = ((Spinner) findViewById(R.id.goal)).getSelectedItemPosition();
        gender = ((Spinner) findViewById(R.id.gender)).getSelectedItemPosition();
        age = Integer.parseInt(((EditText) findViewById(R.id.editText3)).getText().toString());
        int tdee = calculateTDEE(height, weight, activityLevel, age, gender, goal);
        Date date = new Date();
        GoalData.AddGoal(new Goal(height,activityLevel,tdee,gender,age,goal));
       WeightData.AddWeight(new Weight(weight,date));

       Intent intent = new Intent(UserGoalsActivity.this, HomeActivity.class);
       startActivity(intent);
        //saves user tdee
        /*SharedPreferences userInfo = getSharedPreferences("userInfo", 0);
        SharedPreferences.Editor editor = userInfo.edit();
        editor.putInt("tdee", tdee);
        editor.commit();*/

    }

    public int calculateTDEE(int heightCm, int weight, int activityLevel, int age, int gender, int goal) {
        int cm = heightCm;
        double kg = weight;
        double bmr = (10 * kg) + (6.25 * cm) - (5 * age) + 5; // men

        if (gender == 1) { //women
            bmr = (10 * kg) + (6.25 * cm) - (5 * age) - 161;
        }

        double activity = 1.2; // little or no exercise - low actvity
        switch (activityLevel) {
            case 1:
                activity = 1.375;  //light active 1-3/week
                break;
            case 2:
                activity = 1.55; //moderately active 3-5
                break;
            case 3:
                activity = 1.725; // very active 6-7
                break;
            case 4:
                activity = 1.9; // super active 7 days exercise plus hard physical work
                break;
        }
        double tdee = bmr * activity;
        switch (goal) {

            case 0:
                tdee -=500;
                break;
            case 2:
                tdee +=500;
                break;
        }
        tdee += .5;

        textView.setText(String.valueOf(tdee));
        return (int) tdee;
    }

}