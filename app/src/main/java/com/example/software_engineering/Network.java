package com.example.software_engineering;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Network extends AsyncTask<String, Void, String> implements Serializable {
    protected boolean finish;
    protected String User_name;
    protected String data;
    private String url;
    private String link;
    private String[] arr_String;//0 = php, 1 = data
    private ArrayList<String> tmp_String;

    private HttpURLConnection httpURLConnection;
    private OutputStream wr;
    private InputStream inputStream;
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
        if(arr_String.length > 1){
            data = arr_String[1];
        }
        for(int i=0;i<_param.length;i++){
            System.out.println("Network input : "+arr_String[i]);
        }
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //this.url = "http://sonjuhy.iptime.org/SE/Login_Check.php";
        this.url = "http://sonjuhy.iptime.org/SE/"+arr_String[0]+".php";
        System.out.println("onPre Success");
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(this.url);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDefaultUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();

            System.out.println("Network class data : "+data);

            if(data != null) {
                System.out.println("Working here");
                wr = httpURLConnection.getOutputStream();
                wr.write(data.getBytes("UTF-8"));
                wr.flush();
                wr.close();
            }

            int responseStatusCode = httpURLConnection.getResponseCode();

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
    }
}
class Network_Access implements Serializable{
    public String Network_Access(String Action, String Network_data) {
        Network n = new Network();//for Using Network without AsyncTask error
        n.Input_data(Action, Network_data);//Sending Data & kind of command to Network Class
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
        return Network_data;
    }
}

