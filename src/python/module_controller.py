import ethClient as e

e.newClient("127.0.0.1")
message = e.MessageForController('test_moule', 'small', '1')

# the string passed to this function will be sent to the controller
# structure your message in a JSON Object get it as a string to send
message.setData('Hello to the controller')

e.sendMessage(message)