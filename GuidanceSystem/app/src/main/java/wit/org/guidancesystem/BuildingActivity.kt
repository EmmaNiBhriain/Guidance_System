package wit.org.guidancesystem

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_building.*
import kotlinx.android.synthetic.main.card_building.view.*
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import wit.org.guidancesystem.activities.DestinationMenu
import wit.org.guidancesystem.main.MainApp
import wit.org.guidancesystem.models.BuildingModel

class BuildingActivity : Base(), BuildingListener {

    lateinit var app:MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building)
        app = application as MainApp
        info{"!!!" + app.destinations.findAll().size}

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = BuildingAdapter(app.buildings.findAll(), this)

    }



    override fun onBuildingClick(building: BuildingModel) {
        startActivity(intentFor<DestinationMenu>().putExtra("building_view", building))  }
}
