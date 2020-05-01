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
import progr.rostoslav.githubapi.entities.Rep
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

    override fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    fun clearSharedPref() {
        val pref = getSharedPreferences(APP_USER, MODE_PRIVATE)
        val editor = pref.edit()
        editor.clear()
        editor.apply()
    }

    override fun refreshData() = app_model.reduce(Action.UIRefreshedListAction())

    override fun repIsSavedChanged(rep: Rep) = app_model.reduce(Action.UIRepSavedChangedAction(rep))

    override fun repItemClicked(rep: Rep) = app_model.reduce(Action.UIRepClickedAction(rep))

    override fun onNewIntent(intent: Intent?) {
        setContent()
        super.onNewIntent(intent)
    }

    override fun onStop() {
        super.onStop()
        saveContet()
    }

    open fun initRealm() {
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
    }

    open fun initNavigation(nav_fragment: Int, nav_view: Int) {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_toolbarr_menu, menu)
        return true
    }

    fun saveContet() = app_model.onDestroy()
}
