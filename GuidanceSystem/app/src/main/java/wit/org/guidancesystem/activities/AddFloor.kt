package wit.org.guidancesystem.activities

import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.*
import android.widget.*
import kotlinx.android.synthetic.main.add_floor.*
import kotlinx.android.synthetic.main.building_square.*
import wit.org.guidancesystem.R
import wit.org.guidancesystem.R.color.colorAccent
import wit.org.guidancesystem.models.AreaType
import wit.org.guidancesystem.models.Metre
import java.text.FieldPosition
import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import wit.org.guidancesystem.main.MainApp
import wit.org.guidancesystem.models.BuildingModel
import kotlin.math.floor

class AddFloor : AppCompatActivity(), AnkoLogger{

    var metres = ArrayList<Metre>()
    lateinit var app: MainApp

    var adapter:FloorAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_floor)

        populateGrid()

        adapter = FloorAdapter(this, metres)
        floorLayout.adapter = adapter
        app = application as MainApp



       // floorLayout.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
         //   val item = (view.findViewById(R.id.metreSquare) as TextView).text.toString()
        //};



    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.add_floor_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.item_confirm ->{
                menu_confirm()

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun menu_confirm(){
        val floorName = EditText(this)

        AlertDialog.Builder(this).setTitle("Add Floor")
            .setMessage("Please confirm that your layout can be saved")
            .setView(floorName)
            .setPositiveButton("OK"){dialog, which ->

                var nameOfFloor = floorName.getText().toString();

                if(nameOfFloor != ""){


                    var building = BuildingModel()
                    building.name = nameOfFloor
                    building.rooms = metres
                    app.buildings.create(building)

                    intent = Intent(this, AdminHome::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(applicationContext, "Please enter a valid name", Toast.LENGTH_SHORT).show()
                }

            }
            .show()
    }

    private fun populateGrid(){
        for (i in 10 downTo 1){
            for(j in 1..10){
                var new = Metre(AreaType.CORRIDOR, "", j, i)
                metres.add(new)
            }

        }
    }

    @SuppressLint("ResourceAsColor")
    fun changeType(view:View){
        metreSquare.setBackgroundColor(R.color.colorAccent)
    }

    class FloorAdapter:BaseAdapter, AnkoLogger{
        var metreList = ArrayList<Metre>()

        var context: Context? = null
        constructor(context:Context, metreList: ArrayList<Metre>):super(){
            this.context = context
            this.metreList = metreList
        }

        override fun getCount():Int{
            return metreList.size
        }

        override fun getItem(position: Int):Any{
            return metreList[position]
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val metre = this.metreList[position]

            var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var floorView = inflator.inflate(R.layout.building_square, null);


            var metreSquare = floorView.findViewById(R.id.metreSquare) as Button

            metreSquare.setOnClickListener {
                if(metre.type == AreaType.CORRIDOR){ //make it a wall
                    metreSquare.setBackgroundResource(R.color.colorWall)
                    metre.type = AreaType.WALL
                }
                else if(metre.type == AreaType.WALL){ //make it a door
                    metreSquare.setBackgroundResource(R.color.colorDoor)
                    metre.type = AreaType.DOOR
                }
                else if(metre.type == AreaType.DOOR){ //make it a room
                    metreSquare.setBackgroundResource(R.color.colorRoom)
                    metre.type = AreaType.ROOM
                }
                else if(metre.type == AreaType.ROOM){ //make it a danger area
                    metreSquare.setBackgroundResource(R.color.colorDanger)
                    metre.type = AreaType.DANGER
                }
                else{
                    metreSquare.setBackgroundResource(R.color.colorCorridor)
                    metre.type = AreaType.CORRIDOR
                }

                info {
                    "!!!Coords: " + metre.xCoOrd.toString() + " " + metre.yCoOrd.toString()
                }

            }

            metreSquare.setOnLongClickListener{

                if(metre.type == AreaType.DOOR){
                    var roomName = EditText(context)
                    roomName.setHint("Enter name of room")

                    var doorBluetoothId = EditText(context)
                    doorBluetoothId.setHint("Enter Bluetooth ID for this room")

                    var layout = LinearLayout(context)
                    layout.orientation = LinearLayout.VERTICAL

                    layout.addView(roomName)
                    layout.addView(doorBluetoothId)

                    AlertDialog.Builder(context as Context)
                        .setTitle("Room Name")
                        .setMessage("Enter a name for this room")
                        .setView(layout)
                        .setPositiveButton("OK"){dialog, which ->
                            var nameOfRoom = roomName.getText().toString();

                            if(nameOfRoom != ""){
                                metre.name = nameOfRoom

                                if(doorBluetoothId.getText().toString() != ""){
                                    metre.bluetoothId = doorBluetoothId.text.toString()
                                }
                                else{
                                    Toast.makeText(context, "Please enter a valid Bluetooth ID", Toast.LENGTH_SHORT).show()

                                }
                            }
                            else{
                                Toast.makeText(context, "Please enter a valid name", Toast.LENGTH_SHORT).show()
                            }

                        }
                        .show()
                }

                true
            }


            return floorView
        }





        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

    }
}
