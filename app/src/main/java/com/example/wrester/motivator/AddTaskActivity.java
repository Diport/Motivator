package com.example.wrester.motivator;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddTaskActivity extends AppCompatActivity {

    String Username;
    AccountDBHelper accountDBHelper;
    EditText Title;
    EditText Points;
    EditText Raiting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        accountDBHelper = new AccountDBHelper(this);
        Username = getIntent().getStringExtra("username");
    }

    public void onClickAdd (View V) //Запись задачи в БД
    {
        final ContentValues cv = new ContentValues();
        final SQLiteDatabase UserDataBase = accountDBHelper.getWritableDatabase();
        Title = findViewById(R.id.titleText);
        Points = findViewById(R.id.pointsText);
        Raiting = findViewById(R.id.raitingText);

        if(Points.getText().toString().isEmpty()  || Raiting.getText().toString().isEmpty())
            Toast.makeText(AddTaskActivity.this,"Пожалуйста зполните поля очков и рейтинга",Toast.LENGTH_LONG).show();
        else {
            cv.put("name", Username);
            cv.put("title", Title.toString());
            cv.put("raitingUp", Raiting.toString());
            cv.put("rairingDown", Raiting.toString());
            cv.put("pointsUp", Points.toString());
            cv.put("pointsDown", Points.toString());
            UserDataBase.insert("tasklist", null, cv);

            UserDataBase.close();
            finish();
        }
    }

    public void onClickCancel (View V) { finish(); }
}
