package id.test.kostbenhil.model.networking

import android.content.Context
import eu.amirs.JSON
import id.test.kostbenhil.model.Penyewa
import id.test.kostbenhil.model.StatusResponse
import id.test.kostbenhil.model.User
import id.test.kostbenhil.shared.Constants
import id.test.kostbenhil.shared.supportclasses.getKey
import id.test.kostbenhil.shared.supportclasses.getList
import id.test.kostbenhil.shared.supportclasses.getString
import org.json.JSONObject

class API(val context: Context) {

    private val TAG = API::class.java.simpleName

    private val apiClient: APIClient = APIClient(context)

    private val BASE_URL = "http://private-anon-b9b881e7cd-ndv1.apiary-mock.com"

    fun validateResponse(json: JSON, url: String, param: JSONObject, delegateAPIValidate: DelegateAPIValidate) {
        var strStatus = json.getString("status")
        var strDescription = json.getString("message")

        if (strDescription.isEmpty()) {
            strDescription = strStatus
        }

        if (strStatus.isEmpty()) {
            strStatus = json.getString("resultCode")
            if(strStatus == "000"){
                delegateAPIValidate.onValidate(true, strStatus)
            } else {
                delegateAPIValidate.onValidate(false, strStatus)
            }
        }

        if (strStatus.isEmpty()) {
            strStatus = json.getString("responseCode")
            if(strStatus == "000"){
                delegateAPIValidate.onValidate(true, strStatus)
            } else {
                delegateAPIValidate.onValidate(false, strStatus)
            }
        }

        if (strStatus.isEmpty()) {
            delegateAPIValidate.onValidate(false, strDescription)
        } else {
            when (strStatus) {
                "VALID" -> delegateAPIValidate.onValidate(true, strDescription)
                "MEMBER_ALREADY_REGISTER" -> {
                    delegateAPIValidate.onValidate(true, strDescription)
                }
                "PROCESSED","000","SUCCESS" -> delegateAPIValidate.onValidate(true, strDescription)
                "INVALID_SECURITY_KEY" -> {
                    apiClient.cancelAPIRequestCall()
//                    context.sendBroadcast(StatusBroadcastReceiver.newIntent(context, strDescription,
//                            StatusBroadcastReceiver.INVALID_SECURITY_KEY))  //todo change this for handle response
                    delegateAPIValidate.onValidate(false, strDescription)
                }
                "TOKEN_EXPIRED" -> {
                    apiClient.cancelAPIRequestCall()
//                    context.sendBroadcast(StatusBroadcastReceiver.newIntent(context, strDescription,
//                            StatusBroadcastReceiver.TOKEN_EXPIRED)) //todo change this for handle response
                    delegateAPIValidate.onValidate(false, strDescription)
                }
                "ERROR" -> {
                    delegateAPIValidate.onValidate(false, strDescription)
                }
                "NO_SERIAL_NUMBER" -> {
                    delegateAPIValidate.onValidate(false, strDescription)
                }
                else -> {
                    delegateAPIValidate.onValidate(true, strDescription)
                }
            }
        }
    }

    fun login(phone: String, password: String, callback: (User, StatusResponse) -> Unit) {
        val generatedJsonObject = JSON.create(
            JSON.dic(
                Constants.PARAM.PHONE_NUMBER, phone,
                Constants.PARAM.PASSWORD, password))
        val jsonParam = JSONObject(generatedJsonObject.toString())
        apiClient.callRestAPILogin("$BASE_URL/auth/v1/login",
            jsonParam, phone, password, apiClient.POST, object : DelegateAPIRequest {
                override fun onCallSuccess(response: JSON) {
                    validateResponse(response, "/auth/v1/login", jsonParam, object : DelegateAPIValidate{
                        override fun onValidate(isValidate: Boolean, description: String) {
                            if (isValidate) {
                                val user = User(response)
                                user.token =  response.getKey("data").getString("token")

                                callback(user, StatusResponse())
                            } else { callback(User(), StatusResponse(isValidate, description, "")) }
                        }
                    })
                }

                override fun onCallFailed(statusResponse: StatusResponse) {
                    callback(User(), statusResponse)
                }
            })
    }

    fun getListTenant(page: Int, pagesize: Int = 10, callback: (ArrayList<Penyewa>, StatusResponse) -> Unit) {
        val generatedJsonObject = JSON.create(
            JSON.dic(
                "onpage", page,
                "pagesize", pagesize))
        val jsonParam = JSONObject(generatedJsonObject.toString())
        apiClient.callRestAPI("$BASE_URL/property/v1/tenants?page=$page",
            jsonParam, apiClient.GET, object : DelegateAPIRequest {
                override fun onCallSuccess(response: JSON) {
                    var arrTenant: ArrayList<Penyewa> = ArrayList()
                    for (_data in response.getList("content")) {
                        arrTenant.add(Penyewa(_data))
                    }

                    callback(arrTenant, StatusResponse())
                }
                override fun onCallFailed(statusResponse: StatusResponse) {
                    callback(ArrayList(), statusResponse)
                }
            })
    }

}