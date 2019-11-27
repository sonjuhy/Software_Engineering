package com.example.software_engineering;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class Schedule implements Serializable {
    private  int Time;
    private  int Sound;
    private  int Vibration;
    private  int AlarmRepeatCount;
    private  double Place_x;
    private  double Place_y;
    private String Network_data;
    private String Name;//UserName
    private Network n;

    Schedule(){
        Network n = new Network();
    }
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
    public boolean Network_DataArrangement(String... _param){ //Setting for Network Class Value before Working Network Class
        //_param mean String[] _param
        if(_param != null){
            switch(_param[0]){//Frist Parameter(String)
                case "UpLoad"://Login part
                    try {//Make and Fit a style data to send Network Class & Server
                        Network_data = URLEncoder.encode("Time","UTF-8") + "=" + URLEncoder.encode(_param[1],"UTF-8");
                        Network_data += URLEncoder.encode("Sound","UTF-8") + "=" + URLEncoder.encode(_param[2],"UTF-8");
                        Network_data += URLEncoder.encode("Vibration","UTF-8") + "=" + URLEncoder.encode(_param[3],"UTF-8");
                        Network_data += URLEncoder.encode("AlarmRepeatCount","UTF-8") + "=" + URLEncoder.encode(_param[4],"UTF-8");
                        Network_data += URLEncoder.encode("Place_X","UTF-8") + "=" + URLEncoder.encode(_param[5],"UTF-8");
                        Network_data += URLEncoder.encode("Place_y","UTF-8") + "=" + URLEncoder.encode(_param[6],"UTF-8");
                        Network_data += URLEncoder.encode("Name","UTF-8") + "=" + URLEncoder.encode(_param[7],"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    n.Input_data("Schedule_UpLoad",Network_data);//Sending Data & kind of command to Network Class
                    Network_Access();//Running Network
                    if(Network_data.equals(false)){ //Login Failed
                        System.out.println("UpLoad Failed");
                        return false;
                    }
                    else {//Login Success
                        System.out.println("UpLoad Success");
                    }
                    break;
                case "DownLoad"://Download User data part
                    n.Input_data("Get_Schedule_Data");//Sending command to Network Class
                    Network_Access();//Running Network
                    Get_ScheduleData(Network_data, this.Name);//translate JSonData from Server to Java and Save Data
                    break;
            }
        }
        return true;//Working is Success
    }
    private void Get_ScheduleData(String mJsonString, String User_name){//Parsing data(JSon to Java)
        System.out.println("mjson : "+mJsonString);

        try{
            JSONObject jsonObject = new JSONObject(mJsonString);//Make object for Checking frist object data in JsonArray
            JSONArray jsonArray = jsonObject.getJSONArray(User_name);//Checking JSonArray

            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);//JSonArray[i] Data is moved to jsonObject1

                this.Sound = Integer.parseInt(jsonObject1.getString("Sound"));
                this.Vibration = Integer.parseInt(jsonObject1.getString("Vibration"));
                this.AlarmRepeatCount = Integer.parseInt(jsonObject1.getString("AlarmRepeatCount"));
                this.Place_x = Double.parseDouble(jsonObject1.getString("Place_X"));
                this.Place_y = Double.parseDouble(jsonObject1.getString("Place_Y"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void Schedule_input(String Name_input , int Sound_input, int Vibration_input, int Time_input ,int AlarmRepeatCount_input, double x_input , double y_input){
        this.Name = Name_input;
        this.Sound = Sound_input;
        this.Vibration = Vibration_input;
        this.Time = Time_input;
        this.AlarmRepeatCount = AlarmRepeatCount_input;
        this.Place_x = x_input;
        this.Place_y = y_input;
    }
   /*String GroupName_output() {
        return this.Group_name;
    }*/
    int ScheduleTime_output(){
        return this.Time;
    }
    int ScheduleSound_output(){
        return this.Sound;
    }
    int ScheduleVivration_output(){
        return this.Vibration;
    }
    int AlarmRepeatCount_output(){
        return this.AlarmRepeatCount;
    }
    double PlaceX_output(){
        return this.Place_x;
    }
    double PlaceY_output(){
        return this.Place_y;
    }

}
