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
            if(ubinascii.hexlify(mfg_data) == str.encode("0600010920020c3244807035e647af85fb0ed5b7eda5d114f2374d65b0")):
                print("you have arrived")
                time.sleep(10)
            else:
                print(ubinascii.hexlify(mfg_data))
