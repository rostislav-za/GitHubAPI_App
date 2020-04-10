package progr.rostoslav.githubapi.ui.activityes

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.RealmConfiguration
import progr.rostoslav.githubapi.Action
import progr.rostoslav.githubapi.R
import progr.rostoslav.githubapi.domain.AppModel
import progr.rostoslav.githubapi.entities.Rep
import progr.rostoslav.githubapi.ui.FollowerView

abstract class BaseActivity:AppCompatActivity(),ActionProvider,FollowerView {
lateinit var app_model:AppModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContent()
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
    override fun onStop() {
        super.onStop()
    saveContet()}

    open fun initRealm() {
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
    }


    open  fun setContent(){}
    open fun saveContet(){}
}