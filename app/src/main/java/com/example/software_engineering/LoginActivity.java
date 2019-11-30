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
        ID_Text = findViewById(R.id.ID_editText);
        PW_Text = findViewById(R.id.PW_editText);

        SignIn = findViewById(R.id.SignIn_button);
        SignUp = findViewById(R.id.SignUp_button);

        AutoLogin = findViewById(R.id.AutoLogin_checkBox);
        /**
         * if(AutoLogin.setOnClickListener()==true){}
         *
         */

        SignIn.setOnClickListener(new OnClickListener() {//Login
            @Override
            public void onClick(View v) {
                if(ID_Text.getText().toString().equals("") || PW_Text.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "Write ID and PW", Toast.LENGTH_LONG).show();
                }
                else {
                    LoginCheck = U.Network_DataArrangement("Login", ID_Text.getText().toString(), PW_Text.getText().toString());
                    System.out.println("Login Check : " + LoginCheck);
                    if (!LoginCheck) {
                        Toast.makeText(LoginActivity.this, "LoginFailed", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intentToMain = new Intent(LoginActivity.this, MainActivity.class);
                        intentToMain.putExtra("User", U);
                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                        startActivity(intentToMain);
                    }
                }
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

