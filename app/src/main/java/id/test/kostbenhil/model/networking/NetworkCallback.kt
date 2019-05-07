package id.test.kostbenhil.model.networking

interface NetworkCallback<in T> {

    fun onSuccess(response: T)

    fun onError(strResponse: String, strErrorCode: String)
}