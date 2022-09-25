package com.example.foodutan;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.foodutan.DatabaseSchema.LoginTable;

public class LoginCursor extends CursorWrapper{
    public LoginCursor(Cursor cursor) {
        super(cursor);
    }

    public loginData getLoginDetail() {
        int id = getInt(getColumnIndex(LoginTable.Cols.ID));
        String email = getString(getColumnIndex(LoginTable.Cols.EMAIL));
        String username = getString(getColumnIndex(LoginTable.Cols.USERNAME));
        String password = getString(getColumnIndex(LoginTable.Cols.PASSWORD));
        return new loginData(id, email, username, password);
    }
}
