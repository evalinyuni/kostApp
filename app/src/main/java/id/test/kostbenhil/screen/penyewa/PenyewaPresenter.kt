package id.test.kostbenhil.screen.penyewa

import android.content.Context
import android.os.Handler
import android.os.Looper
import id.test.kostbenhil.BasePresenter
import id.test.kostbenhil.model.networking.API

class PenyewaPresenter(private val context: Context) : BasePresenter<PenyewaInterface> {

    private val TAG = PenyewaPresenter::class.java.simpleName
    private var view: PenyewaInterface? = null
    private lateinit var API: API

    override fun onAttach(view: PenyewaInterface) {
        this.view = view
        API = API(context)
    }

    override fun onDetach() {
        view = null
    }

    fun listTenant(page: Int, pagesize: Int = 10) {
        view?.onProgress()
        API.getListTenant(page, pagesize){ _arrListTenant, status ->
            val mainHandler = Handler(Looper.getMainLooper())
            mainHandler.post {
                when (status.isSuccess) {
                    true -> view?.onSuccessListTenant(_arrListTenant)
                    else -> view?.onFailed(status)
                }
            }
        }
    }
}