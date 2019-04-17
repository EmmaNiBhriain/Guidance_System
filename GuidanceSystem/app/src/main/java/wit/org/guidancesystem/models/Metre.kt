package wit.org.guidancesystem.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Metre(var type:AreaType = AreaType.CORRIDOR, var name:String="", var xCoOrd:Int = 0, var yCoOrd:Int = 0, var bluetoothId:String = "", var visited:Boolean = false, var fbId:String = "", var date:String="", var time:String=""): Parcelable {
}