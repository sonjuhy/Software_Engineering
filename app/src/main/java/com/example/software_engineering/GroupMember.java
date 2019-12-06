package com.example.software_engineering;

import java.io.Serializable;

public class GroupMember implements Serializable {
    public String id;
    public String name;
    public String PhoneNumber;  //Number of Group people

    public GroupMember(String id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        PhoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
