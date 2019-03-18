package com.example.aalift;

import java.io.Serializable;

public class Nutrition implements Serializable   {

    private String name;
    private float value;

    public Nutrition(String name, float value){
        this.name = name;
        this.value = value;
    }
    public String getName() {
        return name;
    }
    public float getValue() {
        return value;
    }
    public void setValue(float value){
        this.value = value;
    }
}
