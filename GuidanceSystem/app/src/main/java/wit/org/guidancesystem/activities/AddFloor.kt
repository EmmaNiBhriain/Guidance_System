package wit.org.guidancesystem.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.*
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.Button
import kotlinx.android.synthetic.main.add_floor.*
import kotlinx.android.synthetic.main.building_square.*
import wit.org.guidancesystem.R
import wit.org.guidancesystem.R.color.colorAccent
import wit.org.guidancesystem.models.AreaType
import wit.org.guidancesystem.models.Metre
import java.text.FieldPosition
import android.widget.Toast
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import wit.org.guidancesystem.models.BuildingModel
import kotlin.math.floor


class AddFloor : AppCompatActivity(){

    var metres = ArrayList<Metre>()

    var adapter:FloorAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_floor)

        populateGrid()

        adapter = FloorAdapter(this, metres)
        floorLayout.adapter = adapter

        floorLayout.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val item = (view.findViewById(R.id.metreSquare) as TextView).text.toString()
        };
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
        AlertDialog.Builder(this).setTitle("Add Floor")
            .setMessage("Please confirm that your layout can be saved")
            .setPositiveButton("OK"){dialog, which ->
                val ref = FirebaseDatabase.getInstance().getReference("Buildings")

                val buildingid = ref.push().key
                val name = "Test Floor"
                val building= BuildingModel(buildingid!!, name, metres)

                ref.child(buildingid).setValue(building).addOnCompleteListener{
                    Toast.makeText(applicationContext, "Building saved successfully", Toast.LENGTH_LONG).show()
                }

                intent = Intent(this, AdminHome::class.java)
                startActivity(intent)
            }
            .show()
    }

    private fun populateGrid(){
        for (i in 10 downTo 1){
            for(j in 1..10){
                var new = Metre(AreaType.OTHER, j, i)
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
                if(metre.type == AreaType.OTHER){
                    metreSquare.setBackgroundResource(R.color.colorAccent)
                    metre.type = AreaType.CORRIDOR
                }
                else if(metre.type == AreaType.CORRIDOR){
                    metreSquare.setBackgroundResource(R.color.colorPrimary)
                    metre.type = AreaType.WALL
                }
                else if(metre.type == AreaType.WALL){
                    metreSquare.setBackgroundResource(R.color.colorBlack)
                    metre.type = AreaType.ROOM
                }
                else{
                    metreSquare.setBackgroundResource(R.color.colorGrey)
                    metre.type = AreaType.OTHER
                }

                info {
                    "!!!Coords: " + metre.xCoOrd.toString() + " " + metre.yCoOrd.toString()
                }

            }
            return floorView
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

    }
}
