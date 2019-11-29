package com.example.software_engineering;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


import java.util.*;

public class ScheduleMainActivity extends AppCompatActivity {

    private ArrayList<Schedule> time_scheduleArrayList;
    private ArrayList<Schedule> place_scheduleArrayList;
    int y=0, m=0, d=0, h=0, mi=0;
    private Spinner group_spinner;
    private Spinner alarm_spinner;

    ArrayList<String> group_list;
    ArrayList<String> alarm_count;
    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> alarmAdapter;


    Calendar calendar = Calendar.getInstance();

    public ScheduleMainActivity()
    {
        time_scheduleArrayList = new ArrayList<Schedule>();
        place_scheduleArrayList = new ArrayList<Schedule>();
    };

    public void add_schedule(String name, String content,double place_x,double place_y, int alarmRepeatCount, int alarmType,Group group)
    {
        place_scheduleArrayList.add(new Schedule(name,content,place_x,place_y,alarmRepeatCount,alarmType, group));
    }
    public void add_schedule(String name, String content,Calendar calendar, int alarmRepeatCount, int alarmType,Group group)
    {
        calendar.set(y,m,d,h,m);////// 날짜 저장 달이 1작다는데 나중에 실험 해보기


        ////////////// 수정할 것 : 시간 순으로 정렬 해야함
        if(time_scheduleArrayList.size()==0)
        {
            time_scheduleArrayList.add(new Schedule(name, content,calendar ,alarmRepeatCount,alarmType,group));
        }
        else {
            for (int i = 0; i < time_scheduleArrayList.size(); i++) {
                if(time_scheduleArrayList.get(i).getCalendar().after(calendar))
                {
                    time_scheduleArrayList.add(new Schedule(name, content,calendar ,alarmRepeatCount,alarmType,group));
                    return;
                }
            }
        }
        time_scheduleArrayList.add(new Schedule(name, content,calendar ,alarmRepeatCount,alarmType,group));
    }

    public void modified_schedule(String name, String content,double place_x,double place_y, int alarmRepeatCount, int alarmType,int index,Group group)
    {
        ////////////// index변수는 레이아웃에서 선택했을때 몇번째 인지 가져오기
        place_scheduleArrayList.remove(index);
        place_scheduleArrayList.add(new Schedule(name,content,place_x,place_y,alarmRepeatCount,alarmType, group));
    }

    public void modified_schedule(String name, String content,int year,int month,int day,int hour,int minute, int alarmRepeatCount, int alarmType,Group group, int index) {
        ////////////// index변수는 레이아웃에서 선택했을때 몇번째 인지 가져오기
        time_scheduleArrayList.remove(index);
        add_schedule(name, content, calendar, alarmRepeatCount, alarmType, group);
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


        Intent intent = new Intent(this.getIntent());

        int i  = intent.getIntExtra("num", 1);


        if(i == 1) { //날짜별 스케쥴
            setContentView(R.layout.activity_time_schedule);

            group_list = new ArrayList<>();
            group_list.add("철수");
            group_list.add("영희");
            group_list.add("람휘");
            group_list.add("녹지");
            arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, group_list);
            group_spinner = findViewById(R.id.spinner_group);
            group_spinner.setAdapter(arrayAdapter);

            alarm_count = new ArrayList<>();
            alarm_count.add("1회");
            alarm_count.add("2회");
            alarm_count.add("3회");
            alarmAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, alarm_count);
            alarm_spinner = findViewById(R.id.spinner_alarm_count);
            alarm_spinner.setAdapter(alarmAdapter);


            Button time_button = findViewById(R.id.time_button); // 스케쥴 시간창 추가
            time_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTime();
                }
            });


            Button date_button = findViewById(R.id.date_button); //스케쥴 날짜창 추가
            date_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDate();
                }
            });

            group_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //spinner에서 가장 위에가 i= 0번~쭈루룩
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            alarm_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //spinner에서 가장 위에가 i= 0번~쭈루룩
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            Button exit_schedule = findViewById(R.id.exit_add_schedule_button); //스케쥴 추가에 취소 버튼
            exit_schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });


            Button store_schedule_button = findViewById(R.id.store_schedule_button); // 스케쥴 시간창 추가

       /* time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content= null;//////// 이거 나중에 레이아웃에서 추가해줘여ㅑ됨;;
                //add_schedule(R.id.schedule_name_input,content,time,alarmRepeatCount,alarmType,group);
                // add_schedule(name, content, calendar, alarmRepeatCount, alarmType, group);
            }
        });*/

        }

        else if(i ==2) { //시간별 스케쥴
            setContentView(R.layout.activity_location_schedule);

            group_list = new ArrayList<>();
            group_list.add("철수");
            group_list.add("영희");
            group_list.add("람휘");
            group_list.add("녹지");
            arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, group_list);
            group_spinner = findViewById(R.id.spinner_group);
            group_spinner.setAdapter(arrayAdapter);

            alarm_count = new ArrayList<>();
            alarm_count.add("1회");
            alarm_count.add("2회");
            alarm_count.add("3회");
            alarmAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, alarm_count);
            alarm_spinner = findViewById(R.id.spinner_alarm_count);
            alarm_spinner.setAdapter(alarmAdapter);



            Button mapbutton = findViewById(R.id.Map_button); //맵 버튼
            mapbutton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent map = new Intent(ScheduleMainActivity.this, MapsActivity.class);
                    startActivity(map);
                }
            });

            group_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //spinner에서 가장 위에가 i= 0번~쭈루룩
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            alarm_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //spinner에서 가장 위에가 i= 0번~쭈루룩
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            Button exit_schedule = findViewById(R.id.exit_add_schedule_button); //스케쥴 추가에 취소 버튼
            exit_schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });


            Button store_schedule_button = findViewById(R.id.store_schedule_button); //
       /* time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content= null;//////// 이거 나중에 레이아웃에서 추가해줘여ㅑ됨;;
                //add_schedule(R.id.schedule_name_input,content,time,alarmRepeatCount,alarmType,group);
                // add_schedule(name, content, calendar, alarmRepeatCount, alarmType, group);
            }
        });*/

        }














    }


    void showDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Button date_button = findViewById(R.id.date_button);
                y = year;
                m = month+1;
                d = dayOfMonth;
                date_button.setText(y+"년 "+m+"월 "+d+"일");
            }
        },2019, 1, 11);

        datePickerDialog.setMessage("메시지");
        datePickerDialog.show();
    }

    void showTime() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Button time_button = findViewById(R.id.time_button);
                h = hourOfDay;
                mi = minute;
                time_button.setText(h+"시 "+mi+"분");
            }
        }, 21, 12, true);

        timePickerDialog.setMessage("메시지");
        timePickerDialog.show();
    }
}
