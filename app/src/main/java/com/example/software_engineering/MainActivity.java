package com.example.software_engineering;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private User U;
    private Schedule S;
    private Group G;
    private ListView list_time_schedule;
    private ListView list_location_schedule;
    private ArrayList<Schedule> time_scheduleArrayList;
    private ArrayList<Schedule> location_scheduleArrayList;
    private ArrayList<String> groupArrayList = new ArrayList<>();
    private int choose_num=0;
    private int list_position=0;

    private static final int REQUEST_CODE =777;


    private void LoginGetData_Schedule(){
        Schedule_Network schedule_network = new Schedule_Network();
        schedule_network.Network_DataArrangement(U,"DownLoad");
    }
    private void LoginGetData_Group(){
        Group_Network group_network = new Group_Network();
        group_network.Network_DataArrangement(U,0,"DownLoad");//Group Name Load
        group_network.Network_DataArrangement(U,1,"DownLoad");//Group Member Load
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        U = (User) getIntent().getSerializableExtra("User");
        time_scheduleArrayList = U.UserTimeSchedule_Output();
        location_scheduleArrayList = U.UserPlaceSchedule_Output();



        LoginGetData_Schedule();//DownLoad Schedule Data from Server;
        LoginGetData_Group();//DownLoad Group Data from Server;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        time_scheduleArrayList = new ArrayList<>(); //시간별 리스트

// 사이 필요없는값 강제 넣기

//

        list_time_schedule = (ListView)findViewById(R.id.list_time_schedule);
        CustomTimeAdapter time_adapter = new CustomTimeAdapter((time_scheduleArrayList));
        list_time_schedule.setAdapter(time_adapter);

        registerForContextMenu(list_time_schedule);              //<<<<<<<<<<<<<<<<<<








        location_scheduleArrayList = new ArrayList<>(); //장소별 리스트

// 사이 필요없는값 강제 넣기



//



        list_location_schedule = (ListView)findViewById(R.id.list_location_schedule);
        CustomLocationAdapter location_adapter = new CustomLocationAdapter((location_scheduleArrayList));
        list_location_schedule.setAdapter(location_adapter);
        registerForContextMenu(list_location_schedule);

        list_time_schedule.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                list_position=position;
                choose_num = 1;
                return false;
            }
        });

        list_location_schedule.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                list_position=position;
                choose_num = 2;
                return false;
            }
        });

        list_time_schedule.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });

        list_location_schedule.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });

    }


    @Override

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.list_click_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);

    }

    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int index= info.position; //AdapterView안에서 ContextMenu를 보여즈는 항목의 위치

        //선택된 ContextMenu의  아이템아이디를 구별하여 원하는 작업 수행
        //예제에서는 선택된 ListView의 항목(String 문자열) data와 해당 메뉴이름을 출력함

        switch( item.getItemId() ){

            case R.id.modify:

                if(choose_num==1) {
                    Intent intent = new Intent(MainActivity.this,ModifySchedule.class);
                    intent.putExtra("num",choose_num);
                    intent.putExtra("group",groupArrayList);
                    intent.putExtra("list_position",list_position);

                    intent.putExtra("user",U);

                    startActivityForResult(intent,REQUEST_CODE);

                    Toast.makeText(this, choose_num+" Modify 선택 리스트: "+list_position, Toast.LENGTH_SHORT).show();
                    break;
                }

                else if(choose_num==2){

                    Intent intent = new Intent(MainActivity.this,ModifySchedule.class);
                    intent.putExtra("num",choose_num);
                    intent.putExtra("group",groupArrayList);
                    startActivity(intent);
                    Toast.makeText(this, choose_num+" Modify 선택 리스트:"+list_position, Toast.LENGTH_SHORT).show();
                    break;
                }


            case R.id.delete:
                if(choose_num==1) {
                    time_scheduleArrayList.remove(list_position);
                    CustomTimeAdapter time_adapter = new CustomTimeAdapter((time_scheduleArrayList));
                    list_time_schedule.setAdapter(time_adapter);
                    Toast.makeText(this, "선택된 일정이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    break;
                }

                else if(choose_num==2) {
                    location_scheduleArrayList.remove(list_position);
                    CustomLocationAdapter location_adapter = new CustomLocationAdapter((location_scheduleArrayList));
                    list_location_schedule.setAdapter(location_adapter);
                    Toast.makeText(this, "선택된 일정이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    break;
                }

        }
        return true;

    };





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override //메인화면 오른쪽위 점3개 버튼
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                return true;

            case R.id.add_schedule:
                Intent intent = new Intent(getApplicationContext(),PopupActivity.class);
                intent.putExtra("opt","add_schedule");
                Intent group = intent.putExtra("group", groupArrayList);
                intent.putExtra("user",U);
                startActivityForResult(intent,REQUEST_CODE);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (id == R.id.nav_Schedule) {
            drawer.closeDrawer(Gravity.LEFT);

            Intent intent = new Intent(MainActivity.this,ScheduleMainActivity.class);


        } else if (id == R.id.nav_Group) {
            Intent intentToGroup = new Intent(MainActivity.this, GroupMainActivity.class);
            intentToGroup.putExtra("user",U);
            startActivityForResult(intentToGroup,REQUEST_CODE);


        } else if (id == R.id.nav_User_Information) {
            Intent intentToMain = new Intent(MainActivity.this, UserActivity.class);
            intentToMain.putExtra("User", U);
            startActivity(intentToMain);

           /* Intent intentToUserDataRevise = new Intent(MainActivity.this, UserActivity.class);
            intentToUserDataRevise.putExtra("ID",U.UserID_Output());
            intentToUserDataRevise.putExtra("Name",U.UserName_Output());
            intentToUserDataRevise.putExtra("PW",U.UserPW_Output());
            intentToUserDataRevise.putExtra("Phone_Num",U.UserPhone_num_Output());
            startActivityForResult(intentToUserDataRevise,1);*/
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE)
        {
            if(resultCode==RESULT_OK){
                groupArrayList = (ArrayList<String>) data.getSerializableExtra("list");
            }

            else if(resultCode == 0)
            {
                U.TimeSchedule_set((ArrayList<Schedule>) data.getExtras().getSerializable("time"));
                CustomTimeAdapter time_adapter = new CustomTimeAdapter((U.UserTimeSchedule_Output()));
                list_time_schedule.setAdapter(time_adapter);
            }

            else if(resultCode == 1)
            {
               U.PlaceSchedule_set((ArrayList<Schedule>)data.getExtras().getSerializable("location"));
                CustomLocationAdapter location_adapter = new CustomLocationAdapter((U.UserPlaceSchedule_Output()));
                list_location_schedule.setAdapter(location_adapter);
            }

            else
            {
                /////실패
            }
        }

    }
}
