package id.test.kostbenhil.screen.login

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import id.test.kostbenhil.R
import id.test.kostbenhil.shared.supportclasses.convertPhoneNumberID
import id.test.kostbenhil.shared.supportclasses.isPhoneNumber
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
        initView()
        btnNext.setOnClickListener {
            validateMobileNumber()
        }

    }

    private fun initView() {
        txtInputMobileNumber.addTextChangedListener(mobileNumberWatcher)
        txtInputMobileNumber.requestFocus()
        showSoftKeyboard(txtInputMobileNumber)
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

    private fun validateMobileNumber() {
        val phoneNumber = txtInputMobileNumber.text.toString().convertPhoneNumberID()
        Log.d("convertPhoneNumberID", phoneNumber)
        if (phoneNumber.isEmpty()) {
            lblMobileNumberAlert.text = resources.getString(R.string.login_input_your_mobile_number)
            lblMobileNumberAlert.visibility = View.VISIBLE
        } else if (phoneNumber.length < 9
            || phoneNumber.length > 13) {
            lblMobileNumberAlert.text = resources.getString(R.string.login_check_your_mobile_number)
            lblMobileNumberAlert.visibility = View.VISIBLE
        } else if (!phoneNumber.isPhoneNumber()) {
            lblMobileNumberAlert.text = resources.getString(R.string.login_input_your_mobile_number_is_wrong)
            lblMobileNumberAlert.visibility = View.VISIBLE
        } else {
            lblMobileNumberAlert.visibility = View.GONE
            intentLoginRegister(phoneNumber)
        }
    }

    private fun intentLoginRegister(mobileNumber: String) {
        startActivity(InputPINActivity.newIntent(this, mobileNumber))
    }
}
