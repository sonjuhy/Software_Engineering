package com.example.software_engineering;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity {

    // UI references.
    private EditText ID_Text;
    private EditText PW_Text;
    private Button SignIn;
    private Button SignUp;
    private CheckBox AutoLogin;

    private boolean LoginCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        final User U = new User();
        ID_Text = (EditText)findViewById(R.id.ID_editText);
        PW_Text = (EditText)findViewById(R.id.PW_editText);

        SignIn = (Button)findViewById(R.id.SignIn_button);
        SignUp = (Button)findViewById(R.id.SignUp_button);

        AutoLogin = (CheckBox)findViewById(R.id.AutoLogin_checkBox);
        /**
         * if(AutoLogin.setOnClickListener()==true){}
         *
         */

        U.UserInfo_Input("Name","ID","PW","010123456789");
        SignIn.setOnClickListener(new OnClickListener() {//Login
            @Override
            public void onClick(View v) {
               /* LoginCheck =  U.Network_DataArrangement("Login",ID_Text.getText().toString(),PW_Text.getText().toString());
                if(!LoginCheck){
                    Toast.makeText(LoginActivity.this, "LoginFailed", Toast.LENGTH_LONG);
                }
                else {
                    Intent intentToMain = new Intent(LoginActivity.this, MainActivity.class);
                    intentToMain.putExtra("User", U);
                    Toast.makeText(LoginActivity.this,"Login Success", Toast.LENGTH_LONG);
                    startActivity(intentToMain);
                }*/
                Intent intentToMain = new Intent(LoginActivity.this, MainActivity.class);
                intentToMain.putExtra("User", U);
                Toast.makeText(LoginActivity.this,"Login Success", Toast.LENGTH_LONG);
                startActivity(intentToMain);
            }
        });

        SignUp.setOnClickListener(new OnClickListener() {//Make new Account
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
    }
}

