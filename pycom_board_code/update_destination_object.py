import ufirebase as firebase
from network import WLAN
import machine
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

utime.timezone(3600) #set the time zone to gmt+1
localtime = utime.localtime()
todayDate = str(localtime[2]) + "/" + str(localtime[1]) + "/" + str(localtime[0])
nowTime = str(localtime[3]) + ":" + str(localtime[4])
print(todayDate)
print(nowTime)

def updateFirebase():
    try:
        URL = 'guidancesystem-f0136'  #name of database
        destination = firebase.get(URL+'/users/007eob@gmail,com/Destination')
        print(destination)
        value = next(iter(destination.values())) #most recent destination object
        print(value)
        fbID =  value["fbId"]#next(iter(value.values())) #bluetooth Id of most recent destination object
        print(fbID)
        path = URL+'/users/007eob@gmail,com/Destination/' + fbID
        print(path)
        destination = firebase.patch(path, {"visited": True, "date":todayDate, "time":nowTime})
    except:
        print("Not connected to WiFI, reconnecting now")
        wifiConnect()
        print("Retrying write to firebase")
        updateFirebase()

updateFirebase()
