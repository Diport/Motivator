package com.example.wrester.motivator;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ShowDetailsOfTaskActivity extends AppCompatActivity {

    int taskID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details_of_task);

        taskID = getIntent().getIntExtra("identificator",-1);
        AccountDBHelper dbHelper = new AccountDBHelper(this);
        SQLiteDatabase DB = dbHelper.getReadableDatabase();

        Cursor cursor = DB.query(
                AccountDBHelper.SECOND_TABLE_NAME,
                null,
                "id ==" + taskID,
                null,
                null,
                null,
                null);

    }
}
