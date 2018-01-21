package com.example.wrester.motivator;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    String Username;
    Integer Raiting;
    Integer Points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView welcome = findViewById(R.id.TitileUsename);
        TextView raiting = findViewById(R.id.raitingView);
        TextView points = findViewById(R.id.pointsView);

        Username = getIntent().getStringExtra("username");
        Raiting = getIntent().getIntExtra("userraiting",10);
        Points = getIntent().getIntExtra("userpoints",1510);

        welcome.setText("Здравствуйте " + Username);
        raiting.setText(Raiting.toString());
        points.setText(Points.toString());
    }

    public void onClickAddTask (View V) {
        Intent intent = new Intent(MainActivity.this,AddTaskActivity.class);
        intent.putExtra("username", Username);
        startActivity(intent);
    }

    public void onClickShowTasks (View V) {
        Intent intent = new Intent(MainActivity.this,TaskViewActivity.class);
        intent.putExtra("username", Username);
        startActivity(intent);
    }

    public void ocClickDeleteAccount(View V){
        //===============================================================================================================================
        AlertDialog.Builder a_build = new AlertDialog.Builder(MainActivity.this);
        a_build.setMessage("Вы действительно хотите удалить учётную запись?")
                .setPositiveButton("ДА", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                AccountDBHelper dbHelper = new AccountDBHelper(MainActivity.this);
                                SQLiteDatabase DB = dbHelper.getWritableDatabase();

                                DB.delete( "mytable","name = ?",new String[]{Username});
                                DB.delete( "tasklist","username = ?",new String[]{Username});

                                DB.close();
                                finish();
                            }
                        }
                )
                .setNegativeButton("НЕТ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        //===============================================================================================================================
        AlertDialog alertDialog = a_build.create();
        alertDialog.show();
    }
}
