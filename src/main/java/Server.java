import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

//JUST FOR TESTING

class ClientHandler extends Thread {
    DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss");
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;


    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        String received;
        String toreturn;
        System.out.println("Successfully started the thread! :)");
        while (true) {
            try {

                // Ask user what he wants
                dos.writeUTF("What do you want?[Date | Time]..\n" +
                        "Type Exit to terminate connection.");

                // receive the answer from client
                received = dis.readUTF();

                if (received.equals("Exit")) {
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }

                // creating Date object
                Date date = new Date();

                // write on output stream based on the
                // answer from the client
                switch (received) {

                    case "Date":
                        toreturn = fordate.format(date);
                        dos.writeUTF(toreturn);
                        break;
                    case "Time":
                        toreturn = fortime.format(date);
                        dos.writeUTF(toreturn);
                        break;

                    default:
                        System.out.println("A client says :" + received);
                        dos.writeUTF("I see you say : " + received);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        try {
            // closing resources
            this.dis.close();
            this.dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class Server {

    ServerSocket sSocket;

    public Server(int port) throws IOException {
        try {
            this.sSocket = new ServerSocket(port);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void CreateClientThread(Socket s, DataInputStream dis, DataOutputStream dos) throws IOException {

        try {
            System.out.println("Will try to start thread now!");
            Thread t = new ClientHandler(s, dis, dos);
            t.start();
        } catch (Exception e) {
            s.close();
            e.printStackTrace();
        }
    }

    public void AcceptConnections(ServerSocket sSocket) throws IOException {

        Socket s = null;

        try {
            // socket object to receive incoming client requests
            s = sSocket.accept();

            System.out.println("A new client is connected : " + s);

            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            CreateClientThread(s, dis, dos);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


    public static void main(String[] args) throws NullPointerException, IOException {
        Server server = new Server(5056);
        try {
            while (true)
                server.AcceptConnections(server.sSocket);
        } catch (NullPointerException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}