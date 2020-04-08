package progr.rostoslav.githubapi.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_login.*
import progr.rostoslav.githubapi.Action
import progr.rostoslav.githubapi.R
import progr.rostoslav.githubapi.data.local.APP_USER
import progr.rostoslav.githubapi.data.local.USER_LOGIN
import progr.rostoslav.githubapi.data.local.USER_PASSWORD
import progr.rostoslav.githubapi.domain.AppModel
import progr.rostoslav.githubapi.entities.Rep
import progr.rostoslav.githubapi.entities.User
import progr.rostoslav.githubapi.ui.login.LoginActivity


class MainActivity : AppCompatActivity(), ActivityView, ActionProvider {
    lateinit var navController: NavController
    lateinit var user: User
    lateinit var pref: SharedPreferences
    val app_model = AppModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUser()
        initNavigation()
        initRealm()
        app_model.onCreate()
    }

    override fun onStop() {
        super.onStop()
        val editor = pref.edit()
        editor.putString(USER_LOGIN, user.email)
        editor.putString(USER_PASSWORD, user.password)
        editor.apply()
        app_model.onDestroy()
    }

    private fun setUser() {
      var  email=intent.getStringExtra(USER_LOGIN)
      var  pass=intent.getStringExtra(USER_PASSWORD)
        if((email!=null)&&(pass!=null))user= User(email+"",pass+"")
        else {
            pref = getSharedPreferences(APP_USER, MODE_PRIVATE)
            if ( pref.contains(APP_USER) ){
                    email=pref.getString(USER_LOGIN,"")
                    pass= pref.getString(USER_PASSWORD,"")
                if((email!=null)&&(pass!=null))user= User(email,pass)
                else{
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun initNavigation() {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.navFragment) as NavHostFragment? ?: return
        navController = host.navController
        val bottomBar = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomBar?.setupWithNavController(navController)
    }

    private fun initRealm() {
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
    }

    override fun refreshData() {
        app_model.reduce(Action.UIRefreshedListAction())
    }

    override fun repIsSavedChanged(rep: Rep) {
        app_model.reduce(Action.UIRepSavedChangedAction(rep))
    }

    override fun repItemClicked(rep: Rep) {
        app_model.reduce(Action.UIRepClickedAction(rep))
    }
}
