
#################################################
#          Retrieve database data              #
#################################################
import ufirebase as firebase

URL = 'guidancesystem-f0136'  #name of database
destination = firebase.get(URL+'/users/EMCJUDYRJKZlouOnzjGVfxpQTWR2/Destination')


#destination = {'-LaggIDDLvglWKNRQc4J': {'xcoOrd': 6, 'bluetoothId': 'geyshx', 'name': 'garden', 'ycoOrd': 6, 'type': 'DOOR'}}

value = next(iter(destination.values()))
print(value)

beaconId = value["bluetoothId"]
print(beaconId)
