
#################################################
#          Retrieve database data              #
#################################################
import ufirebase as firebase

URL = 'guidancesystem-f0136'  #name of database
destination = firebase.get(URL+'/users/EMCJUDYRJKZlouOnzjGVfxpQTWR2/Destination')
