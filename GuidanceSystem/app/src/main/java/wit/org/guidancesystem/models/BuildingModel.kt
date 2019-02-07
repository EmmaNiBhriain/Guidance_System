package wit.org.guidancesystem.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class BuildingModel(var id:String = "", var name:String = "", var rooms:ArrayList<String> = arrayListOf()): Parcelable {
}