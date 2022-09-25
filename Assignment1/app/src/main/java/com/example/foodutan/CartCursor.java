package com.example.foodutan;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.foodutan.DatabaseSchema.CartTable;

public class CartCursor extends CursorWrapper{
    public CartCursor(Cursor cursor) {
        super(cursor);
    }

    public CartItem getCartDetail() {
        String email = getString(getColumnIndex(CartTable.Cols.EMAIL));
        int foodImageID = getInt(getColumnIndex(CartTable.Cols.FOODIMAGE));
        String restaurant = getString(getColumnIndex(CartTable.Cols.RESTAURANT));
        String foodName = getString(getColumnIndex(CartTable.Cols.FOODNAME));
        Double foodPrice = getDouble(getColumnIndex(CartTable.Cols.FOODPRICE));
        int foodAmount = getInt(getColumnIndex(CartTable.Cols.AMOUNT));
        return new CartItem(email, foodImageID, restaurant, foodName, foodPrice, foodAmount);
    }
}