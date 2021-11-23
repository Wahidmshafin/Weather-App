package com.example.weatherappui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

public class MainActivity2 extends AppCompatActivity {
    RecyclerView recyclerView;
    CustomAdapter dailyadapter;
    Double lat,lon;
    ApiInterface apiInterface;
    String appid="2df3734e7e22db6c0b6a35247e1b7f65";
    ArrayList<PostPojo.Daily>dailyList;
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView=findViewById(R.id.recyclerView);
        Intent intent=getIntent();
        lat=intent.getDoubleExtra("lat",0.0);
        lon= intent.getDoubleExtra("lon",0.0);
        Toast.makeText(this, "hey"+lat +" "+lon, Toast.LENGTH_SHORT).show();
        retrofit=new Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/").addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface=retrofit.create(ApiInterface.class);
        apiInterface.getReWeather(lat,lon,"minutely",appid,"metric").enqueue(new Callback<PostPojo>() {
            @Override
            public void onResponse(Call<PostPojo> call, Response<PostPojo> response) {
                if(response.isSuccessful())
                {
                    dailyList=response.body().getDaily();
                    Log.e("Tag", "onResponse: Failed" );
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity2.this));
                    dailyadapter=new CustomAdapter(dailyList);
                    recyclerView.setAdapter(dailyadapter);

                }
            }

            @Override
            public void onFailure(Call<PostPojo> call, Throwable t) {
                Log.e(TAG, "onFailure: " +t.getLocalizedMessage());

            }
        });
    }
}