package wit.org.guidancesystem.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.card_user.*
import wit.org.guidancesystem.Base
import wit.org.guidancesystem.R
import wit.org.guidancesystem.RoomGraph
import wit.org.guidancesystem.Stats
import wit.org.guidancesystem.main.MainApp
import wit.org.guidancesystem.models.BuildingModel
import com.google.firebase.internal.FirebaseAppHelper.getUid
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.info


class AdminHome : Base() {

    lateinit var db:DatabaseReference

    lateinit var app:MainApp

    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(R.layout.admin_home)

        super.onCreate(savedInstanceState)

        app = application as MainApp

        getUsers()

    }

    /**
     *
     * Retrieve the e-mail address of every user by retrieving the root parent of each entry in Firebase database
     *
     */
    fun getUsers(){
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach{
                    info{"!!!NewMessage " + it.key}
                    app.users.add(it.key!!)

                }
                info{"!!!Number of users: " + app.users.size}

            }

        })

    }

    /**
     * Display a list of users that can be selected to add a building for.
     */
    fun addBuilding(view: View){
        info{"!!!Size of users " + app.users.size}
        intent = Intent(this, ListUsers::class.java)
        startActivity(intent)
    }


    override fun onBackPressed() {
    }//do nothing
}
