package wifi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class WifiClient implements tcom.NetworkingClient{
	
	InetAddress ip;
	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	boolean isConnected=false;
	
	public WifiClient(String ip) throws UnknownHostException {
		super();
		this.ip = InetAddress.getByName(ip);
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
		this.disconnect();
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
