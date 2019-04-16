package wit.org.guidancesystem.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_building.*
import kotlinx.android.synthetic.main.add_dimensions.*
import kotlinx.android.synthetic.main.display_users.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import wit.org.guidancesystem.R
import wit.org.guidancesystem.main.MainApp
import wit.org.guidancesystem.models.BuildingModel

class ListUsers : AppCompatActivity(),AnkoLogger, UserListener {

    lateinit var app:MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.display_users)


        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        userRecyclerView.layoutManager = layoutManager

        info{"!!!No. users " + app.users.size}
        userRecyclerView.adapter = UserAdapter(app.users, this)
    }



    override fun onUserClick(user: String) {
        app.targetUserEmail = user

        val buildingWidth = EditText(this)
        val buildingLength = EditText(this)

        buildingWidth.setHint("Width in metres")
        buildingLength.setHint("Length in metres")

        buildingWidth.inputType = InputType.TYPE_CLASS_NUMBER
        buildingLength.inputType = InputType.TYPE_CLASS_NUMBER

        var layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        layout.addView(buildingWidth)
        layout.addView(buildingLength)

        AlertDialog.Builder(this).setTitle("Layout")
            .setMessage("Enter building Dimensions")
            .setView(layout)
            .setPositiveButton("OK"){dialog, which ->
                var width = buildingWidth.getText().toString();

                var length = buildingLength.getText().toString();

                info { "!!! " + width }

                intent = Intent(this, AddFloor::class.java)
                startActivity(intent)

            }
            .show()

    }
}
