package progr.rostoslav.githubapi.ui.activityes

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.*
import progr.rostoslav.githubapi.Action
import progr.rostoslav.githubapi.R
import progr.rostoslav.githubapi.data.local.APP_USER
import progr.rostoslav.githubapi.data.local.USER_LOGIN
import progr.rostoslav.githubapi.data.local.USER_PASSWORD
import progr.rostoslav.githubapi.domain.AppModel
import progr.rostoslav.githubapi.ui.DataManager
import progr.rostoslav.githubapi.ui.FollowerView

class MainActivity : AppCompatActivity(), ActionProvider, FollowerView {
    lateinit var app_model: AppModel
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContent()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_toolbarr_menu, menu)
        return true
    }
    override fun onNewIntent(intent: Intent?) {
        setContent()
        super.onNewIntent(intent)
    }
    override fun onStop() {
        super.onStop()
        app_model.onDestroy()
    }

    override fun reduce(a: Action) = app_model.reduce(a)

    override fun startLoginActivity() =startActivity(Intent(this, LoginActivity::class.java))

    fun clearSharedPref() {
        val pref = getSharedPreferences(APP_USER, MODE_PRIVATE)
        val editor = pref.edit()
        editor.clear()
        editor.apply()
    }

    fun initRealm() {
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
    }

    fun initNavigation(nav_fragment: Int, nav_view: Int) {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(nav_fragment) as NavHostFragment? ?: return
        navController = host.navController
        val bottomBar = findViewById<BottomNavigationView>(nav_view)
        bottomBar?.setupWithNavController(navController)
    }

    fun setContent() {
        app_model = AppModel()
        initNavigation(R.id.navFragment, R.id.bottomNavigationView)
        initRealm()
        val email = intent?.getStringExtra(USER_LOGIN) ?: "NULL ERROR"
        val pass = intent?.getStringExtra(USER_PASSWORD) ?: "NULL ERROR"
        app_model.onCreate(email, pass, this)
        setSupportActionBar(am_toolbar)
        am_toolbar.setOnMenuItemClickListener {
            clearSharedPref()
            app_model.logOut(it)
            finishAffinity()
            true
        }
        updateView()
    }

    override fun updateView() {
        getSupportActionBar()?.title = DataManager.getUsername()
    }
}
