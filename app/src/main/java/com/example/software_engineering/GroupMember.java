package com.example.software_engineering;

import java.io.Serializable;

public class GroupMember implements Serializable {
    public String id;
    public String name;
    public int PhoneNumber;  //Number of Group people

    public GroupMember(String id, String name, int phoneNumber) {
        this.id = id;
        this.name = name;
        PhoneNumber = phoneNumber;
    }
}
