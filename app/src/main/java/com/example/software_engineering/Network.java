package com.example.software_engineering;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Network extends AsyncTask<String, Void, String> implements Serializable {
    protected boolean finish;
    protected String User_name;
    protected String data;
    private String url;
    private String link;
    private String[] arr_String;//0 = php, 1 = data
    private ArrayList<String> tmp_String;

    Network(){
        finish = false;
        User_name = null;
        data = null;
        url = null;
        link = null;
        arr_String = null;
        tmp_String = null;
    }
    public void Input_data(String... _param){
        arr_String = new String[_param.length];
        arr_String = _param;
        if(arr_String.length > 2){
            data = arr_String[1];
        }
        for(int i=0;i<_param.length;i++){
            System.out.println("Network input : "+arr_String[i]);
        }
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.url = "http://sonjuhy.iptime.org/test.php";
        //this.url = "http://sonjuhy.iptime.org/"+arr_String[0]+".php";
        System.out.println("onPre Success");
        for(int i=0;i<5;i++){
            System.out.println("Network Class : "+i);
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection httpURLConnection = null;

        try {
            URL url = new URL(this.url);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();

            if(data != null) {
                OutputStream wr = httpURLConnection.getOutputStream();
                wr.write(data.getBytes("UTF-8"));
                wr.flush();
                wr.close();
            }

            int responseStatusCode = httpURLConnection.getResponseCode();

            InputStream inputStream;
            if(responseStatusCode == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
                System.out.println("Response OK");
            }
            else{
                inputStream = httpURLConnection.getErrorStream();
                System.out.println("Response Failed");
            }

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while((line = reader.readLine()) != null){
                stringBuilder.append(line);
            }
            httpURLConnection.disconnect();
            System.out.println("here : "+stringBuilder.toString());
            finish = true;
            return stringBuilder.toString();//go to onPostExecute
        }
        catch(IOException e){
            System.out.println("IO Error");
        }
        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println("onPost : " + s);

        /*if(s != null && arr_String != null){
            System.out.println("arr_String[0] : " + arr_String[0]);
            if(arr_String[0].equals("Login_Check")){
                User_name = s;
            }
            else{
                data = s;
            }
        }
        finish = true;*/
    }
}
