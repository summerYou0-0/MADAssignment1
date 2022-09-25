package com.example.foodutan;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.foodutan.DatabaseSchema.CartTable;

public class CartDBHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "foodcart.db";

    public CartDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + DatabaseSchema.CartTable.NAME + "("
                + CartTable.Cols.EMAIL + ", "
                + CartTable.Cols.FOODIMAGE + ", "
                + CartTable.Cols.RESTAURANT + ", "
                + CartTable.Cols.FOODNAME + ", "
                + CartTable.Cols.FOODPRICE + ", "
                + CartTable.Cols.AMOUNT + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
