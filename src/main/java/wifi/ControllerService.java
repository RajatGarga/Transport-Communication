package wifi;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

import py4j.ClientServer;
import tcom.ContInterface;

/*
 * The service to be used by controller module
 * It registers as a controller using the WifiClient and
 * then provides a ClientServer gateway so that a python program can access the methods
 * */

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
