package com.example.software_engineering;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Iterator;

public class GroupSubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_sub);
    }
    public void CreateNewGroup(String GroupName, int PeopleNumber){
        Group group = new Group(GroupName,PeopleNumber);
        User user = new User();
        user.Group_Input(group);
    }
    public void ReviseGroup(String GroupName, int PeopleNumber){
        User user = new User();
        ArrayList GroupLIst = user.UserGroup_Output();
        for(int i = 0; i< GroupLIst.size() ; i++){
            if(GroupLIst.contains(GroupName)){
                int index = GroupLIst.indexOf(GroupLIst);
                GroupLIst.set(index,new Group(GroupName,PeopleNumber));
            }
        }
    }

    public  void  DeleteGroup(String GroupName, int PeopleNumber){
        User user = new User();
        ArrayList GroupLIst = user.UserGroup_Output();
        for(int i = 0; i< GroupLIst.size() ; i++){
            if(GroupLIst.contains(GroupName)){
                int index = GroupLIst.indexOf(GroupLIst);
                GroupLIst.remove(index);
            }
        }
    }
}

