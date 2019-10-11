package wifi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import py4j.GatewayServer;


public class WifiClient implements tcom.NetworkingClient{
	
	InetAddress ip;
	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	static Queue<String> dataController;
	boolean isConnected=false;
	boolean isController=false;
	
	public static void main(String[] args) throws UnknownHostException {
		WifiClient app = new WifiClient("127.0.0.1");
		GatewayServer s = new GatewayServer(app);
		s.start();
	}
	
	public WifiClient(String ip) throws UnknownHostException {
		super();
		this.ip = InetAddress.getByName(ip);
	}
	
	public void setIp(String ip) throws UnknownHostException {
		this.ip = InetAddress.getByName(ip);
	}

	public void registerAsController() throws IOException{
		dataController = new LinkedList<String>();
		this.isController = true;
		if(!this.isConnected) {
			socket = new Socket(ip, 5056);
			isConnected=true;
		}
		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());
		dos.writeUTF("REGISTER_CONTROLLER");
		Thread thread = new Thread() {
			public void run() {
				while(true) {
					try {
						String received = dis.readUTF();
						if(!received.isEmpty()) {
							dataController.add(received);
							ControllerService.newMessage(received);
							//System.out.println("ctrlrec : " + received);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
			}
		};
		thread.start();
	}
	
	public String getNextMessage() {
		if(!this.isController) {
			return "";
		}
		while(dataController.isEmpty()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String s = dataController.poll();
		return s;
	}
	
	@Override
	public void makeConnection() {
		try {
			socket = new Socket(ip, 5056);
			isConnected=true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String sendMessage(String s) throws IOException {
		if(!this.isConnected) {
			socket = new Socket(ip, 5056);
			isConnected=true;
		}
		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());
		dos.writeUTF(s);
		String received = dis.readUTF(); 
		//System.out.println("received :" + received);
		//this.disconnect();
		return received;
	}

	@Override
	public void disconnect() throws IOException {
		dos.writeUTF("CLOSE_CONNECTION_DDDNUJoujhsou(20uj3hioNOW");
		this.isConnected = false;
		socket.close();
		dos.close(); 
        dis.close();
	}

}
