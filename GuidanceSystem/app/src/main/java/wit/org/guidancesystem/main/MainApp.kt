package wit.org.guidancesystem.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import wit.org.guidancesystem.firebase.BuildingFireStore
import wit.org.guidancesystem.firebase.DestinationFireStore
import wit.org.guidancesystem.models.BuildingModel
import wit.org.guidancesystem.models.Metre
import wit.org.guidancesystem.models.RoomModel

class MainApp : Application(), AnkoLogger {
    lateinit var buildings:BuildingFireStore
    lateinit var destinations:DestinationFireStore
    var users = ArrayList<String>()
    var targetUserEmail = ""
    var rooms = ArrayList<RoomModel>()

    override fun onCreate() {
        super.onCreate()
        info("App started")
        buildings = BuildingFireStore(applicationContext)
        destinations = DestinationFireStore(applicationContext)

    }
}