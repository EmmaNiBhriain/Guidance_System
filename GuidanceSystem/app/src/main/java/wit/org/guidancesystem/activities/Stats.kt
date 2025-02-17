package wit.org.guidancesystem.activities

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_stats.*
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.helper.StaticLabelsFormatter
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import wit.org.guidancesystem.R
import wit.org.guidancesystem.main.MainApp
import wit.org.guidancesystem.models.Metre
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Display barcharts of where a user has been on a particular day
 */

class Stats : Base(), AnkoLogger {

    lateinit var app: MainApp
    var customFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH) //format for label display
    var irishDateFormat = SimpleDateFormat("d/M/yyyy", Locale.ENGLISH) //format for use in database

    var selectedDate = Calendar.getInstance() //date chosen in the calendar object
    var dateMap = HashMap<String, ArrayList<Metre>>() //hashmap with a date as a key and all rooms visited on that day as a value
    var frequency = HashMap<String, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        setSupportActionBar(toolbar)

        app = application as MainApp
        info{"!!! " + app.destinations.findAll().size}

        //populate datemap
        for (m in app.destinations.findAll()){
            if(m.visited ){
                if(dateMap.containsKey(m.date)){ //if the date is already a key, add m to the arraylist of that key
                    var rooms = dateMap.get(m.date)
                    rooms!!.add(m)
                    dateMap[m.date] = rooms
                }
                else{ //if the date is not already used, add it to the hashmap
                    var rooms = ArrayList<Metre>()
                    rooms.add(m)
                    dateMap[m.date] = rooms
                }
            }
        }

        for (date in dateMap.keys) {
            val key = date
            val value = dateMap.get(date).toString()
            println("!!!" + key + " " + value)
        }

        createGraph()
        updateGraph(irishDateFormat.format(selectedDate.time))
    }

    /**
     * Read date selected and get the rooms visited on that day
     * Create datapoints using the rooms and frequency visited
     */
    fun createGraph(){
        dateLabel.setText(customFormat.format(selectedDate.time))

        var points = ArrayList<DataPoint>()

        points.add(DataPoint(0.0,0.0))
        var i = 1
        for (pair in frequency){
            points.add(DataPoint(i.toDouble(), pair.value.toDouble()))
            i++
        }
        points.add(DataPoint(i.toDouble(),0.0))


        for (ent in frequency.entries) {
            info{"Element " + ent.key + " occurs " + ent.value + " times"}

        }
        val arrayOfPoints = arrayOfNulls<DataPoint>(points.size)
        points.toArray(arrayOfPoints)

        info { "!!! points" + arrayOfPoints.size }

        val graph = findViewById(R.id.usagegraph) as GraphView

        graph.removeAllSeries()
        val series =  BarGraphSeries<DataPoint>(arrayOfPoints);
        graph.addSeries(series);


        //Add labels on the x axis

        var labels = ArrayList<String>()

        labels.add("")

        for (pair in frequency){
            labels.add(pair.key)
        }
        labels.add("")

        val arrayOfLabels = arrayOfNulls<String>(labels.size)
        labels.toArray(arrayOfLabels)

        val staticLabelsFormatter = StaticLabelsFormatter(graph)
        staticLabelsFormatter.setHorizontalLabels(arrayOfLabels)
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        graph.getGridLabelRenderer().setVerticalAxisTitle("Number of Visits");

        //Set the ranges of the axes
        graph.getViewport().setMinX(0.0);
        graph.getViewport().setMinY(0.0);
        graph.getViewport().setMaxY(12.0);

        info{"!!!! Max y " + graph.getViewport().getMaxY(true)}
        graph.getViewport().setYAxisBoundsManual(true);


        // styling
        series.setValueDependentColor { data ->

            Color.rgb(
                90 + data.y.toInt() * 10, 70, 168
            )
        }

        series.spacing = 50

        series.isDrawValuesOnTop = false
    }

    /**
     * Display the calenday
     */
    fun showCalendar(view: View){
        val now = Calendar.getInstance()
        info{"!!!Date picker launching"}
        val datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, day)
                val date = customFormat.format(selectedDate.time)
                Toast.makeText(this, "date: " + date, Toast.LENGTH_SHORT).show()
                updateGraph(irishDateFormat.format(selectedDate.time))

            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    //for each room in this date, get the frequency visited
    fun updateGraph(date:String){
        frequency = HashMap<String, Int>()
        if(dateMap.containsKey(date)){
            var visitedRooms = dateMap.get(date)

            for( r in visitedRooms!!){
                var current = frequency.get(r.name)
                frequency.put(r.name, if (current == null) 1 else current!! + 1);
            }

            createGraph()

        }
        else{
            Toast.makeText(this, "No rooms are recorded for this date" + date, Toast.LENGTH_SHORT).show()
        }
    }

}
