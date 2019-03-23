package wit.org.guidancesystem.activities

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import wit.org.guidancesystem.R

import kotlinx.android.synthetic.main.activity_destination_menu.*
import kotlinx.android.synthetic.main.room_item.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import wit.org.guidancesystem.main.MainApp
import wit.org.guidancesystem.models.AreaType
import wit.org.guidancesystem.models.BuildingModel
import wit.org.guidancesystem.models.Metre

class DestinationMenu : AppCompatActivity(), AnkoLogger {

    var rooms = ArrayList<Metre>()

    var building = BuildingModel()
    lateinit var app: MainApp

    var adapter:DestinationAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination_menu)
        setSupportActionBar(toolbar)

        if(intent.hasExtra("building_view")){
            building = intent.extras.getParcelable<BuildingModel>("building_view")
            info{"Building name " + building.name}

            for( i in building.rooms){
                if(i.type == AreaType.DOOR){
                    rooms.add(i)
                }
            }
        }

        info{"!!!Rooms " +rooms.size }

        adapter = DestinationAdapter(this, rooms)
        availableRooms.adapter = adapter
        app = application as MainApp

    }



    class DestinationAdapter: BaseAdapter, AnkoLogger {
        var roomList = ArrayList<Metre>()

        var context: Context? = null
        constructor(context: Context, roomList: ArrayList<Metre>):super(){
            this.context = context
            this.roomList = roomList
        }

        override fun getCount():Int{
            return roomList.size
        }

        override fun getItem(position: Int):Any{
            return roomList[position]
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val room = this.roomList[position]

            var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var roomView = inflator.inflate(R.layout.room_item, null);


            //var roomItem = roomView.findViewById(R.id.room_square) as TextView

            roomView.room_square.text = room.name
            info{"!!!Room name " + room.name}

            roomView.room_square.setOnClickListener {
                info{"!!Room selected is " + room.name}

                var userId = FirebaseAuth.getInstance().currentUser!!.uid
                val ref = FirebaseDatabase.getInstance().getReference("users").child(userId).child("Destination")

                val destinationid = ref.push().key
                val destination = room

                ref.child(destinationid!!).setValue(destination).addOnCompleteListener{
                    Toast.makeText(context, "Destination saved successfully", Toast.LENGTH_LONG).show()
                }

            }

            return roomView
        }





        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

    }

}
