package wit.org.guidancesystem

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_room.*

class Room : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        toolbarRoom.title = "Building"
        setSupportActionBar(toolbarRoom)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
