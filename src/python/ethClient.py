from py4j.java_gateway import JavaGateway, GatewayParameters
from py4j.java_gateway import java_import
import json

gateway = JavaGateway()
client = gateway.entry_point

def newClient(ip):
    gateway = JavaGateway()
    client = gateway.entry_point
    java_import(gateway.jvm, 'tcom.Message')
    client.setIp(ip)
    client.makeConnection()
    return client

def registerController():
    client.sendMessage('REGISTER_CONTROLLER')

def getNextMessage():
    s = client.getNextMessage()
    return s

'''
    Takes 3 arguments
    1. Self identifier number/code
    2. size of the message (small/large)
    3. Priority of the message (from 1-3)

    Functions provided
    1. void forController()
    2. boolean isForController()
    3. void setConnection(String url, String reqMethod)
    4. void addRequestProperty(String key, String value)
    5. String getJSONString()
    6. void setData(String data)
'''
def MessageForController(sid, size, priority):
    m = gateway.jvm.Message(sid, size, priority)
    m.forController()
    return m

def sendMessage(message):
    code = client.sendMessage(message.getJSONString())