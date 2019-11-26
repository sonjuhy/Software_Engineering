package com.example.software_engineering;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

//리사이클러 뷰 테스트를 위해 임의 작성
public class GroupMainActivity extends AppCompatActivity {

    private ArrayList<Group> G;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_main);

        RecyclerView recyclerView = findViewById(R.id.group_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        G = new ArrayList<>();
        G.add(new Group("A",1));
        G.add(new Group("B",2));
        G.add(new Group("C",3));
        G.add(new Group("D",4));

        adapter = new CustomAdapter(G);
        recyclerView.setAdapter(adapter);


    }
}
