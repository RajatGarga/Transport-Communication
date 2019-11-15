package tcom;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.HashMap;

public class Message {
	
	String from;
	String to;
    String size;
    public String priority;
    String reply;
    int starve = 0;
    connInfo connection;

    
    //By default the message is for the Remote Control Center (RCC)
    public Message(String from, String size, String priority){
        this.from = from;
        this.to = "RCC";
    	this.size = size;
        this.priority = priority;
        this.connection = new connInfo();
    }
    
    public void forController() {
    	this.to = "controller";
    }
    
    public boolean isForController() {
    	if(this.to.equals("controller")) {
    		return true;
    	}else {
    		return false;
    	}
    }
    
    public void setConnection(String url, String reqMethod){
        connection.setUrl(url);
        connection.setReqMethod(reqMethod);
        connection.reqProperties = new HashMap<String,String>();
    }

    public void addRequestProperty(String key, String value){
        connection.reqProperties.put(key, value);
    }

    public String getJSONString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Message getObjectFromJSON(String json){
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        return gson.fromJson(reader, Message.class);
    }

    public String getConnectionURL(){
        return this.connection.getUrl();
    }

    public String getRequestMethod(){
        return this.connection.getReqMethod();
    }

    public HashMap<String, String> getRequestProperties(){
        return this.connection.getReqProperties();
    }

    public String getData(){
        return this.connection.getData();
    }

    public void setData(String data){
        //TODO if it is GET then can't set data otherwise it will revert to POST
        this.connection.setData(data);
    }

    class connInfo {
        String url;
        String reqMethod;
        HashMap<String, String> reqProperties;
        String data;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getReqMethod() {
            return reqMethod;
        }

        public void setReqMethod(String reqMethod) {
            this.reqMethod = reqMethod;
        }

        public String getData() {
            return data;
        }

        public HashMap<String, String> getReqProperties() {
            return reqProperties;
        }

        public void setData(String data) {
            this.data = data;
        }

    }
}
