package com.example.software_engineering;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

//리사이클러 뷰 테스트를 위해 임의 작성
public class GroupMainActivity extends AppCompatActivity {


    private ArrayList<Group> groupArrayList;
    private CustomAdapter adapter;

    @Override
    public void onBackPressed() {
        ArrayList<String> output = new ArrayList<>();
        Intent intent = new Intent();

        for(Group g : groupArrayList){
            output.add(g.GroupName_output());
        }

        intent.putExtra("list",output);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_main);

        RecyclerView recyclerView = findViewById(R.id.group_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        groupArrayList = new ArrayList<>();

        adapter = new CustomAdapter(groupArrayList);
        adapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                //내용 추가 바람
            }
        });
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK){
            groupArrayList.add(new Group(
                    data.getStringExtra("name"),
                    (ArrayList<GroupMember>) data.getSerializableExtra("list"),
                    data.getIntExtra("color",0)
            ));
            adapter.notifyDataSetChanged();
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
                Intent intent_add = new Intent(GroupMainActivity.this, GroupSubActivity.class);
                startActivityForResult(intent_add,1);

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
