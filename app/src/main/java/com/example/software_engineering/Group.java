package com.example.software_engineering;

import java.io.Serializable;
import java.util.ArrayList;

//리사이클러뷰 테스트를 위해 임시로 구현
public class Group implements Serializable {
    private String group_name;  //Group name
    //private ArrayList<User> U;  //User List
    //private ArrayList<Schedule> S;  //Schedule List
    private int people_number;  //Number of Group people

    public Group(String group_name, int people_number) {
        this.group_name = group_name;
        this.people_number = people_number;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public int getPeople_number() {
        return people_number;
    }

    public void setPeople_number(int people_number) {
        this.people_number = people_number;
    }


}
