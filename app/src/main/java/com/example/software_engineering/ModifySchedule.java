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
    User user;
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

    public void set_schedule(String name, String content, Calendar calendar, int alarmRepeatCount, int sound, int vibration, Group group,int position) {
        time_scheduleArrayList.set(position , new Schedule(name, content, calendar, alarmRepeatCount, sound, vibration,group));
    }
    public void set_schedule(String name, String content, double place_x, double place_y, int alarmRepeatCount, int sound, int vibration,Group group,int position) {
        location_scheduleArrayList.set(position ,new Schedule(name, content, "", "", "", place_x, place_y, alarmRepeatCount, sound, vibration));
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       Intent intent = getIntent();
        position  = intent.getIntExtra("list_position", 1);
        group_list= (ArrayList<String>)intent.getSerializableExtra("group");
        user = (User)intent.getSerializableExtra("user");
        int i  = intent.getIntExtra("num", 1);

        if(i==1) {
            y=user.UserTimeSchedule_Output().get(position).calendar.get(Calendar.YEAR);
            m=user.UserTimeSchedule_Output().get(position).calendar.get(Calendar.MONTH);
            d=user.UserTimeSchedule_Output().get(position).calendar.get(Calendar.DAY_OF_MONTH);
            h=user.UserTimeSchedule_Output().get(position).calendar.get(Calendar.HOUR_OF_DAY);
            mi=user.UserTimeSchedule_Output().get(position).calendar.get(Calendar.MINUTE);
            schedule_sound =user.UserTimeSchedule_Output().get(position).Sound;
            schedule_vibration =user.UserTimeSchedule_Output().get(position).Vibration;
        }

        else if(i==2) {
            location_x = user.UserPlaceSchedule_Output().get(position).Place_x;
            location_y = user.UserPlaceSchedule_Output().get(position).Place_y;
            schedule_sound =user.UserPlaceSchedule_Output().get(position).Sound;
            schedule_vibration =user.UserPlaceSchedule_Output().get(position).Vibration;
        }





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
            edit_name.setText(user.UserTimeSchedule_Output().get(position).Name);

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
                    EditText editText = findViewById(R.id.schedule_name_input);
                    user = (User) getIntent().getSerializableExtra("user");
                    time_scheduleArrayList = user.UserTimeSchedule_Output();
                    String content = null;//////// 이거 나중에 레이아웃에서 추가해줘여ㅑ됨;;
                    calendar.set(y,m,d,h,m);////// 날짜 저장 달이 1작다는데 나중에 실험 해보기
                    //마지막에 new Group()은 임시
                    set_schedule(editText.getText().toString(), content, calendar, 1,  schedule_sound , schedule_vibration, new Group(),position);
                    setAlarm();
                    intent_schedule.putExtra("time",time_scheduleArrayList);
                    setResult(0,intent_schedule);
                    finish();
                }
            });




        }

        else if(i ==2) { //시간별 스케쥴
            setContentView(R.layout.activity_location_schedule);
            EditText edit_name = findViewById(R.id.schedule_name_input);
            edit_name.setText(user.UserPlaceSchedule_Output().get(position).Name);
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
                    EditText editText = findViewById(R.id.schedule_name_input);
                    user = (User) getIntent().getSerializableExtra("user");
                    location_scheduleArrayList = user.UserTimeSchedule_Output();
                    String content = null;//////// 이거 나중에 레이아웃에서 추가해줘여ㅑ됨;;

                    set_schedule(editText.getText().toString(), content, location_x, location_y, 1,  schedule_sound , schedule_vibration, new Group(),position);
                    intent_schedule.putExtra("location",location_scheduleArrayList);
                    setResult(1,intent_schedule);
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
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                date_button.setText(y+"년 "+m+"월 "+d+"일");
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

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
                calendar.set(Calendar.HOUR_OF_DAY, h);
                calendar.set(Calendar.MINUTE, mi);
                time_button.setText(h+"시 "+mi+"분");
                final Calendar calendar = Calendar.getInstance();
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

        timePickerDialog.setMessage("메시지");
        timePickerDialog.show();
    }
}