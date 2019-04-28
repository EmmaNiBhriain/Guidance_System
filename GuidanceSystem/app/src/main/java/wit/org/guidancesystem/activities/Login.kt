package wit.org.guidancesystem.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.login.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import wit.org.guidancesystem.BuildingActivity
import wit.org.guidancesystem.R
import wit.org.guidancesystem.firebase.BuildingFireStore
import wit.org.guidancesystem.firebase.DestinationFireStore
import wit.org.guidancesystem.main.MainApp
import wit.org.guidancesystem.models.BuildingModel
import wit.org.guidancesystem.models.Metre

class Login : AppCompatActivity(), AnkoLogger {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var firestore: BuildingFireStore?=null
    var destFirestore: DestinationFireStore?=null

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {

        app = application as MainApp
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)


        app.users.clear()
        app.rooms.clear()
        app.buildings.clear()

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
                                    else{
                                        intent = Intent(this, UserHome::class.java)
                                        startActivity(intent)
                                    }
                                }
                            }
                            else{
                                intent = Intent(this, UserHome::class.java)
                                startActivity(intent)
                            }


                        }
                    }

                    else{
                        intent = Intent(this, UserHome::class.java)
                        startActivity(intent)
                    }

                } else {
                    toast("Sign Up Failed: ${task.exception?.message}")
                }
            }

        }
    }

    fun signUp(view:View){

        auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firestore = app.buildings
                if(firestore!=null){
                    firestore!!.fetchBuildings {
                        destFirestore = app.destinations
                        if(destFirestore!=null){
                            destFirestore!!.fetchDestinations {
                                if(email.text.toString()=="obrienemma0@gmail.com"){
                                    intent = Intent(this, AdminHome::class.java)
                                    startActivity(intent)
                                }
                                else{

                                    intent = Intent(this, BuildingActivity::class.java)
                                    startActivity(intent)
                                }
                            }
                        }
                    }
                }
                else{
                    intent = Intent(this, BuildingActivity::class.java)
                    startActivity(intent)
                }
                toast("SignIn Success:")
                var userId = FirebaseAuth.getInstance().currentUser!!.uid
                var userEmail = encodeUserEmail(FirebaseAuth.getInstance().currentUser!!.email!!)
                info{"!!!  Email" + userEmail}
                var db = FirebaseDatabase.getInstance().reference

                //val key = db.child("users").child(userEmail).push().key
                db.child("users").child(userEmail).child("Buildings").child("test").push().setValue(BuildingModel("test")) //create folder
                db.child("users").child(userEmail).child("Destination").child("test").push().setValue(Metre()) //create folder

            } else {
                toast("Sign Up Failed: ${task.exception?.message}")
            }
        }


    }

    fun encodeUserEmail(userEmail: String): String {
        return userEmail.replace(".", ",")
    }
}
