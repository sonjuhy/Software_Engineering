package com.example.software_engineering;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

//import yuku.ambilwarna.AmbilWarnaDialog;//// 살려야됨

public class GroupSubActivity extends AppCompatActivity {

    int colorPick;
    TextView colorSample;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                //유저 목록에 추가한다는 내용
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_sub);

        Button button = findViewById(R.id.color_pick);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openColorPicker();//// 살려야됨
            }
        });

        Button button1 = findViewById(R.id.add_member);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                startActivity(intent);
                //startActivityForResult(intent,1);
            }
        });
    }

   /*private void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, colorPick, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                colorSample = findViewById(R.id.color);
                colorPick = color;
                colorSample.setBackgroundColor(color);
            }
        });
        colorPicker.show();
    }*//// 살려야됨

    public void CreateNewGroup(String GroupName, int PeopleNumber) {
        Group group = new Group(GroupName, PeopleNumber);
        User user = new User();
        user.Group_Input(group);
    }

    public void ReviseGroup(String GroupName, int PeopleNumber) {
        User user = new User();
        ArrayList GroupLIst = user.UserGroup_Output();
        for (int i = 0; i < GroupLIst.size(); i++) {
            if (GroupLIst.contains(GroupName)) {
                int index = GroupLIst.indexOf(GroupLIst);
                GroupLIst.set(index, new Group(GroupName, PeopleNumber));
            }
        }
    }

    public void DeleteGroup(String GroupName, int PeopleNumber) {
        User user = new User();
        ArrayList GroupLIst = user.UserGroup_Output();
        for (int i = 0; i < GroupLIst.size(); i++) {
            if (GroupLIst.contains(GroupName)) {
                int index = GroupLIst.indexOf(GroupLIst);
                GroupLIst.remove(index);
            }
        }
    }















}

