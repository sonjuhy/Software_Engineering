package com.example.software_engineering;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserActivity extends AppCompatActivity{
    private User User;
    private String Name;
    private String ID;
    private String PW;
    private String Phone_Num;
    private boolean result;
    private boolean ID_Check;

    private Button Ok_Button;
    private Button Cancle_Button;
    private Button IDCheck_Button;
    private EditText Name_text;
    private EditText ID_text;
    private EditText PW_text;
    private EditText PW_Check_text;
    private EditText PhoneNum_text;

    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

        User = (User) getIntent().getSerializableExtra("User");


        setContentView(R.layout.activity_userdata_revise);
    //텍스트 입력란에 기존 유저정보를 불러와 미리 넣어놓음
        EditText idEt = findViewById(R.id.id_Revise);
            idEt.setText(this.User.UserID_Output());
        EditText nameEt = findViewById(R.id.name_Revise);
            nameEt.setText(this.User.UserName_Output());
        EditText phoneNumEt = findViewById(R.id.phone_Revise);
            phoneNumEt.setText(this.User.UserPhone_num_Output());
        EditText pwCheckEt = findViewById(R.id.pw_Check);
            pwCheckEt.setText(this.User.UserPW_Output());
        EditText pwReviseEt = findViewById(R.id.phone_Revise);
            pwReviseEt.setText(this.User.UserPW_Output());

            //취소 버튼 눌렸을때
    Button exit_revise = findViewById(R.id.exit_button1);
        exit_revise.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v){
            finish();
        }
    });
    IDCheck_Button = findViewById(R.id.id_check1);
    Ok_Button = findViewById(R.id.revise_button);
    Cancle_Button =findViewById(R.id.exit_button1);

    Name_text = findViewById(R.id.name_Revise);
    ID_text = findViewById(R.id.id_Revise);
    PW_text = findViewById(R.id.pw_Revise);
    PW_Check_text = findViewById(R.id.pw_Check);
    PhoneNum_text = findViewById(R.id.phone_Revise);

        System.out.println("Revise Activity Working");

        IDCheck_Button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SignUp_Network signUp_network = new SignUp_Network();
            System.out.println("ID : "+ID_text.getText().toString());
            ID_Check = signUp_network.Network_DataArrangement("IDCheck",ID_text.getText().toString());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!ID_Check){
                Toast.makeText(UserActivity.this, "ID Check isn't Passed", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(UserActivity.this, "ID Check is Passed", Toast.LENGTH_LONG).show();
            }
        }
    });
        Ok_Button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Name = Name_text.getText().toString();
            ID = ID_text.getText().toString();
            PW = PW_text.getText().toString();
            Phone_Num = PhoneNum_text.getText().toString();

            if(Name.matches("")){
                Toast.makeText(UserActivity.this, "Name is Empty", Toast.LENGTH_SHORT).show();
            }
            else if(ID.matches("")){
                Toast.makeText(UserActivity.this, "ID is Empty", Toast.LENGTH_SHORT).show();
            }
            // 수정하기라서 PW빈거 검사 할필요가 있을까?
            else if(PW.matches("")){
                Toast.makeText(UserActivity.this, "PW is Empty", Toast.LENGTH_SHORT).show();
            }
            else if(Phone_Num.matches("")){
                Toast.makeText(UserActivity.this, "Phone Num is Empty", Toast.LENGTH_SHORT).show();
            }
            else{
                SignUp_Network signUp_network = new SignUp_Network();
                if(ID_Check) {
                    result = signUp_network.Network_DataArrangement("IDCheck", ID);
                    if(result) {
                        Name = Name_text.getText().toString();
                        ID = ID_text.getText().toString();
                        PW = PW_text.getText().toString();
                        Phone_Num = PhoneNum_text.getText().toString();
                        System.out.println("UserActivity name : " + Name);
                        System.out.println("UserActivity ID : " + ID);
                        System.out.println("UserActivity PW : " + PW);
                        System.out.println("UserActivity PN : " + Phone_Num);
                        User.Network_DataArrangement("Update_Data",Name, ID, PW, Phone_Num);
                        Toast.makeText(UserActivity.this, "Revise is Success", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(UserActivity.this, "Revise is Failed", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(UserActivity.this, "ID Check isn't Passed", Toast.LENGTH_LONG).show();
                }

            }
        }
    });
        Cancle_Button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });
}
}

