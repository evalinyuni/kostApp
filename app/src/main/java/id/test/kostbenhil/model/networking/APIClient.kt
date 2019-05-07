package id.test.kostbenhil.model.networking

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import eu.amirs.JSON
import id.test.kostbenhil.R
import id.test.kostbenhil.model.StatusResponse
import id.test.kostbenhil.model.User
import id.test.kostbenhil.shared.EncryptUtils
import id.test.kostbenhil.shared.OkhttpSingleton
import id.test.kostbenhil.shared.getClientId
import id.test.kostbenhil.shared.getUser
import okhttp3.*
import okio.Buffer
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.ArrayList

class APIClient(val context: Context) {

    private val TAG = APIClient::class.java.simpleName

    val GET = "get"
    val POST = "post"

    companion object {
        val contectType = MediaType.parse("application/json; charset=utf-8")
    }

    private var okHttpClient: OkHttpClient = OkhttpSingleton.getInstance(context)
    private var call: Call? = null

    fun callRestAPI(url: String, jObjParameters: JSONObject?, strMethod: String, delegateAPIRequest: DelegateAPIRequest) {
        var user : User = context.getUser()
        val token = "Bearer "+user.token
        val clientId = context.getClientId()
        Log.d("==== token",token)
        Log.d("==== clientId",clientId)
        val request = if (strMethod.equals(GET, ignoreCase = true)) {
            Request.Builder()
                .url(url)
                .addHeader("Authorization", token)
                .addHeader("Client-ID",clientId)
                .build()
        } else {
            val body = RequestBody.create(contectType, jObjParameters.toString())
            Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", token)
                .addHeader("Client-ID",clientId)
                .build()
        }

        call = okHttpClient.newCall(request)
        call?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                try {
                    if (jObjParameters != null) {
                        Log.d("onServiceCall-onFailure", "URL : $url, Params : $jObjParameters")
                    } else {
                        Log.d("onServiceCall-onFailure", "URL : $url")
                    }
                } catch (e1: Exception) {
                    e1.printStackTrace()
                }
                delegateAPIRequest.onCallFailed(
                    StatusResponse(false,
                    context.resources.getString(R.string.error_message_failed_get_response))
                )
            }

            @SuppressLint("LongLogTag")
            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                Log.d("onServiceCall-onResponse", "sukses")
                val strResponse: String = response.body()?.string() ?: "{ \"status\":\"ERROR\", \"description\":\"ERROR\"}"
                if (response.isSuccessful && response.code() == 200) {
                    Log.d("onServiceCall-onResponse", "onCallSuccess")
                    delegateAPIRequest.onCallSuccess(JSON(strResponse))
                    response.body()?.close()
                } else {
                    Log.d("onServiceCall-onResponse", "onCallFailed")
                    delegateAPIRequest.onCallFailed(StatusResponse(false, response.code().toString()))
                }
            }
        })
    }

    fun callRestAPILogin(url: String, jObjParameters: JSONObject?, phone:String, password:String, strMethod: String, delegateAPIRequest: DelegateAPIRequest) {
        val authToken = Credentials.basic(phone, password)
        val clientId = context.getClientId()
        val request = if (strMethod.equals(GET, ignoreCase = true)) {
            Request.Builder()
                .url(url)
                .build()
        } else {
            val body = RequestBody.create(contectType, jObjParameters.toString())
            Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", authToken)
                .addHeader("Client-ID",clientId)
                .build()
        }

        call = okHttpClient.newCall(request)
        call?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                try {
                    if (jObjParameters != null) {
                        Log.d("onServiceCall-onFailure", "URL : $url, Params : $jObjParameters")
                    } else {
                        Log.d("onServiceCall-onFailure", "URL : $url")
                    }
                } catch (e1: Exception) {
                    e1.printStackTrace()
                }
                delegateAPIRequest.onCallFailed(StatusResponse(false,
                    context.resources.getString(R.string.error_message_failed_get_response)))
            }

            @SuppressLint("LongLogTag")
            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                Log.d("onServiceCall-onResponse", "sukses")
                val strResponse: String = response.body()?.string() ?: "{ \"status\":\"ERROR\", \"description\":\"ERROR\"}"
                if (response.isSuccessful && response.code() == 200) {
                    Log.d("onServiceCall-onResponse", "onCallSuccess")
                    delegateAPIRequest.onCallSuccess(JSON(strResponse))
                    response.body()?.close()
                } else {
                    Log.d("onServiceCall-onResponse", "onCallFailed")
                    delegateAPIRequest.onCallFailed(StatusResponse(false, response.code().toString()))
                }
            }
        })
    }

    fun cancelAPIRequestCall() {
        if (call != null) {
            call?.cancel()
        }
    }

}
