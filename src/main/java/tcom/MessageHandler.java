package tcom;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

public class MessageHandler {
    public static String makeRequest(String mess){
        Message message = Message.getObjectFromJSON(mess);
        URL url=null;
        try {
            url = new URL(message.getConnectionURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            //TODO Handle well
        }
        if(url == null){
            return null; //TODO error code
        }
        HttpURLConnection con=null;
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(message.getRequestMethod());
            HashMap<String, String> properties = message.getRequestProperties();
            Iterator it = properties.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String,String> pair = (Map.Entry)it.next();
                con.setRequestProperty(pair.getKey(),pair.getValue());
            }
            if(message.getData() != null ){ //TODO CHECK POST ETC
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeUTF(message.getData());
                wr.close();
            }
            int responseCode = con.getResponseCode();
            if(responseCode == HTTP_OK){
                System.out.println("Got good response!");
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
