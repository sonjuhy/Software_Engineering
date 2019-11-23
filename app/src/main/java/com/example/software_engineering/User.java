package com.example.software_engineering;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static java.lang.Thread.sleep;

public class User{
    private String Name;
    private String ID;
    private String PW;
    private String Phone_Num;
    private String Network_data;
    private double Place_x;
    private double Place_y;
    private ArrayList<Schedule> S;
    private ArrayList<Group> G;
    private Network n;

    User(){
        n = new Network();
    }
    private void Network_Access() {
        n = new Network();
        try {
            Network_data = n.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(true){
            if(n.finish == true){
                System.out.println("Asyn finish");
                break;
            }
        }
    }
    public void Network_DataArrangement(String... _param){
        if(_param != null){
            switch(_param[0]){
                case "Login":
                    try {
                        Network_data = URLEncoder.encode("ID","UTF-8") + "=" + URLEncoder.encode(_param[1],"UTF-8");
                        Network_data += URLEncoder.encode("PW","UTF-8") + "=" + URLEncoder.encode(_param[2],"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    n.Input_data("Login_Check",Network_data);
                    Network_Access();
                    n.Input_data("Login_DownLoad");
                    Network_Access();
                    Get_UserData(Network_data, n.User_name);
                    break;
                case "Get_Data":
                    n.Input_data("Get_UserData");
                    Network_Access();
                    Get_UserData(Network_data, this.Name);
                    break;
                case "Upload_Data":
                    try{
                        Network_data = URLEncoder.encode("NAME","UTF-8") + "=" + URLEncoder.encode(this.Name,"UTF-8");
                        Network_data += URLEncoder.encode("ID","UTF-8") + "=" + URLEncoder.encode(this.ID,"UTF-8");
                        Network_data += URLEncoder.encode("PW","UTF-8") + "=" + URLEncoder.encode(this.PW,"UTF-8");
                        Network_data += URLEncoder.encode("Phone_Num","UTF-8") + "=" + URLEncoder.encode(this.Phone_Num,"UTF-8");
                    }
                    catch(UnsupportedEncodingException e){
                        e.printStackTrace();
                    }
                    n.Input_data("UpLoad_UserData",Network_data);
                    Network_Access();
                    break;
            }
        }
    }
    private void Get_UserData(String mJsonString, String User_name){
        System.out.println("mjson : "+mJsonString);
        try{
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(User_name);

            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                String tmp_Name = jsonObject1.getString("Name");
                String tmp_PhoneNum = jsonObject1.getString("Phone_num");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void UserInfo_Input(String name_input, String ID_input, String PW_input, String Phone_input){
        this.Name = name_input;
        this.ID = ID_input;
        this.PW = PW_input;
        this.Phone_Num = Phone_input;
    }
    public void Schedule_Input(Schedule S_input){
        this.S.add(S_input);
    }
    public void Group_Input(Group G_input){
        this.G.add(G_input);
    }
    public void Place_Input(double x, double y){
        this.Place_x = x;
        this.Place_y = y;
    }
    public String UserName_Output(){
        return this.Name;
    }
    public String UserID_Output(){
        return this.ID;
    }
    public String UserPW_Output(){
        return this.PW;
    }
    public String UserPhone_num_Output(){
        return this.Phone_Num;
    }
    public double UserPlaceX_Output(){
        return this.Place_x;
    }
    public double UserPlaceY_Output(){
        return this.Place_y;
    }
    public ArrayList<Schedule> UserSchedule_Output(){
        return this.S;
    }
    public ArrayList<Group> UserGroup_Output(){
        return this.G;
    }
}
