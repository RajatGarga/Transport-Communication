package bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.bluetooth.*;
import javax.microedition.io.Connector;
import javax.obex.*;

public class OBEXPutServer {

    static final String serverUUID = "11111111111111111111111111111123";

    public static void main(String[] args) throws IOException {

        LocalDevice.getLocalDevice().setDiscoverable(DiscoveryAgent.GIAC);

        SessionNotifier serverConnection = (SessionNotifier) Connector.open("btgoep://localhost:"
                + serverUUID + ";name=ObexExample");

        int count = 0;
        while(true) {
            RequestHandler handler = new RequestHandler();
            serverConnection.acceptAndOpen(handler);
            System.out.println("Received OBEX connection " + (++count));
        }
    }

    private static class RequestHandler extends ServerRequestHandler {

        public int onPut(Operation op) {
            try {
                HeaderSet hs = op.getReceivedHeaders();
                String name = (String) hs.getHeader(HeaderSet.NAME);
                if (name != null) {
                    System.out.println("put name:" + name);
                }

                InputStream is = op.openInputStream();

                StringBuffer buf = new StringBuffer();
                int data;
                while ((data = is.read()) != -1) {
                    buf.append((char) data);
                }
                String gotJSON = buf.toString();
                System.out.println("got:" + gotJSON);
                
                String response = tcom.MessageHandler.makeRequest(gotJSON);
                
                byte data2[] = response.getBytes("iso-8859-1"); 
                OutputStream os = op.openOutputStream(); 
                os.write(data2); 
                
                op.close();
                
                return ResponseCodes.OBEX_HTTP_OK;
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseCodes.OBEX_HTTP_UNAVAILABLE;
            }
        }

		
		public int onGet(Operation op) {
			// TODO Auto-generated method stub
			return super.onGet(op);
		}
        
    }
}