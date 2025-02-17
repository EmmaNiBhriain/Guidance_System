package wit.org.guidancesystem.firebase

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import wit.org.guidancesystem.models.BuildingModel
import wit.org.guidancesystem.models.Metre

/**
 * Control communication with Destinations stored in Firebase Realtime Database
 */
class DestinationFireStore (val context: Context): AnkoLogger {

    val destinations = ArrayList<Metre>()

    lateinit var userId:String
    lateinit var userEmail:String
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
        userEmail = encodeUserEmail(FirebaseAuth.getInstance().currentUser!!.email!!)
        destinations.clear()
        db.child("users").child(userEmail).child("Destination").addListenerForSingleValueEvent(valueEventListener)
    }

    fun encodeUserEmail(userEmail: String): String {
        return userEmail.replace(".", ",")
    }
}