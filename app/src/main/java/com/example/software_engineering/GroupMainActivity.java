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
import com.example.software_engineering.Group_Network;

public class GroupMainActivity extends AppCompatActivity {


    private static ArrayList<Group> groupArrayList = new ArrayList<>();
    private CustomAdapter adapter;

    @Override
    public void onBackPressed() {
        ArrayList<String> output = new ArrayList<>();
        Intent intent = new Intent();

        output.add("");
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

        adapter = new CustomAdapter(groupArrayList);
        adapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(getApplicationContext(),GroupSubActivity.class);
                intent.putExtra("opt","edit");
                intent.putExtra("position",pos);
                intent.putExtra("item",groupArrayList.get(pos));
                startActivityForResult(intent,2);
            }
        });
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK){
            if(requestCode==1){
                ArrayList<GroupMember> list = (ArrayList<GroupMember>) data.getSerializableExtra("list");
                // 여기에 추가할 멤버들 어레이리스트 형식으로 한번에 가져옴
                groupArrayList.add(new Group(data.getStringExtra("name"),list,data.getIntExtra("color",0)));
                Group_Network group_network = new Group_Network();
                User tmpU = new User();
                for(int i=0;i<list.size();i++){
                    group_network.Network_DataArrangement(null,0,"UpLoad", data.getStringExtra("name"), list.get(i).name);
                    tmpU.Network_DataArrangement("UpLoad_Invite",data.getStringExtra("name"), list.get(i).name, list.get(0).name);
                }
            }
            else if(requestCode==2){
                if(data.getBooleanExtra("exit",false)){
                    groupArrayList.remove(data.getIntExtra("position",0));
                }else{
                    groupArrayList.get(data.getIntExtra("position",0)).setGroupMember((ArrayList<GroupMember>) data.getSerializableExtra("list"));
                }
            }
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
                intent_add.putExtra("user",getIntent().getSerializableExtra("user"));
                intent_add.putExtra("opt","add");
                startActivityForResult(intent_add,1);

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
