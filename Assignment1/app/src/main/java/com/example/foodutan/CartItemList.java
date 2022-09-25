package com.example.foodutan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import com.example.foodutan.DatabaseSchema.CartTable;

public class CartItemList {
    private SQLiteDatabase db;
    private List<CartItem> cart = new ArrayList<>();

    public CartItemList() {

    }

    public CartItemList(List<CartItem> carts, Context context) {
        this.db = new CartDBHelper(context).getWritableDatabase();
        cart = carts;
    }

    //load the available items through an ArrayList
    public void load(Context context) {
        this.db = new CartDBHelper(context).getWritableDatabase();
        cart = getDBCartList();
    }

    public int size() {
        return cart.size();
    }

    public CartItem get(int i) {
        return cart.get(i);
    }

    //Add new cart item into database
    public int add(CartItem newCartItem) {
        cart.add(newCartItem);

        ContentValues cv = new ContentValues();

        cv.put(CartTable.Cols.EMAIL, newCartItem.getCartEmail());
        cv.put(CartTable.Cols.FOODIMAGE, newCartItem.getCartImageID());
        cv.put(CartTable.Cols.RESTAURANT, newCartItem.getRestaurant());
        cv.put(CartTable.Cols.FOODNAME, newCartItem.getCartName());
        cv.put(CartTable.Cols.FOODPRICE, newCartItem.getCartPrice());
        cv.put(CartTable.Cols.AMOUNT, newCartItem.getCartCount());

        db.insert(CartTable.NAME, null, cv);

        return cart.size() - 1;
    }

    //Update current existing database by some changes variable
    public void edit(CartItem newCartItem) {
        db.execSQL("update " + CartTable.NAME
                    + " set " + CartTable.Cols.EMAIL + "='" + newCartItem.getCartEmail() + "', "
                    + CartTable.Cols.FOODIMAGE + "='" + newCartItem.getCartImageID() +"', "
                    + CartTable.Cols.RESTAURANT + "='" + newCartItem.getRestaurant() + "', "
                    + CartTable.Cols.FOODNAME + "='" + newCartItem.getCartName() + "', "
                    + CartTable.Cols.FOODPRICE + "=" + newCartItem.getCartPrice() + ", "
                    + CartTable.Cols.AMOUNT + "=" + newCartItem.getCartCount()
                    + " where " + CartTable.Cols.EMAIL + "='" + newCartItem.getCartEmail() + "' and " + CartTable.Cols.FOODNAME + "='" + newCartItem.getCartName() + "';");
    }

    //Remove an item based on email and food name
    public void remove(CartItem rmCartItem) {
        cart.remove(rmCartItem);
        db.execSQL("delete from " + CartTable.NAME + " where " + CartTable.Cols.EMAIL + "='" + rmCartItem.getCartEmail() + "' and " + CartTable.Cols.FOODNAME + "='" + rmCartItem.getCartName() + "';");

    }

    //Check if a food name existed in database
    public boolean isItemExisted(String name) {
        boolean existed = false;

        Cursor cursor = db.rawQuery("SELECT EXISTS (SELECT * FROM " + CartTable.NAME + " WHERE " + CartTable.Cols.FOODNAME + "='" + name + "');", null);
        cursor.moveToFirst();

        if(cursor.getInt(0) == 1) {
            existed = true;
        }

        return existed;
    }

    //Sum all the value in database
    public Double getTotalPrice() {
        Double i = 0.0;
        if(db != null) {
            Cursor cursor = db.rawQuery("SELECT SUM(" + CartTable.Cols.FOODPRICE + " * " + CartTable.Cols.AMOUNT + ") FROM " + CartTable.NAME, null);
            System.out.println("Cursor status: " + cursor);
            cursor.moveToFirst();
            i = cursor.getDouble(0);
            cursor.close();
        }
        return i;
    }

    public ArrayList<CartItem> getGuestData() {
        ArrayList<CartItem> cartList = new ArrayList<>();
        Cursor cursor = db.query(CartTable.NAME,
                new String[] {CartTable.Cols.EMAIL, CartTable.Cols.FOODIMAGE, CartTable.Cols.RESTAURANT, CartTable.Cols.FOODNAME, CartTable.Cols.FOODPRICE, CartTable.Cols.AMOUNT},
                CartTable.Cols.EMAIL + " = ''",
                null,
                null,
                null,
                null);

        CartCursor cartCursor = new CartCursor(cursor);

        try {
            cartCursor.moveToFirst();
            while (!cartCursor.isAfterLast()) {
                cartList.add(cartCursor.getCartDetail());
                cartCursor.moveToNext();
            }
        } finally {
            cartCursor.close();
        }
        return cartList;
    }

    public ArrayList<CartItem> getUserData(String email) {
        ArrayList<CartItem> cartList = new ArrayList<>();
        Cursor cursor = db.query(CartTable.NAME,
                new String[] {CartTable.Cols.EMAIL, CartTable.Cols.FOODIMAGE, CartTable.Cols.RESTAURANT, CartTable.Cols.FOODNAME, CartTable.Cols.FOODPRICE, CartTable.Cols.AMOUNT},
                CartTable.Cols.EMAIL + " ='" + email + "'",
                null,
                null,
                null,
                null);

        CartCursor cartCursor = new CartCursor(cursor);

        try {
            cartCursor.moveToFirst();
            while (!cartCursor.isAfterLast()) {
                cartList.add(cartCursor.getCartDetail());
                cartCursor.moveToNext();
            }
        } finally {
            cartCursor.close();
        }
        return cartList;
    }

    //Update the email if default email is null
    public void updateEmptyMail(String email) {
        db.execSQL("update " + CartTable.NAME + " set " + CartTable.Cols.EMAIL + "='" + email + "' where " + CartTable.Cols.EMAIL + "='';");
    }

    //get all the databases queries and store in array list (used in load (line 24))
    public ArrayList<CartItem> getDBCartList() {
        ArrayList<CartItem> cartList = new ArrayList<>();
        Cursor cursor = db.query(CartTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        CartCursor cartCursor = new CartCursor(cursor);

        try {
            cartCursor.moveToFirst();
            while (!cartCursor.isAfterLast()) {
                cartList.add(cartCursor.getCartDetail());
                cartCursor.moveToNext();
            }
        } finally {
            cartCursor.close();
        }
        return cartList;
    }


    //Clear all the data that existed in the Database
    public void clearDB(){
        db.delete(CartTable.NAME, null, null);
    }
}
