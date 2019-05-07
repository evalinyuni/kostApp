package id.test.kostbenhil.screen.beranda

import android.content.Context
import id.test.kostbenhil.BasePresenter
import id.test.kostbenhil.model.networking.API

class BerandaPresenter(private val context: Context) : BasePresenter<BerandaInterface> {

    private val TAG = BerandaPresenter::class.java.simpleName
    private var view: BerandaInterface? = null
    private lateinit var API: API

    override fun onAttach(view: BerandaInterface) {
        this.view = view
        API = API(context)
    }

    override fun onDetach() {
        view = null
    }
}