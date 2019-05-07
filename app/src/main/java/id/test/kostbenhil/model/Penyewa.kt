package id.test.kostbenhil.model

import android.os.Parcelable
import eu.amirs.JSON
import id.test.kostbenhil.shared.supportclasses.getInt
import id.test.kostbenhil.shared.supportclasses.getList
import id.test.kostbenhil.shared.supportclasses.getString
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Penyewa(
    var arrTenants: ArrayList<DiscoverTenants> = ArrayList()
): Parcelable {
    constructor(data: JSON) : this() {
        for (_tenants in data.getList("tenants")) {
            this.arrTenants.add(DiscoverTenants(_tenants))
        }

    }
}
