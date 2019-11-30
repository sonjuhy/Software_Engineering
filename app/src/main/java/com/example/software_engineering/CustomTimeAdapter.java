package com.example.software_engineering;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomTimeAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<Schedule> time_schedule = null;
    private int nListCnt = 0;

    public CustomTimeAdapter(ArrayList<Schedule> time){
        time_schedule = time;
        nListCnt = time_schedule.size();
    }


    @Override
    public int getCount() {
        Log.i("TAG", "getCount");
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
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

        return convertView;
    }
}
