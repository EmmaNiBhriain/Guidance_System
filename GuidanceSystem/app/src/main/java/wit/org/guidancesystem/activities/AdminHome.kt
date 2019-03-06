package wit.org.guidancesystem.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import wit.org.guidancesystem.AddBuilding
import wit.org.guidancesystem.Base
import wit.org.guidancesystem.R

class AdminHome : Base() {

    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(R.layout.admin_home)

        super.onCreate(savedInstanceState)

    }

    fun addBuilding(view: View){
        intent = Intent(this, AddBuilding::class.java)
        startActivity(intent)
    }
}
