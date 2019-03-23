package wit.org.guidancesystem.firebase

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import wit.org.guidancesystem.models.BuildingModel

class BuildingFireStore(val context: Context):AnkoLogger {

    val buildings = ArrayList<BuildingModel>()
    lateinit var userId:String
    lateinit var db:DatabaseReference

    fun findAll():List<BuildingModel>{
        return buildings
    }

    fun findById(id:String):BuildingModel?{
        val foundBuilding:BuildingModel?= buildings.find{b -> b.id == id}
        return foundBuilding
    }

    fun create(building:BuildingModel){
        val key = db.child("users").child("EMCJUDYRJKZlouOnzjGVfxpQTWR2").child("Buildings").push().key
        building.id = key!!
        buildings.add(building)
        db.child("users").child("EMCJUDYRJKZlouOnzjGVfxpQTWR2").child("Buildings").child(key).setValue(building)
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
        db = FirebaseDatabase.getInstance().reference
        buildings.clear()
        db.child("users").child(userId).child("Buildings").addListenerForSingleValueEvent(valueEventListener)
        info { "!!!! " + buildings.size+ userId }
    }
}