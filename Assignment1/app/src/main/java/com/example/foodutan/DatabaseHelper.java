package com.example.foodutan;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.foodutan.DatabaseSchema.LoginTable;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "foodordering.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + DatabaseSchema.LoginTable.NAME + "("
                + LoginTable.Cols.ID + ", "
                + LoginTable.Cols.EMAIL + ", "
                + LoginTable.Cols.USERNAME + ", "
                + LoginTable.Cols.PASSWORD + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
