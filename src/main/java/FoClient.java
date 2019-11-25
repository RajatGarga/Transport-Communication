import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class FoClient {
	public static void main(String[] args) throws IOException {
        Socket socket = null;
        String host = "127.0.0.1";
        long startTime; //start time
        long firstTime;
        int prevCount = 0;
        socket = new Socket("192.168.43.194", 4444);

        File file = new File("C:\\Users\\rajat garga\\Downloads\\4ftResult.pdf");
        // Get the size of the file
        long length = file.length();
        byte[] bytes = new byte[64 * 1024];
        InputStream in = new FileInputStream(file);
        OutputStream out = socket.getOutputStream();

        int count;
        int counter = 0;
        startTime = System.currentTimeMillis();
        firstTime = System.currentTimeMillis();
        while ((count = in.read(bytes)) > 0) {
        	counter++;
            out.write(bytes, 0, count);
            if((System.currentTimeMillis()-startTime)/1000.0 > 5) {
            	double speed = (((counter-prevCount)*64)/((System.currentTimeMillis()-startTime)/1000.0))/1024;
            	prevCount = counter;
            	System.out.println("Speed = " + speed);
            	startTime = System.currentTimeMillis();
            }
        }
    	double speed = (((counter)*64)/((System.currentTimeMillis()-firstTime)/1000.0))/1024;
    	System.out.println("Average Speed = " + speed);
    	
        out.close();
        in.close();
        socket.close();
    }
}
