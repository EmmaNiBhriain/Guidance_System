import ufirebase as firebase
from network import WLAN
import machine


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


def updateFirebase():
    try:
        URL = 'guidancesystem-f0136'  #name of database
        destination = firebase.get(URL+'/users/007eob@gmail,com/Destination')
        print(destination)
        value = next(iter(destination.values())) #most recent destination object
        print(value)
        fbID =  next(iter(value.values())) #bluetooth Id of most recent destination object
        print(fbID)
        destination = firebase.patch(URL+'/users/007eob@gmail,com/Destination/' + fbID, {"visited": True})
    except:
        print("Not connected to WiFI, reconnecting now")
        wifiConnect()
        print("Retrying write to firebase")
        updateFirebase()

updateFirebase()
