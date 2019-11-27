package com.example.software_engineering;

import java.io.Serializable;
import java.util.ArrayList;

//리사이클러뷰 테스트를 위해 임시로 구현
public class Group implements Serializable {
    private String GroupName;  //Group name
    private int PeopleNumber;  //Number of Group people
    private  int GroupColor = 0;
    //private ArrayList<User> U;  //User List
    //private ArrayList<Schedule> S;  //Schedule List

    public Group(String Group_name, int People_number) {
        this.GroupName = Group_name;
        this.PeopleNumber = People_number;
    }

    public void GroupName_input(String group_name) {
        this.GroupName = group_name;
    }
    public void PeopleNumber_input(int people_number) {
        this.PeopleNumber = people_number;
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
