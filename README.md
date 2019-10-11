# Transport-Communication

Py4J and JRE must be installed in all systems that will be using this module and its APIs. [Install Py4J](https://www.py4j.org/install.html)

First run the CommunicationModule.jar using "java -jar" command

## For communication module
1. Run the /bin/ControllerService.jar using "java -jar" command. It will ask for the IP address of the communication module
2. Use /src/python/controller.py as a template to register the controller with the communication module and to implement the 
behavior that handles data from other modules.
Complete behavior must be handled in the one function provided, so it is recommended that you crease a custom dispatcher class that handles the messages.
3. To communicate with the server, refer to /src/python/controller_server.py file. ControllerService must be running and controller 
should be registered with the communication module for this to work

## For any other module
1. Run the /bin/ModuleService.jar using "java -jar" command.
2. Follow the example shown in /src/python/module_controller.py