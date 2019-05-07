package id.test.kostbenhil.shared.supportclasses

import android.annotation.SuppressLint
import android.util.Log
import java.util.regex.Pattern

fun String.convertPhoneNumberID(): String {
    return when {
        this.startsWith("8") -> "0$this"
        this.startsWith("62") -> "0${this.substring(2, this.length)}"
        this.startsWith("+62") -> "0${this.substring(3, this.length)}"
        else -> this
    }
}

@SuppressLint("LongLogTag")
fun String.isPhoneNumber(): Boolean {
    if (this.startsWith("+")) {
        if (!this.startsWith("+62")) {
            return false
        }
    }

    var strPhoneNumber: String = this
    if (strPhoneNumber.startsWith("62")) {
        strPhoneNumber = "0${this.substring(2, this.length)}"
    } else if (strPhoneNumber.startsWith("+62")) {
        strPhoneNumber = "0${this.substring(3, this.length)}"
    }
    Log.d("convertPhoneNumberID-isPhoneNumber", strPhoneNumber)

//    val PHONE_REGEX =  "^(^0856|^0811|^0812|^0813|^0821|^0822|^0823|^0852|^0853|^0851|^0814|^0815|^0816|^0855|^0857|^0858|^0817|^0818|^0819|^0877|^0878|^0879|^0838|^0831|^0859|^0832|^0833)|^08098888"
    val PHONE_REGEX = "^(^08).*\$"
    val pattern = Pattern.compile(PHONE_REGEX)
    val matcher = pattern.matcher(strPhoneNumber)
    Log.d("convertPhoneNumberID-PHONE_REGEX", "${matcher.matches()}")

    return matcher.matches()
}