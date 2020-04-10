package progr.rostoslav.githubapi.ui.activityes

import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*
import progr.rostoslav.githubapi.R
import progr.rostoslav.githubapi.data.local.USER_LOGIN
import progr.rostoslav.githubapi.data.local.USER_PASSWORD
import progr.rostoslav.githubapi.domain.AppModel
import progr.rostoslav.githubapi.ui.DataManager

class MainActivity : BaseActivity() {

    override fun setContent() {
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

    override fun saveContet() = app_model.onDestroy()

}
