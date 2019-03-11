package wit.org.guidancesystem.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Metre(var id:Int, var type:AreaType): Parcelable {
}