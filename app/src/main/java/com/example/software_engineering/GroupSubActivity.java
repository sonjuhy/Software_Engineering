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
    public void CreateNewGroup(String GroupName, int PeopleNumber , int GroupColor){
        Group group = new Group(GroupName,PeopleNumber);
        User user = new User();
        user.Group_Input(group);
    }
    /*public void ReviseGroup(String GroupName, int PeopleNumber , int GroupColor){
        User user = new User();
        ArrayList GroupLIst = user.UserGroup_Output();
        for (Iterator<Group> iter = GroupLIst.iterator(); iter.hasNext(); ) {
            if(iter.next().GroupName_output().equals(GroupName)){ //비교를 이름이아니고 주어진 고유 번호로 해야함

            }

    }*/
}
