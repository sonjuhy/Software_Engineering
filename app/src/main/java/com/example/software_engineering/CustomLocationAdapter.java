package com.example.software_engineering;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.Calendar;
import java.util.ArrayList;

public class CustomLocationAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<Schedule> location_schedule_list = null;
    private int nListCnt = 0;





    public CustomLocationAdapter(ArrayList<Schedule> location){
        location_schedule_list = location;
        nListCnt = location_schedule_list.size();
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
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if(inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.location_list_layout, parent, false);
        }

        TextView name_schedule = (TextView) convertView.findViewById(R.id.name_schedule);
        TextView location_schedule = (TextView) convertView.findViewById(R.id.location_schedule);

        name_schedule.setText(location_schedule_list.get(position).Name);
        location_schedule.setText(location_schedule_list.get(position).locationXY());
        return convertView;
    }
}
