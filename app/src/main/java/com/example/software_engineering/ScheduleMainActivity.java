package com.example.software_engineering;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;



import java.util.*;

public class ScheduleMainActivity extends AppCompatActivity {

    private ArrayList<Schedule> time_scheduleArrayList;
    private ArrayList<Schedule> place_scheduleArrayList;
    int y=0, m=0, d=0, h=0, mi=0;
    public ScheduleMainActivity()
    {
        time_scheduleArrayList = new ArrayList<Schedule>();
        place_scheduleArrayList = new ArrayList<Schedule>();
    };

    public void add_schedule(String name, String content,double place_x,double place_y, int alarmRepeatCount, int alarmType,Group group)
    {
        place_scheduleArrayList.add(new Schedule(name,content,place_x,place_y,alarmRepeatCount,alarmType, group));
    }
    public void add_schedule(String name, String content,int time, int alarmRepeatCount, int alarmType,Group group)
    {

        ////////////// 수정할 것 : 시간 순으로 정렬 해야함
        if(time_scheduleArrayList.size()==0)
        {
            time_scheduleArrayList.add(new Schedule(name, content, time,alarmRepeatCount,alarmType,group));
        }
        else {
            for (int i = 0; i < time_scheduleArrayList.size(); i++) {
                if(time_scheduleArrayList.get(i).getTime()>time)
                {
                    time_scheduleArrayList.add(i,new Schedule(name, content, time,alarmRepeatCount,alarmType,group));
                    break;
                }
            }
        }
        time_scheduleArrayList.add(new Schedule(name, content, time,alarmRepeatCount,alarmType,group));
    }

    public void modified_schedule(String name, String content,double place_x,double place_y, int alarmRepeatCount, int alarmType,int index,Group group)
    {
        ////////////// index변수는 레이아웃에서 선택했을때 몇번째 인지 가져오기
        place_scheduleArrayList.remove(index);
        place_scheduleArrayList.add(new Schedule(name,content,place_x,place_y,alarmRepeatCount,alarmType, group));
    }

    public void modified_schedule(String name, String content, int time, int alarmRepeatCount, int alarmType, int index,Group group)
    {
        ////////////// index변수는 레이아웃에서 선택했을때 몇번째 인지 가져오기
        time_scheduleArrayList.remove(index);
        add_schedule(name, content,time,alarmRepeatCount,alarmType,group);
    }

    public void remove_time_schedule(int index)
    {
        time_scheduleArrayList.remove(index);
    }
    public void remove_palce_schedule(int index)
    {
        place_scheduleArrayList.remove(index);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Button date_button = findViewById(R.id.date_button); //스케쥴 날짜창 추가
        date_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showDate();
            }
        });

        Button time_button = findViewById(R.id.time_button); // 스케쥴 시간창 추가
        time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTime();
            }
        });

        Button exit_schedule = findViewById(R.id.exit_button);
        exit_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }


    void showDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                y = year;
                m = month+1;
                d = dayOfMonth;

            }
        },2019, 1, 11);

        datePickerDialog.setMessage("메시지");
        datePickerDialog.show();
    }

    void showTime() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                h = hourOfDay;
                mi = minute;

            }
        }, 21, 12, true);

        timePickerDialog.setMessage("메시지");
        timePickerDialog.show();
    }
}