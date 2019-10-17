import tcom.Message;

//JUST FOR TESTING
public class json {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String dataString = "hello world!";
		Message message = new Message("small", "high", "required");
        message.setConnection("https://ennubno89hle.x.pipedream.net", "POST");
        message.addRequestProperty("key1", "value1");
        message.addRequestProperty("key2", "value2");
        message.addRequestProperty("key3", "value3");
        message.addRequestProperty("keay3", "value4");
        message.setData(dataString);
        System.out.println(message.getJSONString());
	}

}
