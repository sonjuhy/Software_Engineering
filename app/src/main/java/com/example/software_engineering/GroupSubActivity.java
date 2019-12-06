package com.example.software_engineering;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import yuku.ambilwarna.AmbilWarnaDialog;//// 살려야됨

public class GroupSubActivity extends AppCompatActivity {

    int colorPick;
    TextView colorSample;
    ArrayList<GroupMember> list = new ArrayList<>();
    final Intent intent = new Intent();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_CANCELED) {

        } else {
            switch (requestCode) {
                case 1:
                    GroupMember member = (GroupMember) data.getSerializableExtra("member");
                    list.add(member);
                    intent.putExtra("list",list);
                    break;
                case 2:
                    list = (ArrayList<GroupMember>) data.getSerializableExtra("list");
                    intent.putExtra("list",list);
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_sub);

        if(getIntent().getStringExtra("opt").equals("edit")){
            Intent data = getIntent();
            Group group = (Group) data.getSerializableExtra("item");

            TextView textView = findViewById(R.id.gName);
            TextView textView1 = findViewById(R.id.color);
            EditText editText = findViewById(R.id.gName_input);
            Button button = findViewById(R.id.color_pick);

            TableRow.LayoutParams params = (TableRow.LayoutParams)textView.getLayoutParams();
            params.gravity = Gravity.START;
            params.span = 2;
            textView.setLayoutParams(params);

            textView.setText("그룹명      "+group.GroupName_output());
            editText.setVisibility(View.GONE);

            list = group.GroupMember_output();
            textView1.setBackgroundColor(group.GroupColor_output());

            button.setText("그룹 나가기");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //나가기
                    intent.putExtra("exit",true);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });

            Button button2 = findViewById(R.id.member_edit);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                    intent.putExtra("list", list);
                    intent.putExtra("opt", "view");
                    startActivityForResult(intent, 2);
                }
            });

            intent.putExtra("position",data.getIntExtra("position",0));

        }
        else {
            User U = (User) getIntent().getSerializableExtra("user");
            list.add(new GroupMember(U.UserID_Output(),U.UserName_Output(),U.UserPhone_num_Output()));

            Button button = findViewById(R.id.color_pick);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openColorPicker();
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

        }

        Button button1 = findViewById(R.id.add_member);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtra("opt", "add_member");
                startActivityForResult(intent, 1);
            }
        });

        Button button3 = findViewById(R.id.add_group);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = findViewById(R.id.gName_input);
                if(name.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"그룹명을 입력해주세요.",Toast.LENGTH_LONG).show();
                }else{
                    intent.putExtra("name",name.getText().toString());
                    setResult(RESULT_OK,intent);
                    finish();
                }
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
                intent.putExtra("color",color);
            }
        });
        colorPicker.show();
    }
}

