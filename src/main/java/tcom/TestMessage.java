package tcom;


//JUST FOR TESTING

public class TestMessage {

    public static void main(String[] args){

        Message message = new Message("Small", "high", "no");
        message.setConnection("https://enjgmc47bodec.x.pipedream.net", "GET");
        message.addRequestProperty("key1", "value1");
        message.addRequestProperty("key2", "value2");
        message.addRequestProperty("key3", "value4");
        message.setData("lots and lots and lots of data :P this was supposed to be get");


        MessageHandler.makeRequest(message);
    }
}
