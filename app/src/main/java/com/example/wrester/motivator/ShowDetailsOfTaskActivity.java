package com.example.wrester.motivator;

import android.animation.TypeConverter;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ShowDetailsOfTaskActivity extends AppCompatActivity {

    AccountDBHelper dbHelper = new AccountDBHelper(this);
    int taskID;
    TextView NameOfTask;
    TextView Description;
    TextView Points;
    TextView Raiting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details_of_task);

        NameOfTask = findViewById(R.id.NameOfTask);
        Description = findViewById(R.id.Description);
        Points = findViewById(R.id.Points);
        Raiting = findViewById(R.id.Raiting);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SQLiteDatabase DB = dbHelper.getReadableDatabase();
        taskID = getIntent().getIntExtra("identificator",-1);
        if(taskID !=-1) {
            Cursor cursor = DB.query(
                    AccountDBHelper.SECOND_TABLE_NAME,
                    null,
                    "id ==" + taskID,
                    null,
                    null,
                    null,
                    null);

            if(cursor.moveToFirst()) {
                NameOfTask.setText(cursor.getString(cursor.getColumnIndex("title")));
                String buf = cursor.getString(cursor.getColumnIndex("description"));
                if (buf != null) Description.setText(buf);
                else Description.setText("");
                Points.setText(( cursor.getString(cursor.getColumnIndex("pointsUp"))));
                Raiting.setText(cursor.getString(cursor.getColumnIndex("raitingUp")));
            }
            cursor.close();
        }
        DB.close();
    }

    public void onClickDelete(View V) {
        SQLiteDatabase DB = dbHelper.getWritableDatabase();
        DB.delete(AccountDBHelper.SECOND_TABLE_NAME,"id ==" + taskID,null);

        DB.close();
        finish();
    }

    public void onClickChange(View V) {
        Intent intent = new Intent(this,AddTaskActivity.class);
        intent.putExtra("ID",taskID);
        startActivity(intent);
    }
}
