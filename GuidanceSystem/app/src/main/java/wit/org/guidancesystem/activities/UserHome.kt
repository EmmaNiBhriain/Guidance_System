package wit.org.guidancesystem.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import org.jetbrains.anko.info
import wit.org.guidancesystem.*
import wit.org.guidancesystem.main.MainApp

/**
 * Display home screen for a user when they log in
 */
class UserHome : Base() {

    lateinit var app:MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home)

        app = application as MainApp
    }

    /**
     * Functions called when buttons are pressed in the layout view
     */
    fun showBuildings(view: View){
        if(app.buildings.findAll().get(0).id == ""){
            Toast.makeText(this,"No Buildings to Display", Toast.LENGTH_SHORT).show()
        }
        else{
            intent = Intent(this, BuildingList::class.java)
            startActivity(intent)
        }

    }

    fun viewStats(view:View){
        intent = Intent(this, Stats::class.java)
        startActivity(intent)
    }

    fun viewRoomStats(view: View){
        intent = Intent(this, RoomGraph::class.java)
        startActivity(intent)
    }

    //Cannot redisplay the login screen using the back button
    override fun onBackPressed() {
        //do nothing
    }
}
