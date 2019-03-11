package wit.org.guidancesystem.activities

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.add_floor.*
import kotlinx.android.synthetic.main.building_square.*
import wit.org.guidancesystem.R
import wit.org.guidancesystem.R.color.colorAccent
import wit.org.guidancesystem.models.AreaType
import wit.org.guidancesystem.models.Metre
import java.text.FieldPosition

class AddFloor : AppCompatActivity() {

    var metres = ArrayList<Metre>()

    var adapter:FloorAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_floor)

        populateGrid()

        adapter = FloorAdapter(this, metres)
        floorLayout.adapter = adapter
    }

    private fun populateGrid(){
        for (i in 1..100){
            var new = Metre(i, AreaType.OTHER)
            metres.add(new)
        }
    }

    @SuppressLint("ResourceAsColor")
    fun changeType(view:View){
        metreSquare.setBackgroundColor(R.color.colorAccent)
    }



    class FloorAdapter:BaseAdapter{
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
            var floorView = inflator.inflate(R.layout.building_square, null)

            return floorView
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

    }
}
