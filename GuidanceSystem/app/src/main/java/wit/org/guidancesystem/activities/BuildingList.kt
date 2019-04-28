package wit.org.guidancesystem.activities

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_building.*
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import wit.org.guidancesystem.R
import wit.org.guidancesystem.main.MainApp
import wit.org.guidancesystem.models.BuildingModel

/**
 * Display a list of buildings that a user has been registered for
 */
class BuildingList : Base(), BuildingListener {

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
