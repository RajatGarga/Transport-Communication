package wifi;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

import py4j.ClientServer;
import tcom.ContInterface;

public class ControllerService {
	static ContInterface PyInt = null;
	
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter IP of communication module : ");
		String ip = sc.nextLine();
		WifiClient controller = new WifiClient(ip);
		controller.registerAsController();
		ClientServer clientServer = new ClientServer(null);
		PyInt = (ContInterface) clientServer.getPythonServerEntryPoint(new Class[] { ContInterface.class });
	}
	
	public static void newMessage(String msg) {
		//System.out.println("Controller got : " + msg);
		if(PyInt != null) {
			PyInt.newMessage(msg);
		}
	}

}
