package com.example.weatherappui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MainActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationProviderClient;
    double lat,lon;
    ApiInterface apiInterface;
    Retrofit retrofit;
    String appid="2df3734e7e22db6c0b6a35247e1b7f65";
    TextView date,time,town,des,temparature,feels,water,wind,thermo,sunrise,sunset;
    Button btn_hour, btn_daily;
    ConstraintLayout layout;
    private String setDate(long unix)
    {
        Date date=new Date(unix*1000L);
        SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
        format.setTimeZone(TimeZone.getDefault());
        String stdate=format.format(date);
        return stdate;

    }
    private String setTime(long unix)
    {
        Date date=new Date(unix*1000L);
        SimpleDateFormat format=new SimpleDateFormat("HH:mm");
        format.setTimeZone(TimeZone.getDefault());
        String stdate=format.format(date);
        return stdate;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout=findViewById(R.id.Mylayout);
        date=findViewById(R.id.date);
        time=findViewById(R.id.time);
        town=findViewById(R.id.town);
        des=findViewById(R.id.des);
        temparature=findViewById(R.id.temparature);
        feels=findViewById(R.id.feels);
        water=findViewById(R.id.water);
        wind=findViewById(R.id.wind);
        thermo=findViewById(R.id.thermo);
        sunrise=findViewById(R.id.sunrise);
        sunset=findViewById(R.id.sunset);
        btn_daily=findViewById(R.id.Daily);
        btn_hour=findViewById(R.id.Hourly);
        Intent intent =getIntent();
        String cityName=intent.getStringExtra(MainActivity3.city);
        retrofit=new Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/").addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface=retrofit.create(ApiInterface.class);
        Log.e(TAG, "onCreate: not going" );
        apiInterface.getCityWeather(cityName,"metric",appid).enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                Pojo pojo=response.body();

                String main=pojo.getWeather().get(0).getMain();
                 lat =pojo.getCoord().getLat();
                lon =pojo.getCoord().getLon();
                date.setText(setDate(pojo.getDt()));
                time.setText(setTime(pojo.getDt()));
                town.setText(pojo.getName());
                des.setText(pojo.getWeather().get(0).getDescription());
                temparature.setText(Double.toString(pojo.getMain().getTemp())+"°C");
                feels.setText(Double.toString(pojo.getMain().getFeelsLike())+"°C");
                water.setText(Integer.toString(pojo.getMain().getHumidity()));
                wind.setText(Double.toString(pojo.getWind().getSpeed()));
                thermo.setText(Double.toString(pojo.getMain().getTempMax()));
                sunrise.setText(setTime(pojo.getSys().getSunrise()));
                sunset.setText(setTime(pojo.getSys().getSunset()));
                btn_daily.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent daily_intent=new Intent(MainActivity.this,MainActivity2.class);
                        daily_intent.putExtra("lat",lat);
                        daily_intent.putExtra("lon",lon);
                        startActivity(daily_intent);
                    }
                });
                btn_hour.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent hourly_intent=new Intent(MainActivity.this,MainActivity4.class);
                        hourly_intent.putExtra("lat",lat);
                        hourly_intent.putExtra("lon",lon);
                        startActivity(hourly_intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getLocalizedMessage() );

            }
        });


    }


}
