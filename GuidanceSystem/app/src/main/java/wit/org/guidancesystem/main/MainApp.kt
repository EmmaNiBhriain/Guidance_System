package wit.org.guidancesystem.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import wit.org.guidancesystem.models.BuildingModel
import wit.org.guidancesystem.models.RoomModel

class MainApp : Application(), AnkoLogger {
    val buildings = ArrayList<BuildingModel>()
    val rooms = ArrayList<RoomModel>()

    override fun onCreate() {
        super.onCreate()
        info("App started")
        val test1 = arrayListOf<String>()
        test1.addAll(listOf("room1","room2"))
        buildings.add(BuildingModel("12343242", "Home", test1))
        buildings.add(BuildingModel("12343242", "Away", test1))

    }
}