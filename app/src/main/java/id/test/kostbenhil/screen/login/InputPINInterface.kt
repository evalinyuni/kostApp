package id.test.kostbenhil.screen.login

import id.test.kostbenhil.BaseView
import id.test.kostbenhil.model.StatusResponse

interface InputPINInterface : BaseView{
    fun onProgress()
    fun onFinishProgress()
    fun onSuccessLogin()
    fun onFailed(statusResponse: StatusResponse)
}