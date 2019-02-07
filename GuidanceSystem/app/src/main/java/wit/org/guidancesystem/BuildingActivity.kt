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
import org.jetbrains.anko.intentFor
import wit.org.guidancesystem.main.MainApp
import wit.org.guidancesystem.models.BuildingModel

class BuildingActivity : AppCompatActivity(), BuildingListener {

    lateinit var app:MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = BuildingAdapter(app.buildings, this)

        addBuilding.setOnClickListener {
            intent = Intent(this, AddBuilding::class.java)
            startActivityForResult(intent,0)
        }

        addRoom.setOnClickListener {
            intent = Intent(this, Room::class.java)
            startActivityForResult(intent, 0)
        }
    }



    override fun onBuildingClick(building: BuildingModel) {
        startActivityForResult(intentFor<AddBuilding>().putExtra("building_edit", building), 0)    }
}
