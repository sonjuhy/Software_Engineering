package com.example.software_engineering;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PopupActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Intent intent = getIntent();
        String opt = intent.getStringExtra("opt");
        if(opt.equals("add_member")){
            setContentView(R.layout.popup_add_activity);

            final GroupMember member = new GroupMember("id","name",0);

            final EditText editText = findViewById(R.id.search);
            final TextView textView = findViewById(R.id.search_text);

            Button button = findViewById(R.id.member_search);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //검색 기능 구현 후 해당하는 값 밑에 대입
                    member.id="아이디";
                    member.name = "이름";
                    member.PhoneNumber = 1;
                    textView.setText("ID: "+member.id+", 이름: "+member.name);
                }
            });

            Button button2 = findViewById(R.id.member_ok);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!member.id.equals("id")){
                        Intent intent = new Intent(getApplicationContext(),GroupSubActivity.class);
                        intent.putExtra("member",member);
                        setResult(RESULT_OK,intent);
                        finish();
                    } else{
                        Toast.makeText(PopupActivity.this,"검색한 사용자가 없습니다.",Toast.LENGTH_LONG).show();
                    }
                }
            });

            Button button3 = findViewById(R.id.member_cancel);
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    setResult(RESULT_CANCELED,intent);
                    finish();
                }
            });
        }
        else if(opt.equals("delete")){

        }
        else if(opt.equals("view")) {
            setContentView(R.layout.popup_view_activity);

            ArrayList<GroupMember> list = (ArrayList<GroupMember>) intent.getExtras().get("list");
            CustomMemberAdapter adapter;

            RecyclerView recyclerView = findViewById(R.id.member_view);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(manager);

            adapter=new CustomMemberAdapter(list);
            recyclerView.setAdapter(adapter);

            Button button1 = findViewById(R.id.view_ok);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        else if(opt.equals("add_schedule")){
            setContentView(R.layout.popup_add_schedule);
            final Intent schedule = new Intent(PopupActivity.this, ScheduleMainActivity.class);

            Button time_button = findViewById(R.id.set_time_schedule);
            time_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    schedule.putExtra("num",1);
                    startActivity(schedule);
                }
            });

            Button location_button = findViewById(R.id.set_location_schedule);
            location_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    schedule.putExtra("num",2);
                    startActivity(schedule);
                }
            });
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE)
            return false;
        return true;
    }
}
