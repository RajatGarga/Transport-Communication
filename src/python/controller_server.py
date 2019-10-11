from py4j.java_gateway import JavaGateway
from py4j.java_gateway import java_import

#to be done only once
gateway = JavaGateway()
java_import(gateway.jvm, 'tcom.Message')
java_import(gateway.jvm, 'wifi.WifiClient')

#use appropriate ip address for the communication module machine
client = gateway.jvm.WifiClient("127.0.0.1")
client.makeConnection()

m = gateway.jvm.Message('controller', 'small', '1')
m.setConnection("https://ennubno89hle.x.pipedream.net", "POST")
m.addRequestProperty("key1", "value1")
m.addRequestProperty("key2", "value2")
m.addRequestProperty("key3", "value3")
m.setData('Hello to the server!!') #POST data

response = client.sendMessage(m.getJSONString())
print("response : " + response)