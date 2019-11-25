import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FoServer {
	public final static int SOCKET_PORT = 5501;
	  public final static String FILE_TO_SEND = "C:\\Users\\rajat garga\\Downloads\\5thsemgs.pdf";
	  
	  public static void main(String[] args) throws IOException {
	        ServerSocket serverSocket = null;

	        try {
	            serverSocket = new ServerSocket(4444);
	        } catch (IOException ex) {
	            System.out.println("Can't setup server on this port number. ");
	        }

	        Socket socket = null;
	        InputStream in = null;
	        OutputStream out = null;

	        try {
	            socket = serverSocket.accept();
	        } catch (IOException ex) {
	            System.out.println("Can't accept client connection. ");
	        }

	        try {
	            in = socket.getInputStream();
	        } catch (IOException ex) {
	            System.out.println("Can't get socket input stream. ");
	        }
	        DataInputStream dis = new DataInputStream(in);
	        String fileName = dis.readUTF();
	        try {
	        	System.out.println("D:\\"+fileName);
	            out = new FileOutputStream("D:\\"+fileName);
	        } catch (FileNotFoundException ex) {
	            System.out.println("File not found. ");
	        }

	        byte[] bytes = new byte[64*1024];

	        int count;
	        while ((count = in.read(bytes)) > 0) {
	            out.write(bytes, 0, count);
	        }

	        out.close();
	        in.close();
	        socket.close();
	        serverSocket.close();
	    }
}
