package com.example.aalift;

import java.util.Date;

public class Goal {

    private int height;
    private int activityLevel;


    private int tdee;
    private int gender;
    private int age;


    private int goal;

    public Goal(int height, int activityLevel, int tdee, int gender, int age, int goal) {
        this.height = height;
        this.activityLevel = activityLevel;
        this.tdee = tdee;
        this.gender = gender;
        this.age = age;
    }



    public void setHeight(int height) {
        this.height = height;
    }


    public void setActivityLevel(int activityLevel) {
        this.activityLevel = activityLevel;
    }

    public void setTdee(int tdee) {
        this.tdee = tdee;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
    public void setGoal(int goal) {
        this.goal = goal;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public int getActivityLevel() {
        return activityLevel;
    }

    public int getGoal() {
        return goal;
    }

    public int getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public int getTdee() {
        return tdee;
    }

}


