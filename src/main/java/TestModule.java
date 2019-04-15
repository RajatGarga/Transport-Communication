import java.io.IOException;
import java.net.UnknownHostException;

import bluetooth.BluetoothClient;
import tcom.Message;
import tcom.NetworkingClient;
import wifi.WifiClient;

public class TestModule {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("STARTING WIFI CLIENT TEST");
		System.out.println("=========================");
		NetworkingClient client = null; 
		try {
			client = new WifiClient("localhost");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(client != null) {
			client.makeConnection();
			Message message = new Message("Small", "high", "no");
	        message.setConnection("https://ennubno89hle.x.pipedream.net", "POST");
	        message.addRequestProperty("key1", "value1");
	        message.addRequestProperty("key2", "value2");
	        message.addRequestProperty("key3", "value4");
	        message.setData("lots and lots and lots of data :P this was supposed to be POST from wifi");
	        String response = null;
	        try {
				response = client.sendMessage(message.getJSONString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        if(response != null) {
	        	System.out.println("Received a response from the wifi server : ");
	        	System.out.println(response);
	        }
		}
		

		System.out.println("STARTING BLUETOOTH CLIENT TEST");
		System.out.println("=========================");
		client = new BluetoothClient();
		client.makeConnection();
		if(client != null) {
			client.makeConnection();
			Message message = new Message("Small", "high", "no");
	        message.setConnection("https://ennubno89hle.x.pipedream.net", "POST");
	        message.addRequestProperty("key1", "value1");
	        message.addRequestProperty("key2", "value2");
	        message.addRequestProperty("key3", "value4");
	        message.setData("lots and lots and lots of data :P this was supposed to be POST from bluetooth the second time");
	        String response = null;
	        try {
				response = client.sendMessage(message.getJSONString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        if(response != null) {
	        	System.out.println("Received a response from the bluetooth server : ");
	        	System.out.println(response);
	        }
		}
	}

}
