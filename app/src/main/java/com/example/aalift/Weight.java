package com.example.aalift;



import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


public class Weight implements Parcelable {
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


    protected Weight(Parcel in) {
        weight = in.readFloat();
        long tmpDate = in.readLong();
        date = tmpDate != -1 ? new Date(tmpDate) : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(weight);
        dest.writeLong(date != null ? date.getTime() : -1L);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Weight> CREATOR = new Parcelable.Creator<Weight>() {
        @Override
        public Weight createFromParcel(Parcel in) {
            return new Weight(in);
        }

        @Override
        public Weight[] newArray(int size) {
            return new Weight[size];
        }
    };
}

