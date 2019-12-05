package com.example.software_engineering;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PopupActivity extends Activity {

    private static final int REQUEST_CODE_POPUP =888;
    private boolean result;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Intent intent = getIntent();
        String opt = intent.getStringExtra("opt");
        if(opt.equals("add_member")){
            setContentView(R.layout.popup_add_activity);

            final GroupMember member = new GroupMember("id","name",0);

            final EditText editText = findViewById(R.id.search);
            final TextView textView = findViewById(R.id.search_text);

            Button button = findViewById(R.id.member_search);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //검색 기능 구현 후 해당하는 값 밑에 대입
                    PopUp_Network popUp_network = new PopUp_Network();
                    result = popUp_network.Network_DataArrangement("Search",editText.getText().toString());
                    if(result){
                        User User_search = new User();
                        User_search.UserInfo_Input("",editText.getText().toString(),"","");
                        User_search.Network_DataArrangement("Get_Data","");
                        member.id=User_search.UserID_Output();
                        member.name = User_search.UserName_Output();
                        textView.setText("ID: "+member.id+", 이름: "+member.name);
                    }
                    else{
                        textView.setText("찾을수 없습니다.");
                    }
                }
            });

            Button button2 = findViewById(R.id.member_ok);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!member.id.equals("id")){
                        Intent intent = new Intent(getApplicationContext(),GroupSubActivity.class);
                        intent.putExtra("member",member);
                        setResult(RESULT_OK,intent);
                        finish();
                    } else{
                        Toast.makeText(PopupActivity.this,"검색한 사용자가 없습니다.",Toast.LENGTH_LONG).show();
                    }
                }
            });

            Button button3 = findViewById(R.id.member_cancel);
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    setResult(RESULT_CANCELED,intent);
                    finish();
                }
            });
        }
        else if(opt.equals("delete")){
            setContentView(R.layout.popup_view_activity);

            final ArrayList<GroupMember> list = (ArrayList<GroupMember>) intent.getExtras().get("list");
            CustomMemberAdapter adapter;

            RecyclerView recyclerView = findViewById(R.id.member_view);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(manager);

            adapter=new CustomMemberAdapter(list);
            adapter.setOnItemClickListener(new CustomMemberAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int pos) {
                    list.remove(pos);
                    Intent intent = new Intent();
                    intent.putExtra("list",list);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
            recyclerView.setAdapter(adapter);

            Button button1 = findViewById(R.id.view_ok);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    setResult(RESULT_CANCELED,intent);
                    finish();
                }
            });

        }
        else if(opt.equals("view")) {
            setContentView(R.layout.popup_view_activity);

            ArrayList<GroupMember> list = (ArrayList<GroupMember>) intent.getExtras().get("list");
            CustomMemberAdapter adapter;

            RecyclerView recyclerView = findViewById(R.id.member_view);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(manager);

            adapter=new CustomMemberAdapter(list);
            recyclerView.setAdapter(adapter);

            Button button1 = findViewById(R.id.view_ok);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        else if(opt.equals("add_schedule")){
            setContentView(R.layout.popup_add_schedule);
            final Intent schedule = new Intent(PopupActivity.this, ScheduleMainActivity.class);


            schedule.putExtra("group",getIntent().getSerializableExtra("group"));
            schedule.putExtra("user",getIntent().getSerializableExtra("user"));
            Button time_button = findViewById(R.id.set_time_schedule);
            time_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    schedule.putExtra("num",1);
                    startActivityForResult(schedule,REQUEST_CODE_POPUP);

                }
            });


            Button location_button = findViewById(R.id.set_location_schedule);
            location_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    schedule.putExtra("num",2);
                    startActivity(schedule);

                }
            });

            setResult(0,schedule);

        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE)
            return false;
        return true;
    }
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_POPUP)
        {
            if(resultCode==0){
                System.out.println("~~~~~~~~~~~~popup on activity~~~~~~~~~~~~~~~~~~~~~~~~~");
                   setResult(0,data);
                finish();
            }
        }

    }







}
class PopUp_Network{
    private Network n;
    private String Network_data;
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
                case "Search"://Login part
                    try {//Make and Fit a style data to send Network Class & Server
                        Network_data = URLEncoder.encode("ID","UTF-8") + "=" + URLEncoder.encode(_param[1],"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Network_Access("SignUp_IDCheck", Network_data);//Sending Data, kind of command to Network Class &Running Network
                    if(Network_data.equals(false)){ //Login Failed
                        System.out.println("Search Failed");
                        return false;
                    }
                    else {//Login Success
                        System.out.println("Search Success");
                    }
                    break;
            }
        }
        return true;//Working is Success
    }
}
