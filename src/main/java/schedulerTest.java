import java.util.LinkedList;
import java.util.Queue;

import tcom.Message;
import tcom.MessageHandler;

public class schedulerTest {

	public static void main(String[] args) {
		
		Queue<Integer> q = new LinkedList<>();
		q.add(0);
		q.add(1);
		q.add(1);
		q.add(3);
		q.add(4);
		q.add(1);
		q.add(0);
		q.add(2);
		q.add(2);
		q.add(3);
		q.add(3);
		q.add(3);
		q.add(3);
		q.add(3);
		
		for(int i=0; i<9; i++) {
			Message message = new Message("small", "high", "required");
	        message.setConnection("https://postb.in/1572035751911-7721095026936", "POST");
	        message.addRequestProperty("key1", "value1");
	        message.addRequestProperty("key2", "value2");
	        message.addRequestProperty("key3", "value3");
	        message.addRequestProperty("key3", "value4");
	        message.setData("Reqeust number : " + i + " wow!");
	        message.priority = q.poll()+"";
	        System.out.println("Sceduled!");
	        MessageHandler.makeRequest(message);
		}
        try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
