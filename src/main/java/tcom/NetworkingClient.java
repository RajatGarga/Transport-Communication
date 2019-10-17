package tcom;

import java.io.IOException;

//Interface to be used for different communication protocols

public interface NetworkingClient {
	public boolean isConnected = false;
	public void makeConnection();
	public String sendMessage(String s) throws IOException;
	public void disconnect() throws IOException;
	//public boolean isConnected();
}
