package wit.org.guidancesystem.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import wit.org.guidancesystem.Base
import wit.org.guidancesystem.R
import wit.org.guidancesystem.RoomGraph
import wit.org.guidancesystem.Stats

class AdminHome : Base() {

    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(R.layout.admin_home)

        super.onCreate(savedInstanceState)

    }

    fun addBuilding(view: View){
        intent = Intent(this, AddFloor::class.java)
        startActivity(intent)
    }

    fun viewStats(view:View){
        intent = Intent(this, Stats::class.java)
        startActivity(intent)
    }

    fun viewRoomStats(view:View){
        intent = Intent(this, RoomGraph::class.java)
        startActivity(intent)
    }
}
