import ufirebase as firebase
import ubinascii
import time
from network import Bluetooth


#################################################
#        Retrieve Beacon ID of destination      #
#################################################
beaconId = ""

URL = 'guidancesystem-f0136'  #name of database
destination = firebase.get(URL+'/users/EMCJUDYRJKZlouOnzjGVfxpQTWR2/Destination')

value = next(iter(destination.values()))
print(value)

beaconId = value["bluetoothId"]
print(beaconId)



if(beaconId!=""):
    bluetooth = Bluetooth()
    bluetooth.start_scan(20)
    while bluetooth.isscanning():
        adv = bluetooth.get_adv()
        if adv:
            # try to get the complete name
            #print(bluetooth.resolve_adv_data(adv.data, Bluetooth.ADV_NAME_CMPL))

            mfg_data = bluetooth.resolve_adv_data(adv.data, Bluetooth.ADV_MANUFACTURER_DATA)

            if mfg_data:
                # try to get the manufacturer data (Apple's iBeacon data is sent here)
                #print(ubinascii.hexlify(mfg_data))
                if(ubinascii.hexlify(adv.mac) == str.encode("70bca02823b7")):
                    print("you have arrived")
                    time.sleep(10)
                    break
                else:
                    print(ubinascii.hexlify(mfg_data))
else:
    print("No beaconId")
