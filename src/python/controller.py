from py4j.clientserver import ClientServer, JavaParameters, PythonParameters
from py4j.java_gateway import JavaGateway
from py4j.java_gateway import java_import

class ControllerModule(object):
    
    def newMessage(self, message):
        # Implement behavior for handling data received from other modules
        # Print and check the structure of JSON Received before implementing
        print(message)
    
    class Java:
        implements = ["tcom.ContInterface"]

cont = ControllerModule()
gateway = ClientServer(
    java_parameters=JavaParameters(),
    python_parameters=PythonParameters(),
    python_server_entry_point=cont)