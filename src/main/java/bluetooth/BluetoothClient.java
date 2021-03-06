package bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.obex.*;

public class BluetoothClient implements tcom.NetworkingClient {
	
	static String serverURL;
	boolean isConnected;
	static ClientSession clientSession;

	
	@Override
	public void makeConnection() {
		this.isConnected = false;
		if(this.isConnected) {
			return;
		}
		String[] searchArgs = null;
        // Connect to OBEXPutServer from examples
        searchArgs = new String[] { "11111111111111111111111111111123" };
        try {
			ServicesSearch.main(searchArgs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (ServicesSearch.serviceFound.size() == 0) {
            System.out.println("OBEX service not found");
            return;
        }
        serverURL = (String)ServicesSearch.serviceFound.elementAt(0);
        try {
			clientSession = (ClientSession) Connector.open(serverURL);
			this.isConnected = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String sendMessage(String s) throws IOException {
		HeaderSet hsConnectReply = clientSession.connect(null);
        if (hsConnectReply.getResponseCode() != ResponseCodes.OBEX_HTTP_OK) {
            System.out.println("Failed to connect");
            return null;
        }

        HeaderSet hsOperation = clientSession.createHeaderSet();
        hsOperation.setHeader(HeaderSet.NAME, "Hello.txt");
        hsOperation.setHeader(HeaderSet.TYPE, "text");

        //Create PUT Operation
        Operation putOperation = clientSession.put(hsOperation);

        // Send some text to server
        byte data[] = s.getBytes("iso-8859-1");
        OutputStream os = putOperation.openOutputStream();
        os.write(data);
        os.close();
        
        InputStream is = putOperation.openInputStream();
        StringBuffer buf = new StringBuffer(); 
        int data2; 
        while ((data2 = is.read()) != -1) { 
                buf.append((char) data2); 
        }
        String returnString = buf.toString();
        is.close();
		putOperation.close();
        return returnString;
	}
	
	@Override
	public void disconnect() throws IOException {
        clientSession.disconnect(null);
        clientSession.close();
	}
}