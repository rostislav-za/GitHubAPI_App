package progr.rostoslav.githubapi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.realm.Realm
import io.realm.RealmConfiguration
import progr.rostoslav.githubapi.R
import progr.rostoslav.githubapi.data.DataRepository
import progr.rostoslav.githubapi.domain.AppModel
import progr.rostoslav.githubapi.entities.Rep

class MainActivity : AppCompatActivity(), ActivityView, ActionProvider {
    lateinit var navController: NavController
    val app_model = AppModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavigation()
        initRealm()
        app_model.onCreate()
    }

    override fun onStop() {
        super.onStop()
        app_model.onDestroy()
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
        app_model.updateData()
    }

    override fun repIsSavedChanged(rep: Rep) {
        app_model.repIsSavedChanged(rep)
    }

    override fun repItemClicked(rep: Rep) {
        app_model.repItemClicked(rep)
    }


}
