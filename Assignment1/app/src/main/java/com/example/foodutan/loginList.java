package com.example.foodutan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;

import com.example.foodutan.DatabaseSchema.LoginTable;

import java.util.ArrayList;
import java.util.List;

public class loginList {
    private SQLiteDatabase db;
    private List<loginData> loginDat = new ArrayList<>();

    public loginList() {}

    public void load(Context context) {
        this.db = new LoginDBHelper(context.getApplicationContext()).getWritableDatabase();
        loginDat = getDBLoginList();
    }

    public int size() {
        return loginDat.size();
    }

    public loginData getData(int num) {
        return loginDat.get(num);
    }

    //Check if the email exist in database, if cursor return 1 means true
    public boolean checkMailExisted(EditText email) {
        String mail = email.getText().toString();
        boolean existed = false;

        Cursor cursor = db.rawQuery("SELECT EXISTS (SELECT * FROM " + LoginTable.NAME + " WHERE " + LoginTable.Cols.EMAIL + "='" + mail + "');", null);
        cursor.moveToFirst();

        if(cursor.getInt(0) == 1) {
            existed = true;
        }

        return existed;
    }

    //Check if the email existed, if existed then check if the password is same. If cursor = 1 means the login is success
    public boolean attemptLogin(EditText email, EditText password) {
        String mail = email.getText().toString();
        String pass = password.getText().toString();
        boolean success = false;

        Cursor cursor = db.rawQuery("SELECT EXISTS (SELECT " + LoginTable.Cols.EMAIL + "='" + mail + "' FROM " + LoginTable.NAME + " WHERE " + LoginTable.Cols.PASSWORD + "='" + pass + "');", null);
        cursor.moveToFirst();

        if(cursor.getInt(0) == 1) {
            success = true;
        }

        return success;
    }

    //add an email and password into database
    public int add(loginData newLoginData) {
        loginDat.add(newLoginData);

        ContentValues cv = new ContentValues();
        cv.put(LoginTable.Cols.ID, newLoginData.getID());
        cv.put(LoginTable.Cols.EMAIL, newLoginData.getEmail());
        cv.put(LoginTable.Cols.USERNAME, newLoginData.getUsername());
        cv.put(LoginTable.Cols.PASSWORD, newLoginData.getPassword());

        db.insert(LoginTable.NAME, null, cv);
        return loginDat.size() - 1;
    }

    //remove an email - not used
    public void delete(loginData removeLoginData) {
        loginDat.remove(removeLoginData);
        String id = String.valueOf(removeLoginData.getID());

        db.execSQL("delete from " + LoginTable.NAME + " where " + LoginTable.Cols.ID + "=" + id + ";");
    }

    //get all the items in database and store in an array list
    public ArrayList<loginData> getDBLoginList() {
        ArrayList<loginData> loginList = new ArrayList<>();
        Cursor cursor = db.query(LoginTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        LoginCursor loginCursor = new LoginCursor(cursor);

        try {
            loginCursor.moveToFirst();
            while (!loginCursor.isAfterLast()) {
                loginList.add(loginCursor.getLoginDetail());
                loginCursor.moveToNext();
            }
        } finally {
            loginCursor.close();
        }
        return loginList;
    }

    //Used to clear Login Data
    public void clearDB() {
        db.delete(LoginTable.NAME, null, null);
    }
}
