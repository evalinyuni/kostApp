package id.test.kostbenhil.screen.login

import android.content.Context
import android.os.Handler
import android.os.Looper
import id.test.kostbenhil.BasePresenter
import id.test.kostbenhil.model.networking.API
import id.test.kostbenhil.shared.saveUser

class InputPINPresenter(private val context: Context) : BasePresenter<InputPINInterface>{

    private var view: InputPINInterface? = null
    private lateinit var API: API

    override fun onAttach(view: InputPINInterface) {
        this.view = view
        API = API(context)
    }

    override fun onDetach() {
        view = null
    }

    fun login(phoneNumber: String, password: String) {
        view?.onProgress()
        API.login(phoneNumber, password) { response, status ->
            val mainHandler = Handler(Looper.getMainLooper())
            mainHandler.post {
                when (status.isSuccess){
                    true -> {
                        context.saveUser(response)
                        view?.onSuccessLogin()
                    }
                    else -> view?.onFailed(status)

                }
            }
        }
    }
}