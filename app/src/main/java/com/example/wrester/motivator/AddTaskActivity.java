package com.example.wrester.motivator;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddTaskActivity extends AppCompatActivity {

    String Username;
    AccountDBHelper accountDBHelper;
    EditText Description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        accountDBHelper = new AccountDBHelper(this);
        Username = getIntent().getStringExtra("username");
    }

    public void onClick (View V) //Запись задачи в БД
    {
        final ContentValues cv = new ContentValues();
        final SQLiteDatabase UserDataBase = accountDBHelper.getWritableDatabase();

        Description = (EditText)findViewById(R.id.descriptionText);
        cv.put("name",Username);
        cv.put("description",Description.toString());
        UserDataBase.insert("tasks",null,cv);
    }
}
