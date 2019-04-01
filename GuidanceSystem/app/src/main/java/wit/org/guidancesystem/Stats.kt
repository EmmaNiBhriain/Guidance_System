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








class Stats : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        setSupportActionBar(toolbar)

        val graph = findViewById(R.id.usagegraph) as GraphView
        val series =  BarGraphSeries<DataPoint>(arrayOf(
            DataPoint(0.0, -1.0),
            DataPoint(1.0, 5.0),
            DataPoint(2.0, 3.0)
        ));
        graph.addSeries(series);


        val staticLabelsFormatter = StaticLabelsFormatter(graph)
        staticLabelsFormatter.setHorizontalLabels(arrayOf("old", "middle", "new"))
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

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
