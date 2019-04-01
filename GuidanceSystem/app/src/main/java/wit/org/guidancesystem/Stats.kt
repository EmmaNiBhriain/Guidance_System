package wit.org.guidancesystem

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;

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


class Stats : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp

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

        var i = 0
        for (pair in frequency){
            points.add(DataPoint(i.toDouble(), pair.value.toDouble()))
            i++
        }


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

        for (pair in frequency){
            labels.add(pair.key.name)
        }

        val arrayOfLabels = arrayOfNulls<String>(labels.size)
        labels.toArray(arrayOfLabels)

        val staticLabelsFormatter = StaticLabelsFormatter(graph)
        staticLabelsFormatter.setHorizontalLabels(arrayOfLabels)
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);


        graph.graphContentLeft

        //Set the ranges of the axes
        graph.getViewport().setMinX(0.0);
        graph.getViewport().setMinY(0.0);

        graph.getViewport().setYAxisBoundsManual(true);


        // styling
        series.setValueDependentColor { data ->
            Color.rgb(
                data.x.toInt() * 255 / 4,
                Math.abs(data.y * 255 / 6).toInt(),
                100
            )
        }

        series.spacing = 10

// draw values on top
        series.isDrawValuesOnTop = true
        series.valuesOnTopColor = Color.RED
    }

}
