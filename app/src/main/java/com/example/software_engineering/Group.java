package com.example.software_engineering;

import java.io.Serializable;
import java.util.ArrayList;

//리사이클러뷰 테스트를 위해 임시로 구현
public class Group implements Serializable {
    private String GroupName;  //Group name
    private ArrayList<GroupMember> groupMember;
    private int PeopleNumber;  //Number of Group people
    private  int GroupColor = 0;

    public Group(String GroupName, int PeopleNumber) {
        groupMember = new ArrayList<GroupMember>();
        this.GroupName = GroupName;
        this.PeopleNumber = PeopleNumber;
    }

    public void GroupName_input(String GroupName) {
        this.GroupName = GroupName;
    }
    public void PeopleNumber_input(int PeopleNumber) {
        this.PeopleNumber = PeopleNumber;
    }
    public void GroupColor_input(int GroupColor){
        this.GroupColor = GroupColor;
    }
    public String GroupName_output() {
        return this.GroupName;
    }
    public int GroupColor_output() {
        return this.GroupColor;
    }
    public int PeopleNumber_output() {
        return this.PeopleNumber;
    }

}
