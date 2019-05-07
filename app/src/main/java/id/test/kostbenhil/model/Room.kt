package id.test.kostbenhil.model

import android.os.Parcelable
import eu.amirs.JSON
import id.test.kostbenhil.shared.supportclasses.getString
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Room(var name: String = ""): Parcelable {
    constructor(data: JSON): this() {
        this.name = data.getString("name")
    }
}