package com.example.software_engineering;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.ArrayList;

public class CustomTimeAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<Schedule> time_schedule_list = null;
    private int nListCnt = 0;





    public CustomTimeAdapter(ArrayList<Schedule> time){
        time_schedule_list = time;
        nListCnt = time_schedule_list.size();
    }


    @Override
    public int getCount() {
        Log.i("TAG", "getCount");
        return nListCnt;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if(inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.time_list_layout, parent, false);
        }

        TextView name_schedule = (TextView) convertView.findViewById(R.id.name_schedule);
        TextView time_schedule = (TextView) convertView.findViewById(R.id.time_schedule);

        name_schedule.setText(time_schedule_list.get(position).Name);
        time_schedule.setText(time_schedule_list.get(position).time_cal());

        /*LinearLayout time_layout = (LinearLayout)convertView.findViewById(R.id.time_list_layout);
        time_layout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(v.getContext(),"위치는: "+position,Toast.LENGTH_SHORT);
            }
        });*/
        return convertView;
    }
}
