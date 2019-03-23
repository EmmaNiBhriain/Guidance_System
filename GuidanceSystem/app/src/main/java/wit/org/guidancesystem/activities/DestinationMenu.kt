package wit.org.guidancesystem.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import wit.org.guidancesystem.R

import kotlinx.android.synthetic.main.activity_destination_menu.*

class DestinationMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination_menu)
        setSupportActionBar(toolbar)

    }

}
