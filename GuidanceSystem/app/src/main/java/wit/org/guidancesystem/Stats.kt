package wit.org.guidancesystem

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.View
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_stats.*
import com.jjoe64.graphview.series.LineGraphSeries
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.ValueDependentColor
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.helper.StaticLabelsFormatter
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import wit.org.guidancesystem.main.MainApp
import wit.org.guidancesystem.models.Metre
import java.text.SimpleDateFormat
import java.util.*


class Stats : Base(), AnkoLogger {

    lateinit var app: MainApp
    var customFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)

    var selectedDate = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        setSupportActionBar(toolbar)

        app = application as MainApp
        info{"!!! " + app.destinations.findAll().size}

        //frequency in a week TODO limit it to a week
        var frequency = HashMap<Metre, Int>()

        for (m in app.destinations.findAll()){
            if(m.visited ){
                var current = frequency.get(m)
                frequency.put(m, if (current == null) 1 else current!! + 1);
            }
        }

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
        val series =  BarGraphSeries<DataPoint>(arrayOfPoints);
        graph.addSeries(series);


        //Add labels on the x axis

        var labels = ArrayList<String>()

        labels.add("")

        for (pair in frequency){
            labels.add(pair.key.name)
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

        info{"!!!! Max y " + graph.getViewport().getMaxY(true)}
        graph.getViewport().setYAxisBoundsManual(true);


        // styling
        series.setValueDependentColor { data ->

            Color.rgb(
                90 + data.y.toInt() * 10, 70, 168
            )
        }

        series.spacing = 50

// draw values on top
        series.isDrawValuesOnTop = false
        series.valuesOnTopColor = Color.RED
    }


    fun showCalendar(view: View){
        val now = Calendar.getInstance()
        info{"!!!Date picker launching"}
        val datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                //val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, day)
                val date = customFormat.format(selectedDate.time)
                Toast.makeText(this, "date: " + date, Toast.LENGTH_SHORT).show()
                dateLabel.setText(customFormat.format(selectedDate.time))

            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

}
