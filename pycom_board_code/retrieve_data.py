import ufirebase as firebase
import ubinascii
import time
from network import Bluetooth


#################################################
#        Retrieve Beacon ID of destination      #
#################################################

URL = 'guidancesystem-f0136'  #name of database
destination = firebase.get(URL+'/users/EMCJUDYRJKZlouOnzjGVfxpQTWR2/Destination')

value = next(iter(destination.values()))
print(value)

beaconId = value["bluetoothId"]
print(beaconId)
