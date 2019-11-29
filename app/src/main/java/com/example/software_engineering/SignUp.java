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
        EditText brand_phone = findViewById(R.id.phone_input);
        brand_phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        Button exit_schedule = findViewById(R.id.exit_button);
        exit_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }

}
