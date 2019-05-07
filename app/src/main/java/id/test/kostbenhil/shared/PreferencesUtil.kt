package id.test.kostbenhil.shared

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.scottyab.aescrypt.AESCrypt
import id.test.kostbenhil.model.User
import java.security.GeneralSecurityException

fun Context.pref(): SharedPreferences {
    return this.getSharedPreferences("kos_app_pref", Activity.MODE_PRIVATE)
}

fun Context.prefContains(strKey: String): Boolean {
    return this.pref().contains(strKey)
}

private fun decryptPref(value: String): String {
    val decrypted: String
    try {
        decrypted = AESCrypt.decrypt("", value)
    } catch (e: GeneralSecurityException) {
        //handle error
        return ""
    }
    return decrypted
}

// save Pref User
private fun Context.savePrefUser(user : User) {
    val userString: String = Gson().toJson(user)
    this.setPrefUser(userString)
}

fun Context.getUser(): User {
    val userString: String = this.getPrefUser()
    return Gson().fromJson(userString, User::class.java)
}

fun Context.saveUser(userData: User) {
    val user = getUser()
    user.token = userData.token
    Log.d("==== token", userData.token)
    this.savePrefUser(user)
}

fun Context.saveClientId(clientId: String) {
    this.saveToPref("client_id", clientId)
}

fun Context.getClientId(): String {
    return this.getStringFromPref("client_id")
}

fun Context.setPrefUser(user: String) {
    val editor = this.pref().edit()
    editor.putString("user", encryptPref(user))
    /* save pref */
    editor.apply()
}

fun Context.getPrefUser(): String {
    return if (this.prefContains("user")) {
        val value = this.pref().getString("user","{}")
        var valueDecrypt = decryptPref(value)
        if (valueDecrypt.isNullOrBlank() || valueDecrypt.isEmpty()) {
            valueDecrypt = "{}"
        }
        valueDecrypt
    } else {
        "{}"
    }
}

fun Context.saveToPref(strKey: String, value: Any?) {
    /* checking value type */
    val editor = this.pref().edit()
    when (value) {
        is String   -> editor.putString(strKey, encryptPref(value))
        is Boolean  -> editor.putBoolean(strKey, value)
        is Float    -> editor.putFloat(strKey, value)
        is Int      -> editor.putInt(strKey, value)
        is Long     -> editor.putLong(strKey, value)
    }
    /* save pref */
    editor.apply()
}

fun Context.getStringFromPref(strKey: String): String {
    return if (prefContains(strKey)) {
        val value = this.pref().getString(strKey, "")
        var valueDecrypt = decryptPref(value)
        if (valueDecrypt.isNullOrBlank()) {
            valueDecrypt = ""
        }
        valueDecrypt
    } else {
        ""
    }
}

private fun encryptPref(value: String): String {
    val encrypted: String
    try {
        encrypted = AESCrypt.encrypt("", value)
    } catch (e: GeneralSecurityException) {
        //handle error
        return ""
    }
    return encrypted
}