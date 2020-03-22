package progr.rostoslav.githubapi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import progr.rostoslav.githubapi.R
import progr.rostoslav.githubapi.data.DataRepository

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavigation()
        initData()
    }
    private fun initNavigation() {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.navFragment) as NavHostFragment? ?: return
        navController = host.navController
        val bottomBar = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomBar?.setupWithNavController(navController)
    }

    private  fun initData(){
        val dr= DataRepository()
        dr.getReps("octokit")
        dr.getRepInfo("rostislav-za", "GitHubAPI")
        dr.getCommits(10)
    }
}
