package com.example.weatherappui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity3 extends AppCompatActivity {
    EditText City;

    public static final String city="com.example.weatherappui.extra.NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    }
    public void openActivity(View view){
        Intent intent=new Intent(this,MainActivity.class);
        City=findViewById(R.id.City);
        String nameText=City.getText().toString();
        intent.putExtra(city,nameText);
        startActivity(intent);
    }
}