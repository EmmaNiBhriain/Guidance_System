package wit.org.guidancesystem.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.login.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import wit.org.guidancesystem.AddBuilding
import wit.org.guidancesystem.R

class Login : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

    }

    fun logIn(view: View){
        val email = email.text.toString()
        val password = password.text.toString()
        if (email == "" || password == "") {
            toast("Please provide email + password")
        }
        else {
            intent = Intent(this, AddBuilding::class.java)
            startActivity(intent)
        }
    }
}
