package wifi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import py4j.GatewayServer;


/*
 * provides all networking services to the modules using a IP based networking interface
 * also creates a GatewayServer for python to connect in case it is to be used by a module
 * */

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
		GatewayServer s = new GatewayServer(app, 25335);
		s.start();
	}
	
	public WifiClient(String ip) throws UnknownHostException {
		super();
		this.ip = InetAddress.getByName(ip);
	}
	
	public void setIp(String ip) throws UnknownHostException {
		this.ip = InetAddress.getByName(ip);
	}

	//Controller module uses this function to register itself as the controller
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
	
//	public String getNextMessage() {
//		if(!this.isController) {
//			return "";
//		}
//		while(dataController.isEmpty()) {
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		String s = dataController.poll();
//		return s;
//	}
	
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
	
	public void sendFile(String filePath, String fileName, String socketAddress) throws IOException {
		if(!this.isConnected) {
			socket = new Socket(ip, 5056);
			isConnected=true;
		}
		dis = new DataInputStream(socket.getInputStream());
		OutputStream out = socket.getOutputStream();
		InputStream in = null;
		dos = new DataOutputStream(out);
		dos.writeUTF("TRANSFER_FILE");
		String received = dis.readUTF();
		if(received.equals("OK")) {
			System.out.println("got ok!");
			dos.writeUTF(fileName);
			dos.writeUTF(socketAddress);
			received = dis.readUTF();
			if(received.equals("READY")) {
				File file = new File(filePath+fileName);
				in = new FileInputStream(file);
				long length = file.length();
		        byte[] bytes = new byte[64 * 1024];
		        int count;
		        while ((count = in.read(bytes)) > 0) {
		            out.write(bytes, 0, count);
		        }
			}
		}
		out.close();
        in.close();
        socket.close();
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
