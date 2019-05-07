package id.test.kostbenhil.screen.login

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import id.test.kostbenhil.MainActivity
import id.test.kostbenhil.R
import id.test.kostbenhil.model.StatusResponse
import id.test.kostbenhil.model.networking.API
import kotlinx.android.synthetic.main.activity_input_pin.*

class InputPINActivity : AppCompatActivity(), InputPINInterface {

    private val TAG = InputPINActivity::class.java.simpleName

    companion object {
        val MOBILE_NUMBER = "mobile_number"
        fun newIntent(context: Context, phoneNumber: String): Intent {
            val intent = Intent(context, InputPINActivity::class.java)
            intent.putExtra(MOBILE_NUMBER, phoneNumber)
            return intent
        }
    }

    private lateinit var presenter: InputPINPresenter
    private lateinit var phoneNumber: String
    private var API: API? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_pin)

        supportActionBar?.hide()
        onAttach()
        initView()
        btnLogin.setOnClickListener {
            validatePassword()
        }
    }

    private fun initView() {
        txtInputPassword.addTextChangedListener(mobileNumberWatcher)
        txtInputPassword.requestFocus()
        showSoftKeyboard(txtInputPassword)
    }

    private val mobileNumberWatcher: TextWatcher = object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            lblMobileNumberAlert.text = ""
            lblMobileNumberAlert.visibility = View.GONE
        }
    }

    fun showSoftKeyboard(editText: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun validatePassword() {
        if (intent.hasExtra(MOBILE_NUMBER)) {
            phoneNumber = intent.getStringExtra(MOBILE_NUMBER)
            if (txtInputPassword.text.isEmpty()) {
                lblMobileNumberAlert.text = "Masukkan password Anda"
                lblMobileNumberAlert.visibility = View.VISIBLE
            } else {
                lblMobileNumberAlert.visibility = View.GONE
                intentLogin(phoneNumber)
            }
        }
    }

    private fun intentLogin(phoneNumber: String) {
        val password = txtInputPassword.text.toString()
        presenter.login(phoneNumber, password)
    }

    override fun onProgress() {
    }

    override fun onFinishProgress() {
    }

    override fun onSuccessLogin() {
        if (!isFinishing) {
            onFinishProgress()
            startActivity(MainActivity.newIntent(this))
            finish()
        }
    }

    override fun onFailed(statusResponse: StatusResponse) {
        onFinishProgress()
        Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
    }

    override fun onAttach() {
        presenter = InputPINPresenter(this)
        presenter.onAttach(this)
    }

    override fun onDetach() {
        presenter.onDetach()
    }
}
