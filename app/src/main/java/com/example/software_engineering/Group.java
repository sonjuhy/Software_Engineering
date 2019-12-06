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

    //테스트용 임시
    public Group() {
    }

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
    public void GroupMember_input(GroupMember GM){this.groupMember.add(GM);}
    public String GroupName_output() {
        return this.GroupName;
    }
    public int GroupColor_output() {
        return this.GroupColor;
    }
    public int PeopleNumber_output() {
        return this.PeopleNumber;
    }
    public ArrayList<GroupMember> GroupMember_output(){return this.groupMember;}
    public void setGroupMember(ArrayList<GroupMember> groupMember) {
        this.groupMember = groupMember;
        PeopleNumber = groupMember.size();
    }
}
class Group_Network{
    private String Network_data;
    private Network n;

    private void Network_Access(String Action, String Data) {
        n = new Network();//for Using Network without AsyncTask error
        n.Input_data(Action, Data);
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
            if (chose == 0) {//Group Process
                switch (_param[0]) {//Frist Parameter(String)
                    case "UpLoad"://Login part
                        try {//Make and Fit a style data to send Network Class & Server
                            Network_data = URLEncoder.encode("Time", "UTF-8") + "=" + URLEncoder.encode(_param[1], "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        Network_Access("Put_groupdb", Network_data);//Running Network
                        if (Network_data.equals(false)) { //Upload Failed
                            System.out.println("UpLoad Failed");
                            return false;
                        } else {//Upload Success
                            System.out.println("UpLoad Success");
                        }
                        break;
                    case "DownLoad"://Download User data part
                        Network_Access("Get_GroupName", "");//Running Network
                        Get_GroupData(Network_data, U);//translate JSonData from Server to Java and Save Data
                        break;
                }
            }
        }
        else {
                switch ((_param[0])) {
                    case "UpLoad":
                        try {
                            Network_data = URLEncoder.encode("groupName", "UTF-8") + "=" + URLEncoder.encode(_param[1], "UTF-8");
                            Network_data += "&" + URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(_param[2], "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        //Network_Access();
                        Get_GroupData(Network_data, U);
                        if (Network_data.equals(false)) { //Upload Failed
                            System.out.println("UpLoad Failed");
                            return false;
                        } else {//Upload Success
                            System.out.println("UpLoad Success");
                        }
                        break;
                    case "DownLoad":
                        ArrayList<Group> tmp_G = U.UserGroup_Output();
                        for (int i = 0; i < tmp_G.size(); i++) {
                            Network_Access("Get_GroupMember", tmp_G.get(i).GroupName_output());
                            Get_GroupMemberData(Network_data, tmp_G.get(i));
                        }
                        break;
                }
            }

        return true;//Working is Success
    }
    private void Get_GroupData(String mJsonString, User U){//Parsing data(JSon to Java)
        System.out.println("mjson : "+mJsonString);
        String name_tmp = null;
        try{
            JSONObject jsonObject = new JSONObject(mJsonString);//Make object for Checking frist object data in JsonArray
            JSONArray jsonArray = jsonObject.getJSONArray(U.UserID_Output());//Checking JSonArray

            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);//JSonArray[i] Data is moved to jsonObject1
                name_tmp = jsonObject1.getString("groupName");
                Group G = new Group(name_tmp,null,0);
                U.Group_Input(G);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void Get_GroupMemberData(String mJsonString, Group G){
        String name_tmp = null;
        try{
            JSONObject jsonObject = new JSONObject(mJsonString);//Make object for Checking frist object data in JsonArray
            JSONArray jsonArray = jsonObject.getJSONArray(G.GroupName_output());//Checking JSonArray

            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);//JSonArray[i] Data is moved to jsonObject1
                name_tmp = jsonObject1.getString("userID");
                GroupMember GM = new GroupMember(name_tmp,G.GroupName_output(),""); // 여기 폰번호 어디감 ?
                G.GroupMember_input(GM);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}