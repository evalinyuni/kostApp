package id.test.kostbenhil.screen.pengaturan

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import id.test.kostbenhil.R


class PengaturanFragment : Fragment() {

    companion object {
        fun newInstance(): PengaturanFragment {
            val fragment = PengaturanFragment()
            return fragment
        }
    }

    private lateinit var viewContainer: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewContainer = inflater.inflate(R.layout.fragment_pengaturan, container, false)

        return viewContainer
    }

}
