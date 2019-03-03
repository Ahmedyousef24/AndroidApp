package com.example.aalift;



import java.time.LocalDateTime;
import java.util.Date;


public class Weight {
    private float weight;
    private Date date;

    public Weight(float weight, Date date){
        this.weight = weight;
        this.date = date;
    }

    public float getWeight(){
        return weight;
    }
    public Date getDate() { return date; }

}
