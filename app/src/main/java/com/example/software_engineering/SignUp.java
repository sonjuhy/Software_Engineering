package com.example.software_engineering;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class SignUp extends AppCompatActivity {
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
        IDCheck_Button = findViewById(R.id.id_check);
        Ok_Button = findViewById(R.id.button);
        Cancle_Button =findViewById(R.id.exit_button);

        Name_text = findViewById(R.id.editText2);
        ID_text = findViewById(R.id.id_input);
        PW_text = findViewById(R.id.editText5);
        PW_Check_text = findViewById(R.id.editText6);
        PhoneNum_text = findViewById(R.id.phone_input);

        System.out.println("SignUp Activity Working");
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
                    Toast.makeText(SignUp.this, "ID Check isn't Passed", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(SignUp.this, "ID Check is Passed", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(SignUp.this, "Name is Empty", Toast.LENGTH_SHORT).show();
                }
                else if(ID.matches("")){
                    Toast.makeText(SignUp.this, "ID is Empty", Toast.LENGTH_SHORT).show();
                }
                else if(PW.matches("")){
                    Toast.makeText(SignUp.this, "PW is Empty", Toast.LENGTH_SHORT).show();
                }
                else if(Phone_Num.matches("")){
                    Toast.makeText(SignUp.this, "Phone Num is Empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    SignUp_Network signUp_network = new SignUp_Network();
                    if(ID_Check) {
                        result = signUp_network.Network_DataArrangement("UpLoad", Name, ID, PW, Phone_Num);
                        if(result) {
                            Toast.makeText(SignUp.this, "Sign Up is Success", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(SignUp.this, "Sign Up is Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(SignUp.this, "ID Check isn't Passed", Toast.LENGTH_LONG).show();
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

class SignUp_Network implements Serializable {
    private String Network_data;
    private Network n;

    private boolean result;

    private void Network_Access(String Action, String Data) {
        n = new Network();//for Using Network without AsyncTask error
        n.Input_data(Action, Data);
        try {
            Network_data = n.execute().get(); //execute Network and take return value to Network_data
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(true){
            if(n.finish == true){  //when Network doInBackground is End
                System.out.println("Asyn finish");
                break;
            }
        }
    }
    public boolean Network_DataArrangement(String... _param){ //Setting for Network Class Value before Working Network Class
        //_param mean String[] _param
        if(_param != null){
            switch(_param[0]){//Frist Parameter(String)
                case "UpLoad"://Login part
                    try {//Make and Fit a style data to send Network Class & Server
                        Network_data = URLEncoder.encode("Name","UTF-8") + "=" + URLEncoder.encode(_param[1],"UTF-8");
                        Network_data += URLEncoder.encode("ID","UTF-8") + "=" + URLEncoder.encode(_param[2],"UTF-8");
                        Network_data += URLEncoder.encode("PW","UTF-8") + "=" + URLEncoder.encode(_param[3],"UTF-8");
                        Network_data += URLEncoder.encode("Phone_Num","UTF-8") + "=" + URLEncoder.encode(_param[4],"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Network_Access("Get_UserData",Network_data);//Sending Data & kind of command to Network Class Running Network
                    if(Network_data.equals(false)){ //Login Failed
                        System.out.println("UpLoad Failed");
                        return false;
                    }
                    else {//Login Success
                        System.out.println("UpLoad Success");
                    }
                    break;
                case "IDCheck":
                    try {
                        Network_data = URLEncoder.encode("Name","UTF-8") + "=" + URLEncoder.encode(_param[1],"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Network_Access("SignUp_IDCheck",Network_data);
                    System.out.println("SignUP ID check : " + Network_data);
                    if(Network_data.equals("Success")){
                        result = true;
                    }
                    else{
                        result = false;
                    }
                    break;
                default:
                    result = false;
                    break;
            }
        }
        if(result == true){
            return true;//Working is Success
        }
        else{
            return false;//Working is Success
        }
    }
}
