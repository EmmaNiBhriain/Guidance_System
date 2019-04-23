from machine import Pin
import time
from mqtt import MQTTClient
from network import WLAN
import machine

global location

########################################
#       Connect to WiFi            #####
########################################

wlan = WLAN(mode=WLAN.STA)

nets = wlan.scan()
print(nets)
for net in nets:
    if net.ssid == 'VMCAD4B64':
        print('Network found!')
        wlan.connect(net.ssid, auth=(net.sec, 'Xdcr7zrm2Jsx'), timeout=5000)
        while not wlan.isconnected():
            machine.idle() # save power while waiting
        print('WLAN connection succeeded!')
        break
    else:
        print("Network not found")


########################################
#       set up mqtt client          #####
########################################

client = MQTTClient("Pycom", "io.adafruit.com",user="EmmaNiBhriain", password="e99caf87d89749f28926c88d7170137c", port=1883)
client.connect()
location = "kitchen"


########################################
# Initialise button on expansion board #
########################################

Pin.exp_board.G17
p_in = Pin(Pin.exp_board.G17, mode=Pin.IN, pull = Pin.PULL_UP)
Pin.exp_board.G17.id()

########################################
#    Check if the button is pressed    #
#    Trigger email if button pressed   #
########################################
while True:
    if p_in() == 0:
        print("Button pressed", p_in())
        client.publish(topic="EmmaNiBhriain/feeds/indoor_location", msg=location)
        client.check_msg()
        print("location published :", location )
        time.sleep(3)
    else:
        pass
