package com.example.software_engineering;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;


public class Schedule implements Serializable {
    private  Calendar calendar;
    private  int Sound;
    private  int Vibration;

    private int AlarmRepeatCount;
    private  double Place_x;
    private  double Place_y;
    private  int alarmType;

    private String content;
    private String Time;
    private String Place;
    private Group group;
    private String Name;
    private String ID; //UserID


    public Schedule(String name, String content, String Time, String Place, String ID, double place_x,double place_y, int alarmRepeatCount, int Sound, int Vibration)
    {
        this.Name =name;
        this.ID = ID;
        this.content =content;
        this.Place_x = place_x;
        this.Place_y = place_y;
        this.Time = Time;
        this.Place = Place;
        this.AlarmRepeatCount = alarmRepeatCount;
        this.Sound = Sound;
        this.Vibration =Vibration;
    }


    public Schedule(String name, String content,Calendar calendar, int alarmRepeatCount, int Sound, int Vibration)
    {
        this.Name =name;
        this.content =content;
        this.calendar = calendar;
        this.AlarmRepeatCount = alarmRepeatCount;
        this.Sound = Sound;
        this.Vibration =Vibration;
    }

    public String time_cal()
    {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        String time = (hour+"시 "+min+"분");

        return time;
    }

    public String locationXY()
    {
        String locationXY = ("X좌표: "+Place_x+" Y좌표: "+Place_y);
        return locationXY;
    }


    public Calendar getCalendar()
    {
        return calendar;
    }

    Schedule(){
        Network n = new Network();
    }

    /*void Schedule_input(String Name_input , int Sound_input, int Vibration_input, int Time_input ,int AlarmRepeatCount_input, double x_input , double y_input){
        this.Name = Name_input;
        this.Sound = Sound_input;
        this.Vibration = Vibration_input;
        this.Time = Time_input;
        this.AlarmRepeatCount = AlarmRepeatCount_input;
        this.Place_x = x_input;
        this.Place_y = y_input;
    }*/ //v필요없음 삭제필요
   /*String GroupName_output() {
        return this.Group.name;
    }*/
    /*int ScheduleTime_output(){
        return this.Time;
    }*/
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
class Schedule_Network implements Serializable{
    private String Network_data;
    private Network n;

    Schedule_Network(){
        n = new Network();
    }
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
    public boolean Network_DataArrangement(User U,String... _param){ //Setting for Network Class Value before Working Network Class
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
                    Network_Access("Schedule_UpLoad", Network_data);//Sending Data, kind of command to Network Class &Running Network
                    if(Network_data.equals(false)){ //Login Failed
                        System.out.println("UpLoad Failed");
                        return false;
                    }
                    else {//Login Success
                        System.out.println("UpLoad Success");
                    }
                    break;
                case "DownLoad"://Download User data part
                    Network_Access("Get_ScheduleData",U.UserID_Output());//Sending command to Network Class & Running Network
                    Get_ScheduleData(Network_data, U);//translate JSonData from Server to Java and Save Data
                    break;
            }
        }
        return true;//Working is Success
    }
    private void Get_ScheduleData(String mJsonString, User U){//Parsing data(JSon to Java)
        System.out.println("mjson : "+mJsonString);

        int Sound, Vibration ,AlarmRepeatCount;
        double Place_x = 0.0, Place_y = 0.0;
        String Name, Contens, Time, Place;
        try{
            JSONObject jsonObject = new JSONObject(mJsonString);//Make object for Checking frist object data in JsonArray
            JSONArray jsonArray = jsonObject.getJSONArray(U.UserID_Output());//Checking JSonArray

            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);//JSonArray[i] Data is moved to jsonObject1

                Sound = Integer.parseInt(jsonObject1.getString("Sound"));
                Vibration = Integer.parseInt(jsonObject1.getString("Vibration"));
                AlarmRepeatCount = Integer.parseInt(jsonObject1.getString("AlarmRepeatCount"));
                Name = jsonObject1.getString("calendarName");
                Contens = jsonObject1.getString("calendarContens");
                Time = jsonObject1.getString("calendarTime");
                Place = jsonObject1.getString("calendarPlace");

                Schedule schedule_tmp = new Schedule(Name,Contens, Time, Place, U.UserID_Output(), Place_x, Place_y, AlarmRepeatCount, Sound, Vibration);//Data Input
                if(Place.equals("")){
                    U.TimeSchedule_Input(schedule_tmp);
                }
                else{
                    U.PlaceSchedule_Input(schedule_tmp);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
