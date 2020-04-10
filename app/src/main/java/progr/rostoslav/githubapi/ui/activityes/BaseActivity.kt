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
import progr.rostoslav.githubapi.Action
import progr.rostoslav.githubapi.R
import progr.rostoslav.githubapi.data.local.APP_USER
import progr.rostoslav.githubapi.domain.AppModel
import progr.rostoslav.githubapi.entities.Rep
import progr.rostoslav.githubapi.ui.FollowerView

abstract class BaseActivity : AppCompatActivity(), ActionProvider, FollowerView {
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
        val pref = getSharedPreferences(APP_USER, AppCompatActivity.MODE_PRIVATE)
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

    open fun setContent() {}
    open fun saveContet() {}
}