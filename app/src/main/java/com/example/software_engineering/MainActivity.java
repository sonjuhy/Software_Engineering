package com.example.software_engineering;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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




    private void LoginGetData_Schedule(){
        Schedule_Network schedule_network = new Schedule_Network();
        schedule_network.Network_DataArrangement(U,"DownLoad");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        U = (User) getIntent().getSerializableExtra("User");

        LoginGetData_Schedule();//DownLoad Schedule Data from Server;
        //LoginGetData_Group();//DownLoad Group Data from Server;
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

        Schedule time1 = new Schedule("첫번쨰", null,null,0,0,0,null);
        time_scheduleArrayList.add(time1);
        Schedule time2 = new Schedule("두번쨰", null,null,0,0,0,null);
        time_scheduleArrayList.add(time2);
        Schedule time3 = new Schedule("세번쨰", null,null,0,0,0,null);
        time_scheduleArrayList.add(time3);

//

        list_time_schedule = (ListView)findViewById(R.id.list_time_schedule);
        CustomTimeAdapter time_adapter = new CustomTimeAdapter((time_scheduleArrayList));
        list_time_schedule.setAdapter(time_adapter);







        location_scheduleArrayList = new ArrayList<>(); //장소별 리스트

// 사이 필요없는값 강제 넣기

        Schedule location1 = new Schedule("첫번쨰", null,0,0,0,0,0,null);
        location_scheduleArrayList.add(location1);
        Schedule location2 = new Schedule("두번쨰", null,0,0,0,0,0,null);
        location_scheduleArrayList.add(location2);
        Schedule location3 = new Schedule("세번쨰", null,0,0,0,0,0,null);
        location_scheduleArrayList.add(location3);

//

        list_location_schedule = (ListView)findViewById(R.id.list_location_schedule);
        CustomLocationAdapter location_adapter = new CustomLocationAdapter((location_scheduleArrayList));
        list_location_schedule.setAdapter(location_adapter);

    }







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
                intent.putExtra("group",groupArrayList);
                startActivity(intent);

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
        } else if (id == R.id.nav_Group) {
            Intent intentToGroup = new Intent(MainActivity.this, GroupMainActivity.class);
            intentToGroup.putExtra("Group",G);
            startActivityForResult(intentToGroup,1);


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK){
            groupArrayList = (ArrayList<String>) data.getSerializableExtra("list");
        }
    }
}
