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

    public void LoginClick(View V)
    {
        final EditText name = findViewById(R.id.Name);
        final EditText password = findViewById(R.id.Password);
        final ContentValues cv = new ContentValues();
        final SQLiteDatabase UserDataBase = accountDBHelper.getWritableDatabase();

        UserDataBase.delete( "mytable",null,null); //ОТЛАДКА
        UserDataBase.delete( "tasklist",null,null); //ОТЛАДКА

        //===============================================================================================================================
        AlertDialog.Builder a_build = new AlertDialog.Builder(LoginActivity.this);
        a_build.setMessage("Данного профиля не существует. Создать новый?")
                .setPositiveButton("ДА", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                cv.put("name", name.getText().toString());
                                cv.put("password", password.getText().toString());
                                cv.put("raiting",0);
                                cv.put("points",1500);
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

                                UserQuery.close();
                                accountDBHelper.close();
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
        //===============================================================================================================================

        //Проверка на пустые поля логина и пароля
        if(!name.getText().toString().isEmpty()&& !password.getText().toString().isEmpty()) {

            //Делаем запрос имени пользователя
            Cursor UserQuery = UserDataBase.query(
                    "mytable",
                    null,
                    "name == ?",
                    new String[]{name.getText().toString()},
                    null,
                    null,
                    null);


            //Если аккаунта с таким логином не существует
            if(!UserQuery.moveToFirst()) {
                //Предложене создать профиль
                AlertDialog alertDialog = a_build.create();
                alertDialog.show();
            }

            //Если аккаунт с таким логином существует то делаем проверку на правильность введёного пароля
            else if(UserQuery.moveToFirst() && !UserQuery.getString(UserQuery.getColumnIndex("password")).equals(password.getText().toString()))
                Toast.makeText(LoginActivity.this,"Неправильно введён пароль. Повторите попытку",Toast.LENGTH_SHORT).show();

            //Если всё корректно:
           else {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("username", UserQuery.getString(UserQuery.getColumnIndex("name")));
            intent.putExtra("userraiting", UserQuery.getInt(UserQuery.getColumnIndex("raiting")));
            intent.putExtra("userpoints", UserQuery.getInt(UserQuery.getColumnIndex("points")));

            UserQuery.close();
            accountDBHelper.close();
            startActivity(intent);
           }
        }

        //Обработчики пустых строк
        else {
                if(name.getText().toString().isEmpty() && password.getText().toString().isEmpty())
                    Toast.makeText(LoginActivity.this,"Необходимо вветси имя пользователя и пароль",Toast.LENGTH_LONG).show();

                else if (name.getText().toString().isEmpty())
                    Toast.makeText(LoginActivity.this,"Необходимо вветси имя пользователя",Toast.LENGTH_LONG).show();

                else if(password.getText().toString().isEmpty())
                    Toast.makeText(LoginActivity.this,"Необходимо вветси пароль",Toast.LENGTH_LONG).show();
            }
    }
}
