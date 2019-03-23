package wit.org.guidancesystem.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Metre(var type:AreaType , var name:String="", var xCoOrd:Int, var yCoOrd:Int): Parcelable {
}