package progr.rostoslav.githubapi.ui.activityes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import progr.rostoslav.githubapi.R
import progr.rostoslav.githubapi.data.local.APP_USER
import progr.rostoslav.githubapi.data.local.USER_LOGIN
import progr.rostoslav.githubapi.data.local.USER_PASSWORD
import progr.rostoslav.githubapi.entities.User

class LoginActivity : AppCompatActivity() {
    private val PASSWORD_SIZE_RANGE = 6..70
    lateinit var pref: SharedPreferences
    var email = ""
    var pass = ""
    var user = User("", "")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
        updateButtonMode()

        pref = getSharedPreferences(APP_USER, AppCompatActivity.MODE_PRIVATE)
        if ((pref.contains(USER_LOGIN))&&(pref.contains(USER_PASSWORD))){
            email = pref.getString(USER_LOGIN, "") ?: ""
            pass = pref.getString(USER_PASSWORD, "") ?: ""
            user = User(email, pass)

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(USER_LOGIN, user.email)
            intent.putExtra(USER_PASSWORD, user.password)
            startActivity(intent)
            finish()
        }
    }

    private fun init() {
        al_btn_sign_in.setOnClickListener {
            email = al_et_email.text.toString()
            pass = al_et_password.text.toString()
            user = User(email, pass)
            val editor = pref.edit()
            editor.putString(USER_LOGIN, user.email)
            editor.putString(USER_PASSWORD, user.password)
            editor.commit()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(USER_LOGIN, user.email)
            intent.putExtra(USER_PASSWORD, user.password)
            startActivity(intent)
            finish()
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