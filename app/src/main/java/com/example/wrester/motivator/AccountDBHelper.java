package com.example.wrester.motivator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Wrester on 08.12.2017.
 */

//Помощник по созданию БД. Проверяет БД на сущетвование и актуальность версии.
//Использует ссответствующий метод для поправки значений.

public class AccountDBHelper extends SQLiteOpenHelper {

    public AccountDBHelper(Context context) {
        super(context, "UserDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table mytable ("
                + "name string primary key,"
                + "raiting integer,"
                + "points integer" + ");");

        sqLiteDatabase.execSQL("create table tasklist ("
                + "id integer primary key autoincrement,"
                + "name string,"
                + "description string,"
                + "raitingUp integer,"
                + "rairingDown integer,"
                + "pointsUp integer,"
                + "pointsDown integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
