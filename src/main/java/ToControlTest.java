import java.io.IOException;
import java.net.UnknownHostException;

import tcom.Message;
import wifi.WifiClient;

public class ToControlTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			WifiClient client = new WifiClient("127.0.0.1");
			Message m= new Message("testModule", "small", "1");
			m.setData("Hello controller!");
			m.forController();
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
