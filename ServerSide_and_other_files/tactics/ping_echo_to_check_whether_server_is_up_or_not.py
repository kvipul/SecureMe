import os
import time
num_ping = 10;
server_name = "172.20.176.195"; #give the name or the ip of the server

while True:
	response = os.system("ping -c " + str(1) +" " + server_name);#number of packets to be sent at a time
	if response == 0:
	    print "Ping successful. The " + server_name + " is UP"
	else:
	    print "Ping unsuccessful. The " + server_name + " is DOWN"
	    #send message to the required owner
	time.sleep(1)#time between successive pings