package wit.org.guidancesystem.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Model class for a BuildingModel object
 */
@Parcelize
data class BuildingModel(var id:String = "", var name:String = "", var rooms:ArrayList<Metre> = arrayListOf()): Parcelable {
}