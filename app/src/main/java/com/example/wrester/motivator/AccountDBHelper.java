package com.example.wrester.motivator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccountDBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 2;
    public static final String FIRST_TABLE_NAME = "mytable";
    public static final String SECOND_TABLE_NAME = "tasklist";

    public AccountDBHelper(Context context) {
        super(context, "UserDB", null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + FIRST_TABLE_NAME + "("
                + "name text primary key,"
                + "password text,"
                + "raiting integer,"
                + "points integer"
                 + ");");

        sqLiteDatabase.execSQL("create table " + SECOND_TABLE_NAME + "("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "description text,"
                + "raitingUp integer,"
                + "rairingDown integer,"
                + "pointsUp integer,"
                + "pointsDown integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+ FIRST_TABLE_NAME);
        sqLiteDatabase.execSQL("drop table if exists "+ SECOND_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
