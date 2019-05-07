package id.test.kostbenhil.screen.beranda

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import id.test.kostbenhil.R

class BerandaFragment : Fragment(), BerandaInterface, View.OnClickListener {

    companion object {
        fun newInstance(): BerandaFragment {
            val fragment = BerandaFragment()
            return fragment
        }
    }

    private var presenter: BerandaPresenter? = null

    private lateinit var viewContainer: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewContainer = inflater.inflate(R.layout.fragment_beranda, container, false)

        return viewContainer
    }

    fun refreshPage() {
        if (isAdded) {
            presenter = BerandaPresenter(activity as Context)
            presenter?.onAttach(this)
        }
    }

    override fun onClick(v: View?) {
    }

    override fun onAttach() {
        presenter = BerandaPresenter(activity as Context)
        presenter?.onAttach(this)
    }
}
