package com.example.software_engineering;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
       EditText brand_phone = (EditText) findViewById(R.id.phone_input);
       brand_phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }
}
