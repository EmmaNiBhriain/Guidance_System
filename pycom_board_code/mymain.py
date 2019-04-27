import ufirebase as firebase
import ubinascii
import time
from network import Bluetooth
from machine import Pin
from network import WLAN
import machine
from mqtt import MQTTClient

########################################
#       Initialize Pin 9 as output    #
########################################

Pin.exp_board.G9
p_out = Pin(Pin.exp_board.G9, mode=Pin.OUT)
Pin.exp_board.G9.id()


########################################
# Initialise button on expansion board #
########################################
Pin.exp_board.G17
p_in = Pin(Pin.exp_board.G17, mode=Pin.IN, pull = Pin.PULL_UP)
Pin.exp_board.G17.id()



#################################################
#       Connect to home wifi network            #
#################################################
def wifiConnect():
    beaconId = ""
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

#################################################
#        Retrieve Beacon ID of destination      #
#################################################
def readDestination():
    try:
        URL = 'guidancesystem-f0136'  #name of database
        destination = firebase.get(URL+'/users/007eob@gmail,com/Destination')
    except:
        print("Cannot read from firebase, reconnecting to wifi")
        wifiConnect()
        time.sleep(3)
        readDestination()
    value = next(iter(destination.values())) #most recent destination object
    print(value)
    beaconId =  value["bluetoothId"] #bluetooth Id of most recent destination object
    print(beaconId)
    print("Beacon ID", str.encode(beaconId))
    return beaconId

#value = next(iter(destination.values()))
#print(value)

#################################################
#        Check if a user has arrived            #
#################################################
def checkId():
    print("BEACON ID", beaconId)
    if beaconId!="" :
        if str.encode(beaconId) in dangerAreas: del dangerAreas[str.encode(beaconId)]
        bluetooth = Bluetooth()
        bluetooth.start_scan(10)
        while bluetooth.isscanning():
            adv = bluetooth.get_adv()
            if adv:
                # try to get the complete name
                #print(bluetooth.resolve_adv_data(adv.data, Bluetooth.ADV_NAME_CMPL))

                if(adv.rssi > -63):
                    mfg_data = adv.mac #bluetooth.resolve_adv_data(adv.data, Bluetooth.ADV_MANUFACTURER_DATA)

                    if mfg_data:
                        # try to get the manufacturer data (Apple's iBeacon data is sent here)
                        #print(ubinascii.hexlify(mfg_data))
                        tempIdValue = ubinascii.hexlify(bluetooth.resolve_adv_data(adv.data, Bluetooth.ADV_MANUFACTURER_DATA))
                        time.sleep(2)
                        print(tempIdValue)
                        if(tempIdValue == str.encode(beaconId)):
                            print("you have arrived", adv.rssi)
                            break
                        else:
                            if ubinascii.hexlify(bluetooth.resolve_adv_data(adv.data, Bluetooth.ADV_MANUFACTURER_DATA)) in dangerAreas:
                                p_out.value(1) #Vibrate the motor
                                time.sleep(2)
                                p_out.value(0)
                                time.sleep(3)
                                if(len(locations)>0):
                                    if(dangerAreas[ubinascii.hexlify(bluetooth.resolve_adv_data(adv.data, Bluetooth.ADV_MANUFACTURER_DATA))] != locations[-1]):
                                        locations.append(dangerAreas[ubinascii.hexlify(bluetooth.resolve_adv_data(adv.data, Bluetooth.ADV_MANUFACTURER_DATA))])
                                        print("location: ", locations[-1])
                                        time.sleep(3)
                                    break
                                else:
                                    locations.append(ubinascii.hexlify(bluetooth.resolve_adv_data(adv.data, Bluetooth.ADV_MANUFACTURER_DATA)))
                                    print("location: ",locations[-1])
                                    time.sleep(3)
                                    break

                                print("Location size ", len(locations))
                                print("Data of danger device", ubinascii.hexlify(bluetooth.resolve_adv_data(adv.data, Bluetooth.ADV_MANUFACTURER_DATA)), " strength ", adv.rssi)
                            else:
                                print("Data of device", ubinascii.hexlify(bluetooth.resolve_adv_data(adv.data, Bluetooth.ADV_MANUFACTURER_DATA)), " strength ", adv.rssi)
                else:
                    continue
    else:
        print("No beaconId")


#################################################
#        Initialise MQTT setupClient           #
#################################################
def setupClient():
    global client
    client = MQTTClient("Pycom", "io.adafruit.com",user="EmmaNiBhriain", password="e99caf87d89749f28926c88d7170137c", port=1883)
    client.connect()


#################################################
#        Trigger an email to be sent           #
#################################################

def publishLocation():
    client.publish(topic="EmmaNiBhriain/feeds/indoor_location", msg=locations[-1]) #publish most recent location
    client.check_msg()
    print("location published :", locations[-1] )

#################################################
#        Called based on timer values           #
#################################################
def handleTimer(alarm):
    global seconds
    seconds += 1 #increment seconds counter
    if(seconds == 100):
        print("Time's up")
        publishLocation() #call for assistance
        alarm.cancel() #end timer after 10 seconds
    elif p_in() == 0:
        print("Button pressed", p_in())
        publishLocation() # call for assistance
        time.sleep(3)
        alarm.cancel() #stop the timer
    else:
        print("seconds ", seconds)

print(beaconId)
global locations
locations = ["starting position"]
dangerAreas = {str.encode("590002150112233445566778899aabbccddeeff03c6a79d1bb"): "kitchen", str.encode("590002150112233445566778899aabbccddeeff088072c42bb"):"bathroom", str.encode("590002150112233445566778899aabbccddeeff0dd907caabb"):"living room"}
#beaconId = "590002150112233445566778899aabbccddeeff03c6a79d1bb" #test value
wifiConnect()
beaconId = readDestination()
setupClient()
seconds = 0
tim = machine.Timer.Alarm(handler= handleTimer, s=1, periodic=True)
checkId()
