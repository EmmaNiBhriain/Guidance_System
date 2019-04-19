import ufirebase as firebase
import ubinascii
import time
from network import Bluetooth
from machine import Pin
from network import WLAN
import machine

# initialize `P9` in gpio mode and make it an output
Pin.exp_board.G9
p_out = Pin(Pin.exp_board.G9, mode=Pin.OUT)
Pin.exp_board.G9.id()

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
        readDestination()
    value = next(iter(destination.values())) #most recent destination object
    print(value)
    beaconId =  value["bluetoothId"]#next(iter(value.values())) #bluetooth Id of most recent destination object
    print(beaconId)
    print("Beacon ID", str.encode(beaconId))
    return beaconId

#value = next(iter(destination.values()))
#print(value)


def checkId():
    print("BEACON ID", beaconId)
    if beaconId!="" :
        if str.encode(beaconId) in dangerAreas: dangerAreas.remove(str.encode(beaconId))
        bluetooth = Bluetooth()
        bluetooth.start_scan(20)
        while bluetooth.isscanning():
            adv = bluetooth.get_adv()
            if adv:
                # try to get the complete name
                #print(bluetooth.resolve_adv_data(adv.data, Bluetooth.ADV_NAME_CMPL))

                mfg_data = adv.mac #bluetooth.resolve_adv_data(adv.data, Bluetooth.ADV_MANUFACTURER_DATA)

                if mfg_data:
                    # try to get the manufacturer data (Apple's iBeacon data is sent here)
                    #print(ubinascii.hexlify(mfg_data))
                    if(ubinascii.hexlify(bluetooth.resolve_adv_data(adv.data, Bluetooth.ADV_MANUFACTURER_DATA)) == str.encode(beaconId)):
                        print("you have arrived")
                        time.sleep(10)
                        break
                    else:
                        if ubinascii.hexlify(bluetooth.resolve_adv_data(adv.data, Bluetooth.ADV_MANUFACTURER_DATA)) in dangerAreas:
                            #p_out.value(1)
                            #time.sleep(2)
                            #p_out.value(0)
                            #time.sleep(2)#vibrate disk motor
                            print("Danger Zone",ubinascii.hexlify(adv.mac))
                            print("Data of danger zone device", ubinascii.hexlify(bluetooth.resolve_adv_data(adv.data, Bluetooth.ADV_MANUFACTURER_DATA)))
                        else:
                            print("Mac address of bluetooth device",ubinascii.hexlify(adv.mac))
    else:
        print("No beaconId")


#beaconId = value["bluetoothId"]
#print(beaconId)

dangerAreas = [str.encode("590002150112233445566778899aabbccddeeff03c6a79d1bb"), str.encode("fe0cdd907caa"), str.encode("e9823c6a79d1"), str.encode("f12b88072c42"), str.encode("0734226743c5"), str.encode("42cdb5309d8e")]
beaconId = ""
wifiConnect()
beaconId = readDestination()
checkId()
