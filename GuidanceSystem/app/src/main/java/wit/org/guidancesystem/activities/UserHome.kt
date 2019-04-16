package wit.org.guidancesystem.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import wit.org.guidancesystem.*

class UserHome : Base() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home)
    }

    fun showBuildings(view: View){
        intent = Intent(this, BuildingActivity::class.java)
        startActivity(intent)
    }

    fun viewStats(view:View){
        intent = Intent(this, Stats::class.java)
        startActivity(intent)
    }

    fun viewRoomStats(view: View){
        intent = Intent(this, RoomGraph::class.java)
        startActivity(intent)
    }
}
