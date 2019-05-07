package id.test.kostbenhil.shared.supportclasses

import eu.amirs.JSON
import org.json.JSONObject

fun jsonHasKey(jsonString: String, key: String): Boolean {
    return when{
        jsonString.isEmpty() -> false
        jsonString.startsWith("{") && jsonString.endsWith("}") -> {
            val json = JSONObject(jsonString)
            json.has(key)
        }
        else -> false
    }
}

fun JSON.getString(param: String): String {
    val response = this.toString()
    return when{
        jsonHasKey(response, param) -> this.key(param).stringValue()
        else -> return ""
    }
}

fun JSON.getInt(param: String): Int {
    val response = this.toString()
    return when{
        jsonHasKey(response, param) -> this.key(param).intValue()
        else -> return 0
    }
}

fun JSON.getList(param: String = ""): ArrayList<JSON> {
    val listResponse = ArrayList<JSON>()
    if (param.isEmpty()) {
        for (i in 0 until this.count()) {
            listResponse.add(this.index(i))
        }
    } else {
        val jsonResponse = this.key(param)
        for (i in 0 until jsonResponse.count()) {
            listResponse.add(jsonResponse.index(i))
        }
    }
    return listResponse
}

fun JSON.getKey(param: String): JSON {
    val response = this.toString()
    return when{
        jsonHasKey(response, param) -> this.key(param)
        else -> return JSON("")
    }
}