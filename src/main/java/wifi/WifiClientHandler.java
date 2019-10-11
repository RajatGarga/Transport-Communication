package wifi;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import py4j.ClientServer;
import py4j.GatewayServer;
import tcom.ContInterface;
import tcom.Message;

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
                	System.err.println("New Controller Registered!");
                	controller_dis = dis;
                	controller_dos = dos;
                } else{
                	Message message = Message.getObjectFromJSON(received);
                	if(message.isForController()) {
                		System.out.println("For controller : " + message.getData());
                		controller_dos.writeUTF(received);
                		dos.writeUTF("OK");
                	}else {
                		serverResponse = tcom.MessageHandler.makeRequest(message);
                    	dos.writeUTF(serverResponse);
                	}
                	//break;
                	//dos.println(serverResponse);
                }
            } catch (IOException e) { 
                //e.printStackTrace(); 
                this.destroy();
                //System.exit(1);
            }
        } 
    } 
} 