package com.example.foodutan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodutan.databinding.ActivityRestaurantBinding;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity {
    CartItemList cartItemList;

    ArrayList <CartItem> cart = new ArrayList<>();
    private int insertPosition;
    private boolean isLoggedIn, hasItem;
    private String email;
    int itemCount = 1;
    double checkoutPrice;

    ActivityRestaurantBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FragmentManager fm = getSupportFragmentManager();

        cartItemList = new CartItemList();
        cartItemList.load(RestaurantActivity.this);

        int restaurantPic;
        String restaurantName;
        binding.restaurantName.setVisibility(View.VISIBLE);
        binding.restaurantImage.setVisibility(View.VISIBLE);
        binding.listfoodview.setVisibility(View.VISIBLE);

        Intent intent = this.getIntent();
        if(intent != null) {

            //String restaurantName = intent.getStringExtra("name");
            restaurantPic = intent.getIntExtra("picture", R.drawable.bakery);
            restaurantName = intent.getStringExtra("name");
            email = intent.getStringExtra("email");
            isLoggedIn = intent.getBooleanExtra("isLoggedIn", false);

            ShortcutFragment shortcutFragment = new ShortcutFragment();
            fm.beginTransaction().add(R.id.navigation_container, shortcutFragment).commit();

            ArrayList<Food> foodArrayList = intent.getParcelableArrayListExtra("foods");


            binding.restaurantImage.setImageResource(restaurantPic);
            binding.restaurantName.setText(restaurantName);

            ListFoodAdapter listFoodAdapter = new ListFoodAdapter(RestaurantActivity.this, foodArrayList);
            binding.listfoodview.setAdapter(listFoodAdapter);
            binding.listfoodview.setClickable(true);
            binding.listfoodview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //Get the specific food object of specific position of adapter
                        Food food = (Food) binding.listfoodview.getAdapter().getItem(i);

                        //Click an item to pop up a window
                        LayoutInflater inflate = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                        View popup = inflate.inflate(R.layout.popup_addtocart, null);
                        PopupWindow pop = new PopupWindow(popup, 1300, 1600, true);
                        pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);

                        //Pop up Window elements
                        TextView description = popup.findViewById(R.id.popup_description);
                        TextView popup_price = popup.findViewById(R.id.popup_price);
                        TextView count = popup.findViewById(R.id.amount);
                        Button dec = popup.findViewById(R.id.minusButton);
                        Button inc = popup.findViewById(R.id.plusButton);
                        Button goShoppingCart = popup.findViewById(R.id.checkoutButton);
                        ImageView itemPic = popup.findViewById(R.id.itemView);

                        //Pop up window activities
                        TextView foodname = view.findViewById(R.id.foodName);
                        String name = foodname.getText().toString();
                        TextView foodprice = view.findViewById(R.id.foodPrice);
                        String price = foodprice.getText().toString();

                        Double realprice = Double.parseDouble(price.substring(2));
                        checkoutPrice = realprice;

                        itemPic.setImageResource(food.getDrawableId());
                        description.setText(name);
                        popup_price.setText(price);
                        itemCount = 1;
                        count.setText("1");

                        //decrease the amount, with update the price
                        dec.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (itemCount > 1) {
                                    itemCount--;
                                    Double finalPrice = realprice * itemCount;
                                    checkoutPrice = finalPrice;
                                    count.setText(Integer.toString(itemCount));
                                    popup_price.setText("RM" + String.format("%.2f", finalPrice));
                                } else {
                                    count.setText(Integer.toString(itemCount));
                                }
                            }
                        });

                        //increase the amount, with update the price
                        inc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                itemCount++;
                                Double finalPrice = realprice * itemCount;
                                checkoutPrice = finalPrice;
                                count.setText(Integer.toString(itemCount));
                                popup_price.setText("RM" + String.format("%.2f", finalPrice));
                            }
                        });

                        //Floating button for viewing Check out page
                        ExtendedFloatingActionButton addCart = findViewById(R.id.viewCartButton);
                        addCart.setVisibility(View.VISIBLE);

                        addCart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                binding.restaurantName.setVisibility(View.GONE);
                                binding.restaurantImage.setVisibility(View.GONE);
                                binding.listfoodview.setVisibility(View.GONE);
                                pop.dismiss(); //remove the pop up window

                                //pass data and change fragment to checkOut.java
                                Bundle bundle = new Bundle();
                                bundle.putParcelableArrayList("Cart", cart);
                                bundle.putBoolean("isLoggedIn", isLoggedIn);
                                bundle.putInt("position", insertPosition);
                                checkOut checkout = new checkOut();
                                addCart.setVisibility(View.GONE);

                                if (checkout != null) {
                                    checkout.setArguments(bundle);
                                    FragmentManager fm = (RestaurantActivity.this).getSupportFragmentManager();
                                    FragmentTransaction transaction = fm.beginTransaction();
                                    transaction.replace(R.id.register_container, checkout);
                                    transaction.commit();
                                }


                            }
                        });

                        //Button in pop up window, used to add item into Cart
                        goShoppingCart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (!cartItemList.isItemExisted(name)) {
                                    CartItem cItem = new CartItem(food.getDrawableId(), restaurantName, name, realprice, itemCount);
                                    insertPosition = cartItemList.add(cItem);

                                    pop.dismiss();
                                    Toast.makeText(getApplicationContext(), "Added to cart!", Toast.LENGTH_SHORT).show();
                                    cart.add(cItem);
                                } else {
                                    Toast.makeText(getApplicationContext(), "item already existed in Cart!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
            }
    }
}