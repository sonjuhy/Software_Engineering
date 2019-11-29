package com.example.software_engineering;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

public class SignUpActivity extends AppCompatActivity implements Serializable {

    private String Name;
    private String ID;
    private String PW;
    private String Phone_Num;

    private Button Ok_Button;
    private Button Cancle_Button;
    private EditText Name_text;
    private EditText ID_text;
    private EditText PW_text;
    private EditText PhoneNum_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        /*Ok_Button = findViewById(R.id.OK_button);
        Cancel_Button = findViewById(R.id.Cancel_button);
        Name_text = findViewById(R.id.Name_editText);
        ID_text = findViewById(R.id.ID_editText);
        PW_text = findViewById(R.id.PW_editText);
        PhoneNum_text = findViewById(R.id.PhoneNum_editText);*/

        Ok_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = Name_text.getText().toString();
                ID = ID_text.getText().toString();
                PW = PW_text.getText().toString();
                Phone_Num = PhoneNum_text.getText().toString();

                if(Name.matches("")){
                    Toast.makeText(SignUpActivity.this, "Name is Empty", Toast.LENGTH_SHORT);
                }
                else if(ID.matches("")){
                    Toast.makeText(SignUpActivity.this, "ID is Empty", Toast.LENGTH_SHORT);
                }
                else if(PW.matches("")){
                    Toast.makeText(SignUpActivity.this, "PW is Empty", Toast.LENGTH_SHORT);
                }
                else if(Phone_Num.matches("")){
                    Toast.makeText(SignUpActivity.this, "Phone Num is Empty", Toast.LENGTH_SHORT);
                }
                else{
                    Toast.makeText(SignUpActivity.this, "Sign Up is Success", Toast.LENGTH_SHORT);
                }
            }
        });
    }
}
