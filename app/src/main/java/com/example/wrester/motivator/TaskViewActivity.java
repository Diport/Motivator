package com.example.wrester.motivator;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class TaskViewActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout LayoutBox;
    String Username;
    AccountDBHelper dbHelper = new AccountDBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);
        LayoutBox = findViewById(R.id.L_Box);
        Username = getIntent().getStringExtra("username");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Refresh();
    }

    private void Refresh() {
        LayoutBox.removeAllViews();
        Button[] TArr = GetUserTasks(Username);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (TArr!=null)
            for (int i=0;i<TArr.length;i++)
                LayoutBox.addView(TArr[i],params);
    }

    private Button[] GetUserTasks(String username) {
        SQLiteDatabase DB = dbHelper.getReadableDatabase();
        ArrayList<Button> res= new ArrayList<>();

        Cursor TasksQuery = DB.query(
                AccountDBHelper.SECOND_TABLE_NAME,
                new String[]{"id","title"},
                "username == ?",
                new String[]{ username },
                null,
                null,
                null);

        if (TasksQuery.moveToFirst()) {
           do {
               Button btn = new Button(this);
               btn.setId(TasksQuery.getInt(TasksQuery.getColumnIndex("id")));
               btn.setText(TasksQuery.getString(TasksQuery.getColumnIndex("title")));
               btn.setOnClickListener(this);
               res.add(btn);
           } while (TasksQuery.moveToNext());
        }
        else { DB.close(); TasksQuery.close(); return null; }

        DB.close();
        TasksQuery.close();
        return res.toArray(new Button[res.size()]);
    }

    public void onClick(View V) {
        int identificator = V.getId();
        Intent intent = new Intent(this,ShowDetailsOfTaskActivity.class);
        intent.putExtra("identificator",identificator);
        startActivity(intent);
    }
}
