import ubinascii
import time
from network import Bluetooth
ids = []
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
            if(ubinascii.hexlify(mfg_data) == str.encode("590002150112233445566778899aabbccddeeff0dd907baabb")):
                print("you have arrived",ubinascii.hexlify(mfg_data))
                break
                #time.sleep(10)
            else:
                if ubinascii.hexlify(mfg_data) not in ids:
                    ids.append(ubinascii.hexlify(mfg_data))
                    print(ubinascii.hexlify(mfg_data), " strength ", adv.rssi)
±Á(ù€7