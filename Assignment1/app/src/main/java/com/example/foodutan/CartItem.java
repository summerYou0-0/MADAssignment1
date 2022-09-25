package com.example.foodutan;

import android.os.Parcel;
import android.os.Parcelable;

//Class used to store the item in cart

public class CartItem implements Parcelable {
    private String email;
    private int imageID;
    private String restaurant;
    private String name;
    private Double price;
    private int count;

    public CartItem(int imgID, String foodRestaurant, String foodName, Double foodPrice, int itemCount) {
        email = "";
        imageID = imgID;
        restaurant = foodRestaurant;
        name = foodName;
        price =foodPrice;
        count = itemCount;
    }

    public CartItem(String mail, int foodImage, String foodRestaurant, String foodName, Double foodPrice, int foodCount) {
        email = mail;
        imageID = foodImage;
        restaurant = foodRestaurant;
        name = foodName;
        price = foodPrice;
        count = foodCount;
    }


    protected CartItem(Parcel in) {
        name = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        count = in.readInt();
    }

    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };
    public String getCartEmail() {
        return email;
    }

    public int getCartImageID() {
        return imageID;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public String getCartName() {
        return name;
    }

    public Double getCartPrice() {
        return price;
    }

    public int getCartCount() {
        return count;
    }

    public void setCartEmail(String cartEmail) {
        email = cartEmail;
    }

    public void setCartImage(int cartImgID) {
        imageID = cartImgID;
    }

    public void setCartRestaurant(String cartRestaurant) {
        restaurant = cartRestaurant;
    }

    public void setCartName(String cartName) {
        name = cartName;
    }

    public void setCartPrice(Double cartPrice) {
        price = cartPrice;
    }

    public void setCartAmount(int cartAmount) {
        count = cartAmount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        if (price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(price);
        }
        parcel.writeInt(count);
    }
}
