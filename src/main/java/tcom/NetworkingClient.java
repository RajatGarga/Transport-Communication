package tcom;

import java.io.IOException;

public interface NetworkingClient {
	public void makeConnection();
	public String sendMessage(String s) throws IOException;
	public void disconnect() throws IOException;
}
