package com.example.software_engineering;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import java.util.Calendar;
import android.widget.Toast;

import java.util.Calendar;


import java.util.*;

public class ModifySchedule extends AppCompatActivity {

    private ArrayList<Schedule> time_scheduleArrayList;
    private ArrayList<Schedule> location_scheduleArrayList;
    int y=0, m=0, d=0, h=0, mi=0;
    int schedule_sound =0;
    int schedule_vibration =0;
    int position=0;
    double location_x=0, location_y=0;
    String schedule_name;
    private Spinner group_spinner;


    ArrayList<String> group_list;
    ArrayList<String> alarm_count;
    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> alarmAdapter;
    Calendar calendar = Calendar.getInstance();


    public ModifySchedule()
    {
        time_scheduleArrayList = new ArrayList<Schedule>();
        location_scheduleArrayList = new ArrayList<Schedule>();
    }

    public void modified_schedule(String name, String content,Calendar calendar,
                                  int alarmRepeatCount,int sound ,int vibration,Group group,int index)
    {
        ////////////// index변수는 레이아웃에서 선택했을때 몇번째 인지 가져오기
        location_scheduleArrayList.remove(index);
        location_scheduleArrayList.add(new Schedule(name,content,calendar,alarmRepeatCount,sound,vibration, group));
    }

    public void modified_schedule(String name, String content,double place_x,double place_y,
                                  int alarmRepeatCount,int sound ,int vibration,Group group,int index)
    {
        ////////////// index변수는 레이아웃에서 선택했을때 몇번째 인지 가져오기
        location_scheduleArrayList.remove(index);
        location_scheduleArrayList.add(new Schedule(name,content,place_x,place_y,alarmRepeatCount,sound,vibration, group));
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this.getIntent());
        int i  = intent.getIntExtra("num", 1);
        if(i==1) {
            time_scheduleArrayList = (ArrayList<Schedule>) intent.getSerializableExtra("list");
            y=time_scheduleArrayList.get(position).calendar.YEAR;
            m=time_scheduleArrayList.get(position).calendar.MONTH;
            d=time_scheduleArrayList.get(position).calendar.DATE;
            h=time_scheduleArrayList.get(position).calendar.HOUR;
            mi=time_scheduleArrayList.get(position).calendar.MINUTE;
            schedule_sound =time_scheduleArrayList.get(position).Sound;
            schedule_vibration =time_scheduleArrayList.get(position).Vibration;

        }
        else if(i==2) {
            location_scheduleArrayList = (ArrayList<Schedule>) intent.getSerializableExtra("list");
            location_x = location_scheduleArrayList.get(position).Place_x;
            location_y = location_scheduleArrayList.get(position).Place_y;
            schedule_sound =location_scheduleArrayList.get(position).Sound;
            schedule_vibration =location_scheduleArrayList.get(position).Vibration;
        }

        position  = intent.getIntExtra("position", 1);
        group_list= (ArrayList<String>)intent.getSerializableExtra("group");



/////////////////////////////////////////////////////////////////////////////////////////////////// 벨소리 체크 박스
        class soundSwitchListener implements CompoundButton.OnCheckedChangeListener{
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    schedule_sound  = 1;
                else
                    schedule_vibration = 0;
            }
        }/////////////////////////////// 문제 있을수 있음!
        class vibrationSwitchListener implements CompoundButton.OnCheckedChangeListener{
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    schedule_sound =1;
                else
                    schedule_vibration=0;
            }
        }/////////////////////////////// 문제 있을수 있음!
///////////////////////////////////////////////////////////////////////////////////////////////////
        if(i == 1) { //날짜별 스케쥴
            setContentView(R.layout.activity_time_schedule);
            EditText edit_name = findViewById(R.id.schedule_name_input);
            edit_name.setText(time_scheduleArrayList.get(position).Name);

            arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, group_list);
            group_spinner = findViewById(R.id.spinner_group);
            group_spinner.setAdapter(arrayAdapter);



            Button time_button = findViewById(R.id.time_button); // 스케쥴 시간창 추가
            time_button.setText(h+"시 "+mi+"분");
            time_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTime();
                }
            });


            Button date_button = findViewById(R.id.date_button); //스케쥴 날짜창 추가
            date_button.setText(y+"년 "+m+"월 "+d+"일");
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

            Switch ring_switch = findViewById(R.id.bell_switch);//알람음
            if (schedule_sound==1)
                ring_switch.setChecked(true);
            else
                ring_switch.setChecked((false));

            Switch vibration_switch = findViewById(R.id.vibration_switch);//알람음
            if (schedule_vibration==1)
                vibration_switch.setChecked(true);
            else
                vibration_switch.setChecked((false));
            //진동


            Button exit_schedule = findViewById(R.id.exit_add_schedule_button); //스케쥴 추가에 취소 버튼
            exit_schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });




            Button store_time_schedule_button = findViewById(R.id.store_time_schedule_button); //스케쥴 추가 버튼

            store_time_schedule_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v){
                    Intent intent_schedule = getIntent();
                    Bundle bundle = intent_schedule.getExtras();
                    time_scheduleArrayList = (ArrayList<Schedule>) bundle.getSerializable("time_scheduleArrayList");
                    String content = null;//////// 이거 나중에 레이아웃에서 추가해줘여ㅑ됨;;
                    // ModifySchedule(edit_name.getText().toString(), content, calendar, 1,  schedule_sound , schedule_vibration, group,position); 그룹 인텐트로좀 넘겨주세요 -> 이걸 왜 그룹으로 넘김 ?
                    //  setAlarm();

                    bundle.putSerializable("time_scheduleArrayList" , time_scheduleArrayList);
                    intent_schedule.putExtras( bundle );
                    setResult(0,intent_schedule);
                    finish();
                }
            });




        }

        else if(i ==2) { //시간별 스케쥴
            setContentView(R.layout.activity_location_schedule);
            EditText edit_name = findViewById(R.id.schedule_name_input);
            edit_name.setText(location_scheduleArrayList.get(position).Name);
            arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, group_list);
            group_spinner = findViewById(R.id.spinner_group);
            group_spinner.setAdapter(arrayAdapter);





            Button mapbutton = findViewById(R.id.Map_button); //맵 버튼
            mapbutton.setText("X좌표:"+location_x+" Y좌표: "+location_y);
            mapbutton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent map = new Intent(ModifySchedule.this, MapsActivity.class);
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

            Switch ring_switch = findViewById(R.id.bell_switch);//알람음
            if (schedule_sound==1)
                ring_switch.setChecked(true);
            else
                ring_switch.setChecked((false));

            Switch vibration_switch = findViewById(R.id.vibration_switch);//알람음
            if (schedule_vibration==1)
                vibration_switch.setChecked(true);
            else
                vibration_switch.setChecked((false));
            //진동

            Button exit_schedule = findViewById(R.id.exit_add_schedule_button); //스케쥴 추가에 취소 버튼
            exit_schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });


            Button store_location_schedule_button = findViewById(R.id.store_location_schedule_button); //스케쥴 추가에 취소 버튼
            store_location_schedule_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v){

                    Intent intent_schedule = getIntent();
                    Bundle bundle = intent_schedule.getExtras();
                    location_scheduleArrayList = (ArrayList<Schedule>) bundle.getSerializable("location_scheduleArrayList");
                    //            String content = null;//////// 이거 나중에 레이아웃에서 추가해줘여ㅑ됨;
                    // modified_schedule(edit_name.getText().toString(), location_x, location_y, 1,  schedule_sound , schedule_vibration, group,position); //그룹 인텐트로좀 넘겨주세요-> 이걸 왜 그룹으로 넘김 ?/// 위치정보 넘겨야됨
                    //        setAlarm();

                    bundle.putSerializable("location_scheduleArrayList" , location_scheduleArrayList);
                    intent_schedule.putExtras( bundle );
                    setResult(0,intent_schedule);
                    finish();
                }
            });

        }










    }
    /* 알람 등록 */
    private void setAlarm() {
        // 알람 시간 설정

        // 현재일보다 이전이면 등록 실패
        if (this.calendar.before(Calendar.getInstance())) {
            Toast.makeText(this, "알람시간이 현재시간보다 이전일 수 없습니다.", Toast.LENGTH_LONG).show();
            return;
        }

        // Receiver 설정
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 알람 설정
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, this.calendar.getTimeInMillis(), pendingIntent);

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