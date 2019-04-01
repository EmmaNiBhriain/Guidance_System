package wit.org.guidancesystem.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import wit.org.guidancesystem.AddBuilding
import wit.org.guidancesystem.BuildingActivity
import wit.org.guidancesystem.R
import wit.org.guidancesystem.Stats
import wit.org.guidancesystem.firebase.BuildingFireStore
import wit.org.guidancesystem.firebase.DestinationFireStore
import wit.org.guidancesystem.main.MainApp

class Login : AppCompatActivity(), AnkoLogger {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var firestore: BuildingFireStore?=null
    var destFirestore: DestinationFireStore?=null

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {

        app = application as MainApp
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

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firestore = app.buildings
                    if(firestore!=null){
                        firestore!!.fetchBuildings {
                            destFirestore = app.destinations
                            if(destFirestore!=null){
                                destFirestore!!.fetchDestinations {
                                    if(email=="obrienemma0@gmail.com"){
                                        intent = Intent(this, AdminHome::class.java)
                                        startActivity(intent)
                                    }
                                    else if(email == "007eob@gmail.com"){
                                        intent = Intent(this, BuildingActivity::class.java)
                                        startActivity(intent)
                                    }
                                }
                            }


                        }
                    }

                    else{
                        intent = Intent(this, AddBuilding::class.java)
                        startActivity(intent)
                    }

                } else {
                    toast("Sign Up Failed: ${task.exception?.message}")
                }
            }

        }
    }
}
