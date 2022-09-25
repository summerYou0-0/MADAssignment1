package com.example.foodutan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListFoodAdapter extends ArrayAdapter<Food> {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public ListFoodAdapter(Context context, ArrayList<Food> foodArrayList) {
        super(context, R.layout.list_food, foodArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Food food = getItem(position);

        if (convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_food,parent,false);

        }

        ImageView foodPic = convertView.findViewById(R.id.foodPic);
        TextView foodName = convertView.findViewById(R.id.foodName);
        TextView foodPrice = convertView.findViewById(R.id.foodPrice);

       // TextView amount = convertView.findViewById(R.id.amount);
       // Button plusBtn = convertView.findViewById(R.id.plusBtn);
      //  Button minusBtn = convertView.findViewById(R.id.minusBtn);

       foodPic.setImageResource(food.getDrawableId());
       foodName.setText(food.getFoodName());
       foodPrice.setText("RM" + df.format(food.getFoodPrice()));
       //amount.setText("0");


        return convertView;
    }
}
