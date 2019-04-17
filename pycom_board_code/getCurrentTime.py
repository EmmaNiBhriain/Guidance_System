#################################################
#                Connect to WiFi                #
#################################################

from network import WLAN
import machine
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

#wifiConnect()
#setRTCLocalTime()

utime.timezone(3600) #set the time zone to gmt+1
localtime = utime.localtime()
print('Adjusted to GMT +1 timezone', localtime, '\n')
todayDate = str(localtime[2]) + "/" + str(localtime[1]) + "/" + str(localtime[0])
nowTime = str(localtime[3]) + ":" + str(localtime[4])
print(todayDate)
print(nowTime)
