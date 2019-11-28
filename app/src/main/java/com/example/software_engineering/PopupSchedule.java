package com.example.software_engineering;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class PopupSchedule extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_add_schedule);

        Button time_button = findViewById(R.id.set_time_schedule);
        time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent time_schedule = new Intent(PopupSchedule.this, ScheduleMainActivity.class);
                time_schedule.putExtra("num",1);
                startActivity(time_schedule);
            }
        });

        Button location_button = findViewById(R.id.set_location_schedule);
        location_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent location_schedule = new Intent(PopupSchedule.this, ScheduleMainActivity.class);

                location_schedule.putExtra("num",2);
                startActivity(location_schedule);
            }
        });
    }

}
