package com.example.aalift;

import java.util.Date;

public class Calorie {
    private float calorie;
    private Date date;

    public Calorie(float calorie, Date date){
        this.calorie = calorie;
        this.date = date;
    }

    public float GetCalorie(){
        return calorie;
    }

    public Date GetDate(){
        return date;
    }
}
