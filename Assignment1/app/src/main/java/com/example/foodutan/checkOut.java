package com.example.foodutan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class checkOut extends Fragment {
    private CartItemList cartitList, cartitemList, userList, guestList;
    private boolean isLoggedIn;
    private String Email;
    private TextView count;
    private ExtendedFloatingActionButton checkOut;
    private ArrayList<CartItem> cartList;
    FloatingActionButton back;
    private RecyclerView rv;
    private checkOutAdapter adapter;
    private LinearLayoutManager rvLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //load the data from databases
        cartitList = new CartItemList();
        cartitList.load(getActivity());

        System.out.println(isLoggedIn);

        //Create two different ItemList, check the database for all query with email. If user logged in, get all the data based on email address
        //If user not logged in, get all the data based on the condition where email = null; (We never save email for a random user)
        if(!isLoggedIn) {
            guestList = new CartItemList(cartitList.getGuestData(), getActivity());
            cartitemList = guestList;
        }else {
            userList = new CartItemList(cartitList.getUserData(Email), getActivity());
            cartitemList = userList;
        }

        System.out.println(cartitemList.size());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_check_out, container, false);

        Double total = cartitemList.getTotalPrice();

        back = (FloatingActionButton)view.findViewById(R.id.backButton);
        rv = (RecyclerView) view.findViewById(R.id.cartList);
        count = (TextView) view.findViewById(R.id.checkoutCart);
        count.setText("Total Price: " + total);

        //get data from Login fragment
        Bundle b = this.getArguments();

        if(b != null) {
            isLoggedIn = b.getBoolean("isLoggedIn", false);
            Email = b.getString("email");
        }

        //get data from RestaurantActivity fragment
        if(savedInstanceState != null) {
            savedInstanceState = getArguments();
            cartList = savedInstanceState.getParcelableArrayList("Cart");
            isLoggedIn = savedInstanceState.getBoolean("isLoggedIn", false);
        }else {
            Bundle bundle = getArguments();
            cartList = bundle.getParcelableArrayList("Cart");
            isLoggedIn = bundle.getBoolean("isLoggedIn", false);
        }

        //back button, redirected to mainactivity (also passing data)
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("isLoggedIn", isLoggedIn);
                intent.putExtra("email", Email);
                getActivity().startActivity(intent);
            }
        });

        checkOut = view.findViewById(R.id.checkout);

        adapter = new checkOutAdapter();
        rvLayout = new LinearLayoutManager(getActivity());
        rv.setAdapter(adapter);
        rv.setLayoutManager(rvLayout);
        return view;
    }

    private class checkOutViewHolder extends RecyclerView.ViewHolder {
        private CartItem cart;
        private Double cartPrice;
        private Double totalPrice;
        private int cartCount;
        private ImageView restaurantPic;
        private ImageView dec;
        private ImageView inc;
        private TextView description;
        private TextView amount;
        private TextView price;
        private Button remove;

        @SuppressLint("SetTextI18n")
        public checkOutViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_cartitem, parent, false));
            View view =  inflater.inflate(R.layout.fragment_check_out, parent, false);
            dec = (ImageView) itemView.findViewById(R.id.decButton);
            inc = (ImageView) itemView.findViewById(R.id.incButton);
            description = (TextView) itemView.findViewById(R.id.cartItem);
            amount = (TextView) itemView.findViewById(R.id.cartQuantity);
            price = (TextView) itemView.findViewById(R.id.cartPrice);
            remove = (Button) itemView.findViewById(R.id.remove);
            restaurantPic = (ImageView) itemView.findViewById(R.id.restaurantPic);

            //Remove an item in the Cart (cart Database)
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cartitemList.remove(cart);
                    adapter.notifyItemRemoved(getAdapterPosition());
                    count.setText("Total Price: " + Double.toString(cartitemList.getTotalPrice()));
                }
            });

            //decrease amount of item added in Cart
            dec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //the amount cannot less than 1, otherwise the item not existed
                    if(cartCount > 1){
                        cartCount--;
                        Double finalPrice = cartPrice * cartCount;
                        amount.setText(Integer.toString(cartCount));
                        price.setText(Double.toString(finalPrice));

                        cart.setCartAmount(cartCount);
                        cart.setCartPrice(cartPrice);
                        cartitemList.edit(cart);

                        count.setText("Total Price: " + Double.toString(cartitemList.getTotalPrice()));
                    }else{
                        amount.setText(Integer.toString(cartCount));
                        price.setText(Double.toString(cartPrice));

                        cart.setCartAmount(cartCount);
                        cart.setCartPrice(cartPrice);
                        cartitemList.edit(cart);

                        count.setText("Total Price: " + Double.toString(cartitemList.getTotalPrice()));
                    }
                }
            });

            //increase amount of item added in Cart
            inc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cartCount++;
                    Double finalPrice = cartPrice * cartCount;
                    amount.setText(Integer.toString(cartCount));
                    price.setText(Double.toString(finalPrice));

                    cart.setCartAmount(cartCount);
                    cart.setCartPrice(cartPrice);
                    cartitemList.edit(cart);

                    count.setText("Total Price: " + Double.toString(cartitemList.getTotalPrice()));
                }
            });

            //Go to checkOut page, if not logged in will be directed to login page, else go to order history page
            checkOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!isLoggedIn){
                        Toast.makeText(getActivity(), "not logged in, going to log in page", Toast.LENGTH_LONG).show();

                        Login login = new Login();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.register_container, login);
                        ft.commit();
                    }else {
                        cartitemList.updateEmptyMail(Email);
                        //Add something here for Order History

                        Toast.makeText(getActivity(), "Checkout Successfully!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        public void bind(CartItem cart) {
                this.cart = cart;
                cartCount = cart.getCartCount();
                cartPrice = cart.getCartPrice();
                description.setText(cart.getCartName());
                amount.setText(Integer.toString(cart.getCartCount()));
                price.setText(Double.toString(cart.getCartPrice() * cart.getCartCount()));
                totalPrice = cartitemList.getTotalPrice();
                restaurantPic.setImageResource(cart.getCartImageID());
        }
    }

    public class checkOutAdapter extends RecyclerView.Adapter<checkOutViewHolder> {
        @NonNull
        @Override
        public checkOutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new checkOutViewHolder(li, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull checkOutViewHolder holder, int position) {
            holder.bind(cartitemList.get(position));
        }

        @Override
        public int getItemCount() {
            return cartitemList.size();
        }
    }
}

