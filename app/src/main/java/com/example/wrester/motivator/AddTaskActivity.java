package com.example.wrester.motivator;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorTreeAdapter;
import android.widget.EditText;
import android.widget.Toast;

public class AddTaskActivity extends AppCompatActivity {

    String Username;
    AccountDBHelper accountDBHelper;
    EditText Title;
    EditText Points;
    EditText Raiting;
    Button buttonOk;
    int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        accountDBHelper = new AccountDBHelper(this);
        Username = getIntent().getStringExtra("username");
        buttonOk = findViewById(R.id.Ok);
        if(getIntent().hasExtra("ID"))
        {
            ID =getIntent().getIntExtra("ID",-1);
            if (ID !=-1){
                SQLiteDatabase DB = accountDBHelper.getReadableDatabase();
                Title = findViewById(R.id.titleText);
                Points = findViewById(R.id.pointsText);
                Raiting = findViewById(R.id.raitingText);
                Cursor cursor = DB.query(
                        AccountDBHelper.SECOND_TABLE_NAME,
                        null,
                        "id == " + ID,
                        null,
                        null,
                        null,
                        null);

                if(cursor.moveToFirst()) {
                    Title.setText(cursor.getString(cursor.getColumnIndex("title")));
                    Points.setText(cursor.getString(cursor.getColumnIndex("pointsUp")));
                    Raiting.setText(cursor.getString(cursor.getColumnIndex("raitingUp")));

                    cursor.close();
                    DB.close();
                }
                else {cursor.close(); DB.close(); finish();}
                buttonOk.setOnClickListener(ChangeListener);
            }
            else finish();
        }
        else buttonOk.setOnClickListener(AddListener);
    }


    View.OnClickListener AddListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) //Запись задачи в БД
        {
            final ContentValues cv = new ContentValues();
            final SQLiteDatabase UserDataBase = accountDBHelper.getWritableDatabase();
            Title = findViewById(R.id.titleText);
            Points = findViewById(R.id.pointsText);
            Raiting = findViewById(R.id.raitingText);

            if (Points.getText().toString().isEmpty() || Raiting.getText().toString().isEmpty())
                Toast.makeText(AddTaskActivity.this, "Пожалуйста зполните поля очков и рейтинга", Toast.LENGTH_LONG).show();
            else {
                cv.put("username", Username);
                cv.put("title", Title.getText().toString());
                cv.put("raitingUp", Raiting.getText().toString());
                cv.put("rairingDown", Raiting.getText().toString());
                cv.put("pointsUp", Points.getText().toString());
                cv.put("pointsDown", Points.getText().toString());
                UserDataBase.insert("tasklist", null, cv);

                UserDataBase.close();
                finish();
            }
        }
    };

    View.OnClickListener ChangeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final ContentValues cv = new ContentValues();
            final SQLiteDatabase UserDataBase = accountDBHelper.getWritableDatabase();

            if (Points.getText().toString().isEmpty() || Raiting.getText().toString().isEmpty())
                Toast.makeText(AddTaskActivity.this, "Пожалуйста зполните поля очков и рейтинга", Toast.LENGTH_LONG).show();
            else {
                cv.put("title", Title.getText().toString());
                cv.put("raitingUp", Raiting.getText().toString());
                cv.put("rairingDown", Raiting.getText().toString());
                cv.put("pointsUp", Points.getText().toString());
                cv.put("pointsDown", Points.getText().toString());
                UserDataBase.update(AccountDBHelper.SECOND_TABLE_NAME,cv,"id ==" + ID,null);

                UserDataBase.close();
                finish();
            }
        }
    };

    public void onClickCancel (View V) { finish(); }
}
