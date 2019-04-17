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
import wit.org.guidancesystem.main.MainApp
import wit.org.guidancesystem.models.Metre


class RoomGraph : Base(), AdapterView.OnItemSelectedListener {

    lateinit var app:MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_graph)
        setSupportActionBar(toolbar)

        app = application as MainApp

        var list_of_items = ArrayList<String>()

        for (d in app.destinations.findAll())
            list_of_items.add(d.name)

        spinner!!.setOnItemSelectedListener(this)

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_items)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.setAdapter(aa)


        val graph = findViewById(R.id.roomgraph) as GraphView
        val series = LineGraphSeries<DataPoint>(
            arrayOf(
                DataPoint(0.0, 5.0),
                DataPoint(1.0, 5.0),
                DataPoint(2.0, 6.0),
                DataPoint(3.0, 2.0),
                DataPoint(4.0, 2.0)
            )
        )
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
        staticLabelsFormatter.setHorizontalLabels(arrayOf("Mon", "Tues", "Wed", "Thurs", ""))
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        graph.getGridLabelRenderer().setVerticalAxisTitle("Number of Visits");

    }




    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        // use position to know the selected item
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

}
