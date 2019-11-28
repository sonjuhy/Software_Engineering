package com.example.software_engineering;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import yuku.ambilwarna.AmbilWarnaDialog;//// 살려야됨

public class GroupSubActivity extends AppCompatActivity {

    int colorPick;
    TextView colorSample;
    ArrayList<GroupMember> list;

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
                openColorPicker();
            }
        });

        Button button1 = findViewById(R.id.add_member);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtra("opt","add");
                startActivityForResult(intent,1);
            }
        });

        Button button2 = findViewById(R.id.member_edit);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //임시로 데이터 입력
                list = new ArrayList<>();
                list.add(new GroupMember("A","1",0));
                list.add(new GroupMember("B","2",1111));
                list.add(new GroupMember("C","3",2222));
                list.add(new GroupMember("D","4",3333));

                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtra("list",list);
                intent.putExtra("opt","view");
                startActivity(intent);
            }
        });
    }

   private void openColorPicker() {
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
    }

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

