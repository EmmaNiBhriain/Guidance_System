import ubinascii
import time
from network import Bluetooth
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
            if(ubinascii.hexlify(mfg_data) == str.encode("060001092000271de0e9092203c1d6db60d7e30d53bfd0f698597376c4")):
                print("You are in the kitchen")
                time.sleep(10)
            elif(ubinascii.hexlify(mfg_data)== str.encode("06000109200222af248ee980c6e856e5095e06ddadf5dcef7ff47b5e05")):
                print("You are in the bathroom")
                time.sleep(10)
            else:
                print(ubinascii.hexlify(mfg_data))
