package wit.org.guidancesystem.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_building.*
import kotlinx.android.synthetic.main.display_users.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import wit.org.guidancesystem.R
import wit.org.guidancesystem.main.MainApp

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
        intent = Intent(this, AddFloor::class.java)
        startActivity(intent)
    }

}
