package wit.org.guidancesystem

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.anko.info

import kotlinx.android.synthetic.main.activity_add_building.*
import org.jetbrains.anko.toast
import org.jetbrains.anko.*
import wit.org.guidancesystem.main.MainApp
import wit.org.guidancesystem.models.BuildingModel

class AddBuilding : Base(){

    var building = BuildingModel()
    var rooms = arrayListOf<String>()
    lateinit var app:MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_building)
        app = application as MainApp

        toolbarAdd.title = "Building"
        setSupportActionBar(toolbarAdd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        createBuilding.setOnClickListener {

            building.name = buildingName.text.toString()
            building.rooms = rooms

            if(building.name.isEmpty()){
                toast("Please enter name of building")
            }
            else{
                info{"Saving building"}
                createBuildings(building.name, building.rooms)

                //rooms.addAll(listOf("room1","room2"))
            }

        }

    }

    private fun createBuildings(name:String, rooms:ArrayList<String>){
        building.rooms.addAll(listOf("room1","room2"))
        info{building.rooms.size}
        app.buildings.add(building)

        info{"!! Number of buildings " + app.buildings.size}


        val ref = FirebaseDatabase.getInstance().getReference("Buildings")

        val buildingid = ref.push().key
        val building= BuildingModel(buildingid!!, name, rooms)

        ref.child(buildingid).setValue(building).addOnCompleteListener{
            Toast.makeText(applicationContext, "Building saved successfully", Toast.LENGTH_LONG).show()
        }
    }


}
