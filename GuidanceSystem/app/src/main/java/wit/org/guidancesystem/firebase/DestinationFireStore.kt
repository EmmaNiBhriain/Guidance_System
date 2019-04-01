package wit.org.guidancesystem.firebase

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import wit.org.guidancesystem.models.BuildingModel
import wit.org.guidancesystem.models.Metre

class DestinationFireStore (val context: Context): AnkoLogger {

    val destinations = ArrayList<Metre>()

    lateinit var userId:String
    lateinit var db: DatabaseReference

    fun findAll():List<Metre>{
        return destinations
    }

    fun fetchDestinations(destinationsReady:()->Unit){
        val valueEventListener = object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.mapNotNullTo(destinations){it.getValue<Metre>(Metre::class.java)}
                destinationsReady()
            }
        }

        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        destinations.clear()
        db.child("users").child("EMCJUDYRJKZlouOnzjGVfxpQTWR2").child("Destination").addListenerForSingleValueEvent(valueEventListener)
    }
}