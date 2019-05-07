package id.test.kostbenhil.screen.penyewa

import id.test.kostbenhil.BaseView
import id.test.kostbenhil.model.Penyewa
import id.test.kostbenhil.model.StatusResponse

interface PenyewaInterface: BaseView {
    fun onProgress()

    fun onFinishProgress()

    fun onSuccessListTenant(_arrListTenant: ArrayList<Penyewa>)

    fun onFailed(statusResponse: StatusResponse)
}