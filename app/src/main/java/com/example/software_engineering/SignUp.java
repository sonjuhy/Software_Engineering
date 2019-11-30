package com.example.software_engineering;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText et = findViewById(R.id.phone_input);
        et.setInputType(android.text.InputType.TYPE_CLASS_PHONE); // 먼저 EditText에 번호만 입력되도록 바꾼 뒤
        et.addTextChangedListener(new PhoneNumberFormattingTextWatcher()); // 이렇게 리스너를 걸어주면

        Button exit_schedule = findViewById(R.id.exit_button);
        exit_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }

}
