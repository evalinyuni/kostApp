package id.test.kostbenhil.model

import android.os.Parcelable
import eu.amirs.JSON
import id.test.kostbenhil.shared.supportclasses.getInt
import id.test.kostbenhil.shared.supportclasses.getString
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DiscoverTenants(
    var id: Int = 0,
    var amount: Int = 0,
    var nameTenant: String = "",
    var paymentDueAt: Int = 0,
    var paidAt: Int = 0,
    var createdAt: Int = 0
): Parcelable {
    constructor(data: JSON) : this() {
        this.id = data.getInt("id")
        this.amount = data.getInt("amount")
        this.nameTenant = data.getString("")
        this.paymentDueAt = data.getInt("payment_due_at")
        this.paidAt = data.getInt("paid_at")
    }
}