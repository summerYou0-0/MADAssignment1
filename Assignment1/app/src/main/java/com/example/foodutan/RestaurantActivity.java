package com.example.foodutan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.foodutan.databinding.ActivityRestaurantBinding;

import java.util.ArrayList;

public class RestaurantActivity extends AppCompatActivity {

    ActivityRestaurantBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int restaurantPic;
        String restaurantName;
        binding.restaurantName.setVisibility(View.VISIBLE);
        binding.restaurantImage.setVisibility(View.VISIBLE);
        binding.listfoodview.setVisibility(View.VISIBLE);



        Intent intent = this.getIntent();

        if(intent != null){

            //String restaurantName = intent.getStringExtra("name");
            restaurantPic = intent.getIntExtra("picture", R.drawable.bakery);
            restaurantName = intent.getStringExtra("name");


            ArrayList<Food> foodArrayList =  intent.getParcelableArrayListExtra("foods");




            binding.restaurantImage.setImageResource(restaurantPic);
            binding.restaurantName.setText(restaurantName);





            ListFoodAdapter listFoodAdapter = new ListFoodAdapter(RestaurantActivity.this, foodArrayList);
            binding.listfoodview.setAdapter(listFoodAdapter);
            binding.listfoodview.setClickable(true);
            binding.listfoodview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Begin the transaction
                    if(!isLoggedIn()){

                        binding.restaurantName.setVisibility(View.GONE);
                        binding.restaurantImage.setVisibility(View.GONE);
                        binding.listfoodview.setVisibility(View.GONE);

                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        // Replace the contents of the container with the new fragment
                        ft.replace(R.id.register_container, new Login());
                        // Complete the changes added above
                        ft.commit();
                        Toast.makeText(getApplicationContext(), "not logged in, going to log in page", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "already logged in!", Toast.LENGTH_LONG).show();
                    }

                }
            });



        }




    }

    public boolean isLoggedIn(){
        return false;
    }
}