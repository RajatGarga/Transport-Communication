package tcom;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import wifi.WifiClient;

import static java.net.HttpURLConnection.HTTP_OK;

public class MessageHandler {
	
	static HashMap<Integer, Queue<Message>> queue = new HashMap<>();
	static int status = 0;
	
	private static boolean deployIfValid(Message m) {
		if(m.starve > 5) {
			CompletableFuture.runAsync(() -> {
				deployRequest(m);
			});
		}
		Integer priority = Integer.parseInt(m.priority);
		if(Math.pow(2, priority) > status) {
			CompletableFuture.runAsync(() -> {
				deployRequest(m);
			});
			return true; 
		}else if(Math.pow(2,  priority) == status && m.size.toLowerCase().equals("small")) {
			CompletableFuture.runAsync(() -> {
				deployRequest(m);
			});
			return true;
		}
		m.starve++;
		return false;
	}
	
	private static void queueRequest(Message m) {
		if(deployIfValid(m)) {
			return;
		}
		Integer priority = Integer.parseInt(m.priority);
		if(queue.containsKey(priority)) {
			queue.get(priority).add(m);
		}else {
			queue.put(priority, new LinkedList<Message>());
			queue.get(priority).add(m);
		}
	}
	
	private static void deployNext() {
		Set<Integer> priorities = queue.keySet();
		if(priorities.size() <= 0) {
			return;
		}
		int max = Integer.MIN_VALUE;
		for(int i : priorities) {
			if(i>max) {
				max = i;
			}
		}
		if(queue.get(max).size() <= 0) {
			queue.remove(max);
			deployNext();
			return;
		}
		Message m = queue.get(max).poll();
		if(deployIfValid(m)) {
			if(queue.get(max).size() == 0) {
				queue.remove(max);
			}
		}else {
			queue.get(max).add(m);
		}
	}
	
	public static void makeRequest(Message message){
		queueRequest(message);
	}
	
	//Method to send a request to the server
    private static String deployRequest(Message message){
    	System.out.println("DEPLOYING :: " + message.toString());
    	status += Math.pow(2, Integer.parseInt(message.priority));
        URL url=null;
        try {
            url = new URL(message.getConnectionURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            //TODO Handle well
        }
        if(url == null){
        	status -= Math.pow(2, Integer.parseInt(message.priority));
        	deployNext();
            return null; //TODO error code
        }
        OkHttpClient httpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody formBody = RequestBody.create(JSON,message.getData());
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
            	// Get response body
            	System.out.println(response.body().string());
            	return response.body().toString();
            }
        }catch(Exception e) {
        	e.printStackTrace();
        }
        /*
        HttpURLConnection con=null;
            try {
            	System.out.println("making connection!");
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
            	String payload = message.getData();
            	int st = payload.indexOf("{");
            	int end = payload.lastIndexOf("}");
            	System.out.println("has data" + payload.substring(st, end+1));
                con.setDoOutput(true);
                DataOutputStream wr;
				try {
					wr = new DataOutputStream(con.getOutputStream());
	                wr.writeUTF(payload.substring(st, end+1));
	                wr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
            }
            int responseCode = -999;
			try {
				responseCode = con.getResponseCode();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				//responseCode = 404;
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
				status -= Math.pow(2, Integer.parseInt(message.priority));
				System.out.println(response.toString());
				WifiClient client;
				try {
					client = new WifiClient("127.0.0.1");
					Message m= new Message("server", "small", "1");
					m.setData(response.toString());
					m.forController();
					client.sendMessage(m.getJSONString());
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                deployNext();
                return response.toString();
            }else {
            	con.disconnect();
            	System.out.println("Didn't get good response ("+ responseCode +") : Network Failure!");
            	System.out.println("Will try again after 20 seconds");
            	try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	System.out.println("Retrying...");
            	status -= Math.pow(2, Integer.parseInt(message.priority));
            	deployNext();
            	return MessageHandler.deployRequest(message);
            }*/
    }
}
