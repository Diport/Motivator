package com.example.wrester.motivator;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    AccountDBHelper accountDBHelper; //Объявляем БД аакаунтов

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accountDBHelper = new AccountDBHelper(this); //Инициализируем БД
    }

    public void MyClick(View V)
    {
        final EditText name = findViewById(R.id.Name);
        final ContentValues cv = new ContentValues();
        final SQLiteDatabase UserDataBase = accountDBHelper.getWritableDatabase();

        UserDataBase.delete( "mytable",null,null); //ОТЛАДКА

        AlertDialog.Builder a_build = new AlertDialog.Builder(LoginActivity.this);
        a_build.setMessage("Данного профиля не существует. Создать новый?")
                .setPositiveButton("ДА", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                cv.put("raiting",0);
                                cv.put("points",1500);
                                cv.put("name", name.getText().toString());
                                UserDataBase.insert("mytable",null, cv);

                                Cursor UserQuery = UserDataBase.query(
                                        "mytable",
                                        null,
                                        "name == ?",
                                        new String[]{ name.getText().toString()},
                                        null,
                                        null,
                                        null);
                                UserQuery.moveToFirst();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("username", UserQuery.getString(UserQuery.getColumnIndex("name")));
                                intent.putExtra("userraiting", UserQuery.getInt(UserQuery.getColumnIndex("raiting")));
                                intent.putExtra("userpoints", UserQuery.getInt(UserQuery.getColumnIndex("points")));
                                startActivity(intent);
                            }
                        }
                )
                .setNegativeButton("НЕТ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        if(!name.getText().toString().equals("")) {
            //Делаем запрос имени пользователя
            Cursor c = UserDataBase.query(
                    "mytable",
                    new String[]{"name"},
                    "name == ?",
                    new String[]{name.getText().toString()},
                    null,
                    null,
                    null);
            //--------------------------------

            //Проверка на null
            if(!c.moveToFirst()) {
                //Предложене создать профиль
                AlertDialog alertDialog = a_build.create();
                alertDialog.show();
            }

           else {
                Cursor UserQuery = UserDataBase.query(
                   "mytable",
                   null,
                   "name == ?",
                   new String[]{ name.getText().toString()},
                   null,
                   null,
                   null);
           UserQuery.moveToFirst();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("username", UserQuery.getString(UserQuery.getColumnIndex("name")));
            intent.putExtra("userraiting", UserQuery.getInt(UserQuery.getColumnIndex("raiting")));
            intent.putExtra("userpoints", UserQuery.getInt(UserQuery.getColumnIndex("points")));
            startActivity(intent);
           }
        }
        else { Toast.makeText(LoginActivity.this,"Необходимо вветси имя пользователя",Toast.LENGTH_LONG).show(); }
    }
}
