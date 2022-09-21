package com.example.foodutan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Restaurant> {

    public ListAdapter(Context context, ArrayList<Restaurant> restaurantArrayList) {
        super(context, R.layout.list_item, restaurantArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Restaurant restaurant = getItem(position);

        if (convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);

        }

        ImageView restaurantPic = convertView.findViewById(R.id.restaurantPic);
        TextView restaurantName = convertView.findViewById(R.id.restaurantName);
        TextView description = convertView.findViewById(R.id.description);

        restaurantPic.setImageResource(restaurant.getDrawableId());
        restaurantName.setText(restaurant.getRestaurantName());
        description.setText(restaurant.getDescription());


        return convertView;
    }
}
