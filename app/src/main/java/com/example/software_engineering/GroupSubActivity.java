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
    //실제 구현에서는 new 하면 안되고 그룹 불러와야됨
    ArrayList<GroupMember> list = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_CANCELED) {

        } else {
            switch (requestCode) {
                case 1:
                    GroupMember member = (GroupMember) data.getSerializableExtra("member");
                    list.add(member);
                    break;
                case 2:
                    list = (ArrayList<GroupMember>) data.getSerializableExtra("list");
                    break;
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
                intent.putExtra("opt", "add_member");
                startActivityForResult(intent, 1);
            }
        });

        Button button2 = findViewById(R.id.member_edit);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtra("list", list);
                intent.putExtra("opt", "delete");
                startActivityForResult(intent, 2);
            }
        });

        Button button3 = findViewById(R.id.add_group);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });

        Button exit_group = findViewById(R.id.exit_group); //그룹 추가에 취소 버튼
        exit_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

