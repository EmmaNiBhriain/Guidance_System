package wit.org.guidancesystem.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import wit.org.guidancesystem.BuildingActivity
import wit.org.guidancesystem.R

class UserHome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home)
    }

    fun showBuildings(view: View){
        intent = Intent(this, BuildingActivity::class.java)
        startActivity(intent)
    }

    fun viewStats(view:View){

    }

    fun viewRoomStats(view: View){

    }
}
