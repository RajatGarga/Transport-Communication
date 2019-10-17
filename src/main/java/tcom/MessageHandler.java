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
	
	//Method to send a request to the server
    public static String makeRequest(Message message){
        
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
            HashMap<String, String> properties = message.getRequestProperties();
            Iterator it = properties.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String,String> pair = (Map.Entry)it.next();
                con.setRequestProperty(pair.getKey(),pair.getValue());
            }
            if(message.getData() != null ){ 
            	//TODO CHECK POST ETC
            	System.out.println("has data" + message.getData());
                con.setDoOutput(true);
                DataOutputStream wr;
				try {
					wr = new DataOutputStream(con.getOutputStream());
	                wr.writeUTF(message.getData());
	                wr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
            }
            int responseCode;
			try {
				responseCode = con.getResponseCode();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				responseCode = 404;
			}
            if(responseCode == HTTP_OK){
                System.out.println("Got good response!");
                BufferedReader in;
                StringBuffer response = new StringBuffer();
				try {
					in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	                String inputLine;
	                while ((inputLine = in.readLine()) != null) {
	                    response.append(inputLine);
	                }
	                in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
                return response.toString();
            }else {
            	con.disconnect();
            	System.out.println("Didn't get good response : Network Failure!");
            	System.out.println("Will try again after 30 seconds");
            	try {
					Thread.sleep(30000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	System.out.println("Retrying...");
            	return MessageHandler.makeRequest(message);
            }
    }
}
