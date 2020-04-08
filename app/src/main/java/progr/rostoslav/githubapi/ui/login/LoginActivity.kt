package progr.rostoslav.githubapi.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import progr.rostoslav.githubapi.R
import progr.rostoslav.githubapi.data.local.USER_LOGIN
import progr.rostoslav.githubapi.data.local.USER_PASSWORD
import progr.rostoslav.githubapi.entities.User
import progr.rostoslav.githubapi.ui.ActivityView
import progr.rostoslav.githubapi.ui.MainActivity

class LoginActivity : AppCompatActivity(), ActivityView {
    private val PASSWORD_SIZE_RANGE = 6..70

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init() {
        al_btn_sign_in.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(USER_LOGIN,al_et_email.text.toString())
            intent.putExtra(USER_PASSWORD, al_et_password.text.toString())
            startActivity(intent)
        }

        al_et_email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val email_input = al_et_email.text.toString()
                emailError(!checkEmail(email_input))
                updateButtonMode()
            }
        })
        al_et_password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val password = al_et_password.text.toString()
                passwordError(!(password.length in PASSWORD_SIZE_RANGE))
                updateButtonMode()
            }
        })
    }

    private fun emailError(isShowed: Boolean, message: String = "email uncorrect") {
        if (isShowed) al_til_email.setError(message) else al_til_email.setError("")
    }

    private fun passwordError(isShowed: Boolean, message: String = "too easy") {
        if (isShowed) al_til_password.setError(message) else al_til_password.setError("")
    }

    private fun checkEmail(email: String) = email.contains('@') && (email.length in 6..70)
    private fun checkPassword(password: String) = password.length in PASSWORD_SIZE_RANGE

    fun updateButtonMode() {
        al_btn_sign_in.isEnabled =
            checkEmail("" + al_et_email.text) && (checkPassword("" + al_et_password.text))
    }
}