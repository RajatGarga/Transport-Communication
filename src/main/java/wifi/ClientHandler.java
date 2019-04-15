package wifi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class ClientHandler extends Thread  
{
    final DataInputStream dis; 
    final DataOutputStream dos; 
    final Socket s; 

    // Constructor 
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)  
    { 
        this.s = s; 
        this.dis = dis; 
        this.dos = dos; 
    } 

    @Override
    public void run()  
    {
        String received;
        while (true)  
        { 
            try {
                // receive the answer from client 
                received = dis.readUTF(); 
                String serverResponse = null;
                if(received.equals("CLOSE_CONNECTION_DDDNUJoujhsou(20uj3hioNOW")) 
                {
                    System.out.println("Closing this connection" + this.s); 
                    this.s.close();
                    break; 
                }else {
                	serverResponse = tcom.MessageHandler.makeRequest(received);
                	dos.writeUTF(serverResponse);
                }
                
            } catch (IOException e) { 
                e.printStackTrace(); 
            } 
        } 
          
        try
        { 
            // closing resources 
            this.dis.close(); 
            this.dos.close(); 
              
        }catch(IOException e){ 
            e.printStackTrace(); 
        } 
    } 
} 