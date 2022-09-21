package com.example.foodutan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.foodutan.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int[] drawableId = {R.drawable.bakery, R.drawable.bakso, R.drawable.bimbibap, R.drawable.burger,
                R.drawable.coffee, R.drawable.pizza, R.drawable.shawarma, R.drawable.steakhouse};

        String[] restaurantName = {"Bakery", "Bakso", "Bimbibap", "Burger",
                                     "Coffee", "Pizza", "Shawarma", "Steakhouse"};

        String[] description = {"We sell bread", "Yummy bakso", "Delicious korean dish!", "Better than McD!!",
                                 "Cheaper than Starbucks!", "Pizzahut clone", "Tony Stark has been here!", "Best steak in the house!"};

        ArrayList bakeryFood = new ArrayList(), baksoFood = new ArrayList(), bimbibapFood = new ArrayList(),
                burgerFood = new ArrayList(), coffeeFood = new ArrayList(), pizzaFood = new ArrayList(),
                shawarmaFood = new ArrayList(), steakFood = new ArrayList();

        bakeryFood.add(new Food(R.drawable.bakery_bread, "Normal Bread", 3.00));
        bakeryFood.add(new Food(R.drawable.bakery_catbread, "Cat Bread", 6.00));
        bakeryFood.add(new Food(R.drawable.bakery_crossaint, "Croissant", 5.00));
        bakeryFood.add(new Food(R.drawable.bakery_donut, "Sprinkly Donut", 2.00));
        bakeryFood.add(new Food(R.drawable.bakery_subwaybread, "Subway Bread", 10.00));

        baksoFood.add(new Food(R.drawable.bakso_mee, "Bakso Mee", 10.00));
        baksoFood.add(new Food(R.drawable.bakso_mi_bihun, "Bakso Bi Hun", 10.00));
        baksoFood.add(new Food(R.drawable.bakso_spicy, "Bakso Spicy", 11.50));
        baksoFood.add(new Food(R.drawable.bakso_tahu, "Bakso Tahu", 15.80));
        baksoFood.add(new Food(R.drawable.bakso_withegg, "Bakso with Egg", 15.00));

        bimbibapFood.add(new Food(R.drawable.bimbibap_food, "Bimbibap", 20.00));


        ArrayList[] foodList= {bakeryFood, baksoFood, bimbibapFood, burgerFood, coffeeFood, pizzaFood, shawarmaFood, steakFood};



        ArrayList<Restaurant> restaurantArrayList = new ArrayList<>();

        for(int i = 0; i < drawableId.length; i++){
            Restaurant restaurant = new Restaurant(drawableId[i], restaurantName[i], description[i], foodList[i]);
            restaurantArrayList.add(restaurant);
        }

        ListAdapter listAdapter = new ListAdapter(MainActivity.this, restaurantArrayList);
        binding.listview.setAdapter(listAdapter);
        binding.listview.setClickable(true);
        binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,RestaurantActivity.class);
                intent.putExtra("name",restaurantName[i]);
                intent.putExtra("picture",drawableId[i]);

               intent.putParcelableArrayListExtra("foods", foodList[i]);
                startActivity(intent);


            }
        });
    }
}