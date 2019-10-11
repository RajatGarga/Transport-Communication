package wifi;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class WifiReceiver {
	
	ServerSocket ss;
	static Socket s = null;
	
	public WifiReceiver() throws IOException {
		ss = new ServerSocket(5056);
	}
	
	public String getMessage() throws IOException {
		s = this.ss.accept();
		DataInputStream dis = new DataInputStream(s.getInputStream());
		return dis.readUTF();
	}
	
	public void sendMessage(String message) throws IOException {
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
	}
	
	public static void main(String[] args) throws IOException {
        
        WifiReceiver r;
		try {
			r = new WifiReceiver();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}

        while (true)  
        { 
                        
            try 
            { 
                s = r.ss.accept(); 
                  
                System.out.println("A new client is connected : " + s); 
                
                DataInputStream dis = new DataInputStream(s.getInputStream()); 
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
  
                Thread t = new WifiClientHandler(s, dis, dos); 

                t.start();
                
                //if we don't want asynchronous then
                //t.run();
                  
            } 
            catch (Exception e){ 
                s.close();
                e.printStackTrace(); 
            } 
        } 
    }
}
