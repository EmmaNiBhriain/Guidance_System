package wit.org.guidancesystem.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Model class for a RoomModel object
 */
@Parcelize
data class RoomModel(var id:String = "", var type:String = ""): Parcelable {
}