package com.example.foodutan;

import android.os.Parcel;
import android.os.Parcelable;

public class Food implements Parcelable {

    private final int drawableId;
    private String foodName;
    private double foodPrice;

    public Food(int drawableId, String foodName, double foodPrice){
        this.drawableId = drawableId;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
    }

    protected Food(Parcel in) {
        drawableId = in.readInt();
        foodName = in.readString();
        foodPrice = in.readDouble();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    public int getDrawableId(){return drawableId;}

    public String getFoodName(){return foodName;}

    public double getFoodPrice(){return foodPrice;}

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(drawableId);
        parcel.writeString(foodName);
        parcel.writeDouble(foodPrice);
    }
}
