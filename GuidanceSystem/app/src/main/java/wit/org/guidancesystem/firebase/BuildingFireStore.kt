package wit.org.guidancesystem.firebase

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import wit.org.guidancesystem.models.BuildingModel
import wit.org.guidancesystem.models.Metre

/**
 * Control communication with Building objects stored in Firebase Realtime Database
 */
class BuildingFireStore(val context: Context):AnkoLogger {

    val buildings = ArrayList<BuildingModel>()
    lateinit var userId:String
    lateinit var db:DatabaseReference
    lateinit var userEmail:String


    fun findAll():List<BuildingModel>{
        return buildings
    }

    fun clear():Unit{
        return buildings.clear()
    }


    fun create(targetEmail:String, building:BuildingModel){
        //var emailId = decodeUserEmail(targetEmail)
        val key = db.child("users").child(targetEmail).child("Buildings").push().key
        building.id = key!!
        info { key }
        buildings.add(building)

        db.child("users").child(targetEmail).child("Buildings").child(key).setValue(building)

        val testData = db.child("users").child(targetEmail).child("Buildings").child("test")

        if(testData != null){
            testData.removeValue()
        }
        else{
            //do nothing
        }

    }

    fun update(building:BuildingModel) {
        var foundBuilding: BuildingModel? = buildings.find { b -> b.id == building.id }

        if (foundBuilding != null) {
            foundBuilding.name = building.name
            foundBuilding.rooms = building.rooms
        }

        db.child("users").child(userId).child("Buildings").child(building.id).setValue(building)
    }

    fun delete(building: BuildingModel){
        db.child("users").child(userId).child("buildings").child(building.id).removeValue()
        buildings.remove(building)
    }


    fun fetchBuildings(buildingsReady:()->Unit){
        val valueEventListener = object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.mapNotNullTo(buildings){it.getValue<BuildingModel>(BuildingModel::class.java)}
                buildingsReady()
            }
        }

        userId = FirebaseAuth.getInstance().currentUser!!.uid
        userEmail = encodeUserEmail(FirebaseAuth.getInstance().currentUser!!.email!!)
        info{"!!!  Email" + userEmail}
        db = FirebaseDatabase.getInstance().reference
        buildings.clear()
        db.child("users").child(userEmail).child("Buildings").addListenerForSingleValueEvent(valueEventListener)

        info { "!!!! " + buildings.size+ userEmail }
    }

    fun encodeUserEmail(userEmail: String): String {
        return userEmail.replace(".", ",")
    }




}