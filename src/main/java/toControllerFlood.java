import java.io.IOException;
import java.net.UnknownHostException;

import tcom.Message;
import wifi.WifiClient;

public class toControllerFlood {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			WifiClient client = new WifiClient("127.0.0.1");
			Message m= new Message("module1", "small", "1");
			m.setData("1");
			m.forController();
			for(int i=0; i< 5; i++)
				client.sendMessage(m.getJSONString());
			//System.exit(0);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
