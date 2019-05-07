package id.test.kostbenhil.model

import android.os.Parcelable
import eu.amirs.JSON
import id.test.kostbenhil.shared.supportclasses.getInt
import id.test.kostbenhil.shared.supportclasses.getString
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var token: String = ""): Parcelable {
    constructor(data: JSON) : this() {
        this.token = data.getString("token")
    }
}