package wifi;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import py4j.ClientServer;
import py4j.GatewayServer;
import tcom.ContInterface;
import tcom.Message;


//This is a thread that handles all the connections that the communication module gets.
class WifiClientHandler extends Thread  
{	
	static DataInputStream controller_dis = null;
	static DataOutputStream controller_dos = null;
    final DataInputStream dis; 
    final DataOutputStream dos; 
    final Socket s;
    String received;

    // Constructor 
    public WifiClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)  
    { 
        this.s = s; 
        this.dis = dis; 
        this.dos = dos; 
    } 

    @Override
    public void run()  
    {
        while (true)  
        { 
            try {
                // receive from client 
                received = dis.readUTF();
                String serverResponse = null;
                
                if(received.equals("CLOSE_CONNECTION_DDDNUJoujhsou(20uj3hioNOW")) 
                {
                    System.out.println("Closing this connection" + this.s);
                    this.s.close();
                    try
                    { 
                        // closing resources 
                        this.dis.close(); 
                        this.dos.close(); 
                          
                    }catch(IOException e){ 
                        e.printStackTrace(); 
                    } 
                    break; 
                } else if(received.equals("REGISTER_CONTROLLER")){
                	/* Save the I/O streams corresponding to controller in the static variables
                	 * so that all the instances of this thread can access them.
                	 * */
                	System.err.println("New Controller Registered!");
                	controller_dis = dis;
                	controller_dos = dos;
                } else if (received.equals("TRANSFER_FILE")) {
                	System.out.println("Getting Ready to receive a file");
                	dos.writeUTF("OK");
                	String fileName = dis.readUTF();
                	String socketAddress = dis.readUTF();
                	Socket sock = new Socket(socketAddress, 4444); //socket on the server
                	OutputStream out = sock.getOutputStream();
                	DataOutputStream sockout = new DataOutputStream(out);
                	sockout.writeUTF(fileName);
                	dos.writeUTF("READY");
                	System.out.println("Ready!");
                	InputStream in = this.s.getInputStream();
                	byte[] bytes = new byte[64*1024];
        	        int count;
        	        while ((count = in.read(bytes)) > 0) {
        	            out.write(bytes, 0, count);
        	        }
        	        out.close();
        	        in.close();
        	        sock.close();
                	
                } else{
                	Message message = Message.getObjectFromJSON(received);
                	if(message.isForController()) {
                		System.out.println("For controller : " + message.getData());
                		controller_dos.writeUTF(received);
                		dos.writeUTF("OK");
                	}else {
                		dos.writeUTF("OK");
                		tcom.MessageHandler.makeRequest(message);
//                		serverResponse = tcom.MessageHandler.makeRequest(message);
//                    	dos.writeUTF(serverResponse);
                	}
                	//break;
                	//dos.println(serverResponse);
                }
            } catch (IOException e) { 
            	this.stop();
                //e.printStackTrace(); 
                //this.destroy();
                //System.exit(1);
            }
        } 
    } 
} 