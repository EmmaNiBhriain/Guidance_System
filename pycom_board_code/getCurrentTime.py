#################################################
#                Connect to WiFi                #
#################################################

from network import WLAN
from machine import RTC
import sys
import utime

def wifiConnect():
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



#################################################
#                Get Current Date and Time      #
#  Instructions from: MicroPython for the       #
#  Internet of Things                           #
#################################################
def setRTCLocalTime():
    rtc = RTC()
    print("Time before sync: ", rtc.now())
    rtc.ntp_sync("pool.ntp.org")
    while not rtc.synced():
        utime.sleep(1)
        print("Waiting for NTP server...")
    print('\nTime after sync: ', rtc.now())


utime.timezone(3600) #set the time zone to gmt+1
print('Adjusted to GMT +1 timezone', utime.localtime(), '\n')
#setRTCLocalTime()
