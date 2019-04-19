package wit.org.guidancesystem

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_room_graph.*
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.jjoe64.graphview.series.PointsGraphSeries
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.helper.StaticLabelsFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import org.jetbrains.anko.info
import wit.org.guidancesystem.main.MainApp
import wit.org.guidancesystem.models.Metre
import java.text.SimpleDateFormat
import java.util.*


class RoomGraph : Base(), AdapterView.OnItemSelectedListener {

    lateinit var app:MainApp
    var irishDateFormat = SimpleDateFormat("d/M/yyyy", Locale.ENGLISH)

    var dateMap = HashMap<String, ArrayList<Metre>>()
    var frequency = HashMap<Metre, Int>()
    var list_of_items = ArrayList<String>()
    var nameOfRoom = ""
    var datapoint = DataPoint(0.0,0.0)
    var weeklyData = arrayOf(DataPoint(0.0,0.0),DataPoint(1.0,0.0),DataPoint(2.0,0.0),DataPoint(3.0,0.0),DataPoint(4.0,0.0),DataPoint(5.0,0.0),DataPoint(5.0,0.0));
    var weeklyDates = arrayOf("","","","","","","");



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_graph)
        setSupportActionBar(toolbar)

        app = application as MainApp


        //Get the list of rooms that the user has visited
        for (d in app.destinations.findAll())
            list_of_items.add(d.name)


        //mapDateToVisit()

        //get the date for the last 7 days
        //generateGraphPoints()


        //set up the spinner to display the list of rooms
        spinner!!.setOnItemSelectedListener(this)

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_items)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.setAdapter(aa)

    }

    fun createGraph(){

        val graph = findViewById(R.id.roomgraph) as GraphView
        val series = LineGraphSeries<DataPoint>(
            weeklyData
        )
        for(i in weeklyData){
            info { "!!!" + i.x + i.y }
        }
        graph.removeAllSeries()
        graph.addSeries(series)
        //series.setShape(PointsGraphSeries.Shape.POINT)

        series.setColor(Color.rgb(
            140, 70, 168
        ))

        //Set the ranges of the axes
        graph.getViewport().setMinX(0.0);
        graph.getViewport().setMinY(0.0);

        graph.getViewport().setYAxisBoundsManual(true);

        val staticLabelsFormatter = StaticLabelsFormatter(graph)
        staticLabelsFormatter.setHorizontalLabels(weeklyDates)
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        graph.getGridLabelRenderer().setVerticalAxisTitle("Number of Visits");

    }


    fun generateGraphPoints(){
        for(i in 0..6){
            var pastDate = getDate(i)

            if(dateMap.containsKey(pastDate)) {
                var roomsOnThisDay = dateMap.get(pastDate) //get the arraylist of rooms visited on this day of the chosen name

                var timesVisitedToday = roomsOnThisDay!!.size

                weeklyData[i] = DataPoint(i.toDouble(), timesVisitedToday.toDouble())
                info { "!!! Date " +  i + timesVisitedToday}

            }
            else{
                weeklyData[i] = DataPoint(i.toDouble(), 0.0)

            }
            weeklyDates[i] = pastDate.substring(0,pastDate.length - 5)

        }
    }

    //Take a parameter of the number of days ago and return the date of that day
    fun getDate(numDaysAgo:Int):String{
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -numDaysAgo)

        return irishDateFormat.format(calendar.time)
    }

    fun mapDateToVisit(){

        for (r in app.destinations.findAll()){
            if((r.visited)&&(r.name == nameOfRoom)){
                if(dateMap.containsKey(r.date)){ //if the date is already a key, add m to the arraylist of that key
                    var rooms = dateMap.get(r.date)
                    rooms!!.add(r)
                    dateMap[r.date] = rooms
                }
                else{ //if the date is not already used, add it to the hashmap
                    var rooms = ArrayList<Metre>()
                    rooms.add(r)
                    dateMap[r.date] = rooms
                }
            }
            info{"!!! Room" + r.name + " date" + r.date}

        }

    }




    override fun onItemSelected(adapter: AdapterView<*>, arg1: View, position: Int, id: Long) {

        weeklyData = arrayOf(DataPoint(0.0,0.0),DataPoint(1.0,0.0),DataPoint(2.0,0.0),DataPoint(3.0,0.0),DataPoint(4.0,0.0),DataPoint(5.0,0.0),DataPoint(5.0,0.0));

        dateMap = HashMap<String, ArrayList<Metre>>()
        // use position to know the selected item
        nameOfRoom = adapter.getItemAtPosition(position).toString()
        info{"!!!" + nameOfRoom}

        mapDateToVisit()

        for (i in dateMap){
            info {"!!! room for each date" + i.value.get(0).name }
        }

        //get the date for the last 7 days
        generateGraphPoints()

        info { "!!! points generated"  }

        createGraph()
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

}
