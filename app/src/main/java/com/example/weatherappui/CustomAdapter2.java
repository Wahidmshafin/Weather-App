package com.example.weatherappui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class CustomAdapter2 extends RecyclerView.Adapter<CustomAdapter2.ViewHolder> {

    String TAG="ERROR";
    private String setDate(long unix)
    {
        Date date=new Date(unix*1000L);
        SimpleDateFormat format=new SimpleDateFormat("HH:mm");
        format.setTimeZone(TimeZone.getDefault());
        String stdate=format.format(date);
        return stdate;

    }

    private ArrayList<PostPojo.Hourly>hourlyList;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextView day,hum,des,air,tmp;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.timeh);
            day=view.findViewById(R.id.day);
            des=view.findViewById(R.id.ds);
            tmp=view.findViewById(R.id.tmp);
            hum=view.findViewById(R.id.hum);

        }

    }

    public CustomAdapter2(ArrayList<PostPojo.Hourly>hourly) {
        hourlyList = hourly;
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CustomAdapter2.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new CustomAdapter2.ViewHolder(view);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CustomAdapter2.ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.day.setText(setDate(hourlyList.get(position).getDt()));
        String des;
        if(hourlyList.get(position).getWeather().get(0).getDescription()==null)
        {
            des="Normal";
        }
        else
        {
            des=hourlyList.get(position).getWeather().get(0).getDescription();
        }
        Log.e(TAG, "onBindViewHolder: "+des );
        viewHolder.des.setText(des);
        viewHolder.tmp.setText(Double.toString(hourlyList.get(position).getTemp())+"°C");
        viewHolder.hum.setText(Integer.toString((hourlyList).get(position).getHumidity())+"%");
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return 12;
    }
}
