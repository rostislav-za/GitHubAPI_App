package progr.rostoslav.githubapi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import progr.rostoslav.githubapi.DataManager
import progr.rostoslav.githubapi.FollowerView
import progr.rostoslav.githubapi.R
import progr.rostoslav.githubapi.data.DataRepository

class MainActivity : AppCompatActivity(), FollowerView {
    lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toFollowView(this)
        initNavigation()
        initData()
    }
    override fun onStop() {
        toUnfollowView(this)
        super.onStop()
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
//        dr.initData(MainActivity.this)
    }





    override fun updateView() {
        Toast.makeText(this, "update",Toast.LENGTH_SHORT).show()
    }


}
