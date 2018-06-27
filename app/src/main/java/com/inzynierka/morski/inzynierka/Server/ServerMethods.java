package com.inzynierka.morski.inzynierka.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import android.util.Log;

public class ServerMethods {

    public void sendMail(final String to, final String topic, final String message){
        new Thread(new Runnable() {
            public void run() {

                try{
                    //URL url = new URL("http://192.168.0.144:8080/FirstServlet/HelloWorldServlet");
                    //URL url = new URL("http://192.168.0.144:8080/Server/InzynierkaServer");
                    URL url = new URL("http://tylko.no-ip.org:5555/Server/InzynierkaServer");
                    URLConnection connection = url.openConnection();

                    String inputString = "sendMail#" + to +"#" + topic +"#" + message;

                    Log.d("inputString", inputString);

                    connection.setDoOutput(true);
                    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                    out.write(inputString);
                    out.close();

                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    //String returnString="";


                }catch(Exception e)
                {
                    Log.d("Exception",e.toString());
                }

            }
        }).start();
    }

    public void sendBackup(){

    }

}
