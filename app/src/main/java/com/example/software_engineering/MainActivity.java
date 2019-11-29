package com.example.software_engineering;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.solver.GoalRow;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private ListView list_gps_schedule;

    private Schedule LoginGetData_Schedule(){
        S = new Schedule();
        S.Network_DataArrangement("DownLoad");
        return S;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        U = (User) getIntent().getSerializableExtra("User");

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //시간별 스케쥴 리스트 생성
        list_time_schedule = (ListView)findViewById(R.id.list_time_schedule);
        List<String> time_schedule_data = new ArrayList<>();
        ArrayAdapter<String> time_schedule_adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, time_schedule_data);
        list_time_schedule.setAdapter(time_schedule_adapter);
        time_schedule_data.add("시간 스케쥴1");
        time_schedule_data.add("시간 스케쥴2");
        time_schedule_adapter.notifyDataSetChanged();

        //장소별 스케쥴 리스트 생성
        list_gps_schedule = (ListView)findViewById(R.id.list_gps_schedule);
        List<String> gps_schedule_data = new ArrayList<>();
        ArrayAdapter<String> gps_schedule_adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, gps_schedule_data);
        list_gps_schedule.setAdapter(gps_schedule_adapter);
        gps_schedule_data.add("타임 스케쥴1");
        gps_schedule_data.add("타임 스케쥴2");
        gps_schedule_adapter.notifyDataSetChanged();


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (id == R.id.nav_Schedule) {
            drawer.closeDrawer(Gravity.LEFT);
            /*Intent intentToSchedule = new Intent(MainActivity.this, Schedule.class);
            intentToSchedule.putExtra("Schedule",S);
            startActivity(intentToSchedule);*/
        } else if (id == R.id.nav_Group) {
            Intent intentToGroup = new Intent(MainActivity.this, GroupMainActivity.class);
            intentToGroup.putExtra("Group",G);
            startActivity(intentToGroup);


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
