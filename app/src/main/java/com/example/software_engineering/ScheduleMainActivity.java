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

    private ArrayList<Schedule> scheduleArrayList;
    int y=0, m=0, d=0, h=0, mi=0;
    public ScheduleMainActivity()
    {
        scheduleArrayList = new ArrayList<Schedule>();
    };

    public void add_schedule(String name, String content,double place_x,double place_y, int alarmRepeatCount, int alarmType)
    {
            scheduleArrayList.add(new Schedule(name,content,place_x,place_y,alarmRepeatCount,alarmType));
    }
    public void add_schedule(String name, String content, String place,int time, int alarmRepeatCount, int alarmType)
    {
        ////////////// 수정할 것 : 시간 순으로 정렬 해야함
            scheduleArrayList.add(new Schedule(name, content, place,time,alarmRepeatCount,alarmType));
    }

    public void modified_schedule(String name, String content, String place,int time, int alarmRepeatCount, int alarmType, int index)
    {
        ////////////// index변수는 레이아웃에서 선택했을때 몇번째 인지 가져오기
        scheduleArrayList.remove(index);
        scheduleArrayList.add(index,new Schedule(name, content, place,time,alarmRepeatCount,alarmType));
    }

    public void remove_schedule(int index)
    {
        scheduleArrayList.remove(index);
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

        Button mapbutton = findViewById(R.id.Map_button); //맵 버튼
        mapbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent map = new Intent(ScheduleMainActivity.this, MapsActivity.class);
                startActivity(map);
            }
        });

        Button exit_schedule = findViewById(R.id.exit_add_schedule_button); //스케쥴 추가에 취소 버튼
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
