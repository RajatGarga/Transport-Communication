import java.io.IOException;
import java.net.UnknownHostException;

import wifi.WifiClient;

public class fotest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		WifiClient wc = new WifiClient("127.0.0.1");
		wc.sendFile("D:\\", "dse.mp4", "192.168.43.152");
		//wc.sendFile("C:\\Users\\rajat garga\\Downloads\\", "5thsemgs.pdf", "127.0.0.1");
	}
}