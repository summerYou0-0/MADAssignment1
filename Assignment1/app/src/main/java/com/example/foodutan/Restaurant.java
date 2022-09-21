package com.example.foodutan;

import java.util.ArrayList;

public class Restaurant {

    private final int drawableId;
    private String restaurantName;
    private String description;
    private ArrayList<Food> foodArrayList;

    public Restaurant(int drawableId, String restaurantName, String description, ArrayList<Food> foodArrayList)
    {
        this.drawableId = drawableId;
        this.restaurantName = restaurantName;
        this.description = description;
        this.foodArrayList = foodArrayList;
    }

    public int getDrawableId(){return drawableId;}

    public String getRestaurantName(){return restaurantName;}

    public String getDescription(){return description;}
}
