#################################################
#                Connect to WiFi                #
#################################################

from network import WLAN
import machine
wlan = WLAN(mode=WLAN.STA)

nets = wlan.scan()
print(nets)
for net in nets:
    if net.ssid == 'hotspotdemo':
        print('Network found!')
        wlan.connect(net.ssid, auth=(net.sec, 'emmastestdemo'), timeout=5000)
        while not wlan.isconnected():
            machine.idle() # save power while waiting
        print('WLAN connection succeeded!')
        break
    else:
        print("Network not found")
