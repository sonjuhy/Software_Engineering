package com.example.software_engineering;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

//리사이클러뷰 테스트를 위해 임시로 구현
public class Group implements Serializable {
    private String GroupName;  //Group name
    private ArrayList<GroupMember> groupMember;
    private int PeopleNumber;  //Number of Group people
    private int GroupColor = 0;

    public Group(String groupName, ArrayList<GroupMember> groupMember, int groupColor) {
        GroupName = groupName;
        this.groupMember = groupMember;
        PeopleNumber = groupMember.size();
        GroupColor = groupColor;
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
class Group_Network{
    private String Network_data;
    private Network n;

    private void Network_Access() {
        n = new Network();//for Using Network without AsyncTask error
        try {
            Network_data = n.execute().get(); //execute Network and take return value to Network_data
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(true){
            if(n.finish == true){  //when Network doInBackground is End
                System.out.println("Asyn finish");
                break;
            }
        }
    }
    public boolean Network_DataArrangement(User U, int chose, String... _param){ //Setting for Network Class Value before Working Network Class
        //_param mean String[] _param
        if(_param != null) {
            if (chose == 1) {//Group Process
                switch (_param[0]) {//Frist Parameter(String)
                    case "UpLoad"://Login part
                        try {//Make and Fit a style data to send Network Class & Server
                            Network_data = URLEncoder.encode("Time", "UTF-8") + "=" + URLEncoder.encode(_param[1], "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        n.Input_data("Schedule_UpLoad", Network_data);//Sending Data & kind of command to Network Class
                        Network_Access();//Running Network
                        if (Network_data.equals(false)) { //Login Failed
                            System.out.println("UpLoad Failed");
                            return false;
                        } else {//Login Success
                            System.out.println("UpLoad Success");
                        }
                        break;
                    case "DownLoad"://Download User data part
                        n.Input_data("Get_Schedule_Data");//Sending command to Network Class
                        Network_Access();//Running Network
                        Get_ScheduleData(Network_data, U);//translate JSonData from Server to Java and Save Data
                        break;
                }
            }
        }
        else{

        }
        return true;//Working is Success
    }
    private void Get_ScheduleData(String mJsonString, User U){//Parsing data(JSon to Java)
        System.out.println("mjson : "+mJsonString);

        int Sound, Vibration ,AlarmRepeatCount;
        double Place_x, Place_y;
        try{
            JSONObject jsonObject = new JSONObject(mJsonString);//Make object for Checking frist object data in JsonArray
            JSONArray jsonArray = jsonObject.getJSONArray(U.UserID_Output());//Checking JSonArray

            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);//JSonArray[i] Data is moved to jsonObject1

                Sound = Integer.parseInt(jsonObject1.getString("Sound"));
                Vibration = Integer.parseInt(jsonObject1.getString("Vibration"));
                AlarmRepeatCount = Integer.parseInt(jsonObject1.getString("AlarmRepeatCount"));
                Place_x = Double.parseDouble(jsonObject1.getString("Place_X"));
                Place_y = Double.parseDouble(jsonObject1.getString("Place_Y"));
                Schedule schedule_tmp = new Schedule();
                U.UserSchedule_Output().add(schedule_tmp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}