package com.example.aalift;

import java.util.Date;

public class Calorie {
    private float calorie;
    private float fat;
    private float carbohydrates;

    public float getCalorie() {
        return calorie;
    }

    public float getFat() {
        return fat;
    }

    public float getCarbohydrates() {
        return carbohydrates;
    }

    public float getProtein() {
        return protein;
    }

    public Date getDate() {
        return date;
    }

    private float protein;
    private Date date;

    public Calorie(float calorie,float fat, float carbohydrates, float protein ,Date date){
        this.calorie = calorie;
        this.date = date;
        this.fat = fat;

    }


    /*TODO
    1. Make chart faster
     */
}
