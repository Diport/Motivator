package com.example.wrester.motivator;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


        TextView welcome = (TextView)findViewById(R.id.TitileUsename);
        TextView raiting = (TextView)findViewById(R.id.raitingView);
        TextView points = (TextView)findViewById(R.id.pointsView);
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
}
