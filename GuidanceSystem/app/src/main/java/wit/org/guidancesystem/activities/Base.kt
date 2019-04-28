package wit.org.guidancesystem.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.AnkoLogger
import wit.org.guidancesystem.R

/**
 * Class with methods that can be called from other classes that extend this class
 */
open class Base: AnkoLogger, AppCompatActivity() {

    /**
     * Called when option in the toolbar menu is pressed.
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.item_logout -> {
                logout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Sign out, end the firebase instance and redisplay the login screen.
     */
    fun logout(){
        FirebaseAuth.getInstance().signOut()
        intent = Intent(this, Login::class.java)
        startActivity(intent)
    }

    /**
     * Display toolbar menu that has logout option
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        return true

    }
}