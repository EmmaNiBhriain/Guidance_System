package wit.org.guidancesystem

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.AnkoLogger
import wit.org.guidancesystem.activities.Login

open class Base: AnkoLogger, AppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.item_logout -> {
                FirebaseAuth.getInstance().signOut()
                intent = Intent(this, Login::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true

    }
}