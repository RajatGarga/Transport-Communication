import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

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
		
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the local IP addresss of host on WiFi: ");
		String hostip = sc.nextLine();
		System.out.print("Enter your data string : ");
		String dataString = sc.nextLine();
		
		try {
			client = new WifiClient(hostip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		if(client != null) {
			//client.makeConnection();
			Message message = new Message("small", "high", "required");
	        message.setConnection("https://ennubno89hle.x.pipedream.net", "POST");
	        message.addRequestProperty("key1", "value1");
	        message.addRequestProperty("key2", "value2");
	        message.addRequestProperty("key3", "value3");
	        message.addRequestProperty("keay3", "value4");
	        message.setData(dataString);
	        String response = null;
	        try {
				response = client.sendMessage(message.getJSONString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        if(response != null) {
	        	System.out.println("Received a response from the server : ");
	        	System.out.println("\n"+response+"\n");
	        }
		}
		
		
		
		System.out.println("\n");
		System.out.println("STARTING ETHERNET CLIENT TEST");
		System.out.println("=========================");
		client = null;
		System.out.print("Enter the local IP addresss of host on ethernet: ");
		hostip = sc.nextLine();
		System.out.print("Enter your data string : ");
		dataString = sc.nextLine();
		
		try {
			client = new WifiClient(hostip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(client != null) {
			//client.makeConnection();
			Message message = new Message("small", "high", "required");
	        message.setConnection("https://ennubno89hle.x.pipedream.net", "POST");
	        message.addRequestProperty("key4", "value4");
	        message.addRequestProperty("key5", "value5");
	        message.addRequestProperty("key6", "value6");

	        message.addRequestProperty("keay3", "value4");
	        message.setData(dataString);
	        String response = null;
	        try {
				response = client.sendMessage(message.getJSONString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        if(response != null) {
	        	System.out.println("Received a response from the server : ");
	        	System.out.println("\n"+response+"\n");
	        }
		}
		
		
		System.out.println("\n");
		System.out.println("STARTING BLUETOOTH CLIENT TEST");
		System.out.println("=========================");
		System.out.print("Enter your data string : ");
		dataString = sc.nextLine();
		sc.close();
		client = new BluetoothClient();
		client.makeConnection();
		if(client != null) {
			//client.makeConnection();
			Message message = new Message("small", "high", "yes");
	        message.setConnection("https://ennubno89hle.x.pipedream.net", "POST");
	        message.addRequestProperty("key7", "value7");
	        message.addRequestProperty("key8", "value8");
	        message.addRequestProperty("key9", "value9");
	        message.setData(dataString);
	        String response = null;
	        try {
				response = client.sendMessage(message.getJSONString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        if(response != null) {
	        	System.out.println("Received a response from the bluetooth server : ");
	        	System.out.println("\n"+response+"\n");
	        }
		}
	}

}
