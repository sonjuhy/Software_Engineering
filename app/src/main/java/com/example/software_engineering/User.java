package com.example.software_engineering;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static java.lang.Thread.getAllStackTraces;
import static java.lang.Thread.sleep;

public class User implements Serializable {//implements Serializable for using intent
    private String Name; //User name
    private String ID;  //User ID
    private String PW;  //User PW
    private String Phone_Num;  //User Phone_num
    private String Network_data;  //Data value for communicating Server
    private boolean Login_check;
    private double Place_x;  // User X Location
    private double Place_y;  //User Y Location
    private ArrayList<Schedule> S_Time;  //Time Schedule List
    private ArrayList<Schedule> S_Place;  //Place Schedule List
    private ArrayList<Group> G;  //Group List
    private Network n;  //Network Value for Using Network
    private Network_Access na;
    User(){
        n = new Network();
        na = new Network_Access();
        S_Time = new ArrayList<Schedule>();
        S_Place = new ArrayList<Schedule>();
    }
    public boolean Network_DataArrangement(String... _param){ //Setting for Network Class Value before Working Network Class
        Login_check = true;
        //_param mean String[] _param
        if(_param != null){
            switch(_param[0]){//Frist Parameter(String)
                case "Login"://Login part
                    try {//Make and Fit a style data to send Network Class & Server
                        Network_data = URLEncoder.encode("ID","UTF-8") + "=" + URLEncoder.encode(_param[1],"UTF-8");
                        Network_data += "&" + URLEncoder.encode("PW","UTF-8") + "=" + URLEncoder.encode(_param[2],"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Network_data = na.Network_Access("Login_Check", Network_data);//Running Network
                    System.out.println("Server Response : "+na.Server_Response);
                    if(Network_data.equals("Failed") || na.Server_Response == false){ //Login Failed
                        System.out.println("Login Failed");
                        Login_check = false;
                    }
                    else { //Login Success
                        this.Name = Network_data;
                        this.ID = _param[1];
                        this.PW = _param[2];
                        if(this.Name.equals("")) {
                            Login_check = false;
                        }
                        else {
                            try {
                                Network_data = URLEncoder.encode("ID","UTF-8") + "=" + URLEncoder.encode(this.ID, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            System.out.println("Login Success User class : " + Network_data);
                            Network_data = na.Network_Access("Get_UserData", Network_data);//Running Network
                            System.out.println("Phone Num : "+Network_data);
                            this.Phone_Num = Network_data;
                        }
                    }
                    break;
                case "Get_Data"://Download User data part
                    try {
                        Network_data = URLEncoder.encode("ID","UTF-8") + "=" + URLEncoder.encode(this.ID,"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    na.Network_Access("Get_UserData",Network_data);//Running Network
                    Get_UserData(Network_data, this.ID);//translate JSonData from Server to Java and Save Data
                    break;
                case "Upload_Data"://Upload User data to Server
                    try{//Make and Fit a style data to send Network Class & Server
                        Network_data = URLEncoder.encode("NAME","UTF-8") + "=" + URLEncoder.encode(this.Name,"UTF-8");
                        Network_data += "&" + URLEncoder.encode("ID","UTF-8") + "=" + URLEncoder.encode(this.ID,"UTF-8");
                        Network_data += "&" + URLEncoder.encode("PW","UTF-8") + "=" + URLEncoder.encode(this.PW,"UTF-8");
                        Network_data += "&" + URLEncoder.encode("Phone_Num","UTF-8") + "=" + URLEncoder.encode(this.Phone_Num,"UTF-8");
                    }
                    catch(UnsupportedEncodingException e){
                        e.printStackTrace();
                    }
                   // n.Input_data("UpLoad_UserData",Network_data);//Sending Data & kind of command to Network Class
                    Network_data = na.Network_Access("UpLoad_UserData",Network_data);
                    break;
            }
        }
        if(Login_check == false)
            return false;
        else {
            return true;//Working is Success
        }
    }
    private void Get_UserData(String mJsonString, String User_ID){//Parsing data(JSon to Java)
        System.out.println("mjson : "+mJsonString);
        try{
            JSONObject jsonObject = new JSONObject(mJsonString);//Make object for Checking frist object data in JsonArray
            JSONArray jsonArray = jsonObject.getJSONArray(User_ID);//Checking JSonArray

            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);//JSonArray[i] Data is moved to jsonObject1

                this.Name = jsonObject1.getString("Name");//jsonObject1.Name is copied to User.name to String
                this.Phone_Num = jsonObject1.getString("Phone_num");//jsonObject1.Phone_num is copied to User.Phone_num to String
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
    public void TimeSchedule_set(ArrayList<Schedule> S_input){
        this.S_Time = S_input;
    }
    public void PlaceSchedule_set(ArrayList<Schedule> S_input){
        this.S_Place = S_input;
    }

    public void TimeSchedule_Input(Schedule S_input){
        this.S_Time.add(S_input);
    }
    public void PlaceSchedule_Input(Schedule S_input){
        this.S_Place.add(S_input);
    }
    public void Group_Input(Group G_input){
        this.G.add(G_input);
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
    public ArrayList<Schedule> UserTimeSchedule_Output(){
        return this.S_Time;
    }
    public ArrayList<Schedule> UserPlaceSchedule_Outpu(){
        return this.S_Place;
    }
    public ArrayList<Group> UserGroup_Output(){
        return this.G;
    }
}
