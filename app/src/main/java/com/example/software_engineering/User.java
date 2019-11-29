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
    private ArrayList<Schedule> S;  //Schedule List
    private ArrayList<Group> G;  //Group List
    private Network n;  //Network Value for Using Network
    private Network_Access na;
    User(){
        n = new Network();
        na = new Network_Access();
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
                    if(Network_data.equals("Failed")){ //Login Failed
                        System.out.println("Login Failed");
                        Login_check = false;
                    }
                    else { //Login Success
                        this.Name = Network_data;
                        Network_data = na.Network_Access("Get_UserData",Network_data);//Running Network
                        System.out.println("Load Name : " + this.Name);
                    }
                    break;
                case "Get_Data"://Download User data part
                    na.Network_Access("Get_UserData",Network_data);//Running Network
                    Get_UserData(Network_data, this.Name);//translate JSonData from Server to Java and Save Data
                    break;
                case "Upload_Data"://Upload User data to Server
                    try{//Make and Fit a style data to send Network Class & Server
                        Network_data = URLEncoder.encode("NAME","UTF-8") + "=" + URLEncoder.encode(this.Name,"UTF-8");
                        Network_data += URLEncoder.encode("ID","UTF-8") + "=" + URLEncoder.encode(this.ID,"UTF-8");
                        Network_data += URLEncoder.encode("PW","UTF-8") + "=" + URLEncoder.encode(this.PW,"UTF-8");
                        Network_data += URLEncoder.encode("Phone_Num","UTF-8") + "=" + URLEncoder.encode(this.Phone_Num,"UTF-8");
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
        return true;//Working is Success
    }
    private void Get_UserData(String mJsonString, String User_name){//Parsing data(JSon to Java)
        System.out.println("mjson : "+mJsonString);
        try{
            JSONObject jsonObject = new JSONObject(mJsonString);//Make object for Checking frist object data in JsonArray
            JSONArray jsonArray = jsonObject.getJSONArray(User_name);//Checking JSonArray

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
    public void Schedule_Input(Schedule S_input){
        this.S.add(S_input);
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
    public ArrayList<Schedule> UserSchedule_Output(){
        return this.S;
    }
    public ArrayList<Group> UserGroup_Output(){
        return this.G;
    }
}
