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

import java.util.ArrayList;

public class PopupActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Intent intent = getIntent();
        String opt = intent.getStringExtra("opt");
        if(opt.equals("add")){
            setContentView(R.layout.popup_add_activity);

            //버튼 눌러서 검색 기능 넣기

            Button button1 = findViewById(R.id.member_ok);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    //유저 인텐트에 추가
                    //intent.putExtra( 유저 자료);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
        }
        else if(opt.equals("delete")){

        }
        else {
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
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE)
            return false;
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}
