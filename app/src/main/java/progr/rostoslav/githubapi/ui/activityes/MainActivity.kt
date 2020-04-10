package progr.rostoslav.githubapi.ui.activityes

import android.app.Activity
import android.content.Intent
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import progr.rostoslav.githubapi.R
import progr.rostoslav.githubapi.data.local.APP_USER
import progr.rostoslav.githubapi.data.local.USER_LOGIN
import progr.rostoslav.githubapi.data.local.USER_PASSWORD
import progr.rostoslav.githubapi.domain.AppModel
import progr.rostoslav.githubapi.ui.DataManager

class MainActivity : BaseActivity() {
    lateinit var navController: NavController

    override fun setContent() {
        app_model = AppModel()
        initNavigation()
        initRealm()

        var email = intent?.getStringExtra(USER_LOGIN)?:""
        var pass = intent?.getStringExtra(USER_PASSWORD)?:""
        app_model.onCreate(email,pass,this)
        setSupportActionBar(am_toolbar)
        am_toolbar.setOnMenuItemClickListener {
            val pref = getSharedPreferences(APP_USER, AppCompatActivity.MODE_PRIVATE)
            val editor = pref.edit()
            editor.clear()
            editor.apply()
            app_model.logOut(it)
            finishAffinity()
            true
        }
        updateView()
    }

    override fun onNewIntent(intent: Intent?) {
        setContent()
        super.onNewIntent(intent)
    }

    override fun startLoginActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun updateView() {
        getSupportActionBar()?.title=DataManager.getUsername()
    }

    private fun initNavigation() {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.navFragment) as NavHostFragment? ?: return
        navController = host.navController
        val bottomBar = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomBar?.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_toolbarr_menu, menu)
        return true
    }

    override fun saveContet() {
        app_model.onDestroy()
    }
}
