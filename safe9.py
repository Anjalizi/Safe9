import RPi.GPIO as IO
import requests
import time

IO.setmode(IO.BOARD)                    ## Use BOARD pin numbering.
IO.setup(22, IO.OUT)                    ## set output.
IO.setup(11,IO.OUT)

air = requests.get("https://hacknineapi.herokuapp.com/raspi/currentstatus")


ac = air.json()
cond = ac['message']
#print cond
ans = 'y'
while ans is 'y' or ans is 'Y':

	abhi = raw_input("Enter the token key")
	i = 0

	if abhi == cond:
		print "successful"
		for i in range(5):
			IO.output(22, IO.HIGH)
			IO.output(11, IO.HIGH)
			time.sleep(1)
			IO.output(11,IO.LOW)
			IO.output(22,IO.LOW)
			time.sleep(0.5)
			i = i+1
		break
	else:
		print "Access Denied"
	ans = raw_input("Want to retry?")
