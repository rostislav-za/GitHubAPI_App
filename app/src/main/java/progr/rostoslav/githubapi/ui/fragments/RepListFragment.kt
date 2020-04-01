package progr.rostoslav.githubapi.ui.fragments

import android.widget.Toast
import progr.rostoslav.githubapi.FollowerView
import progr.rostoslav.githubapi.ui.fragments.bases.BaseRepListFragment

class RepListFragment : BaseRepListFragment() ,FollowerView{
    override fun onStart() {
      toFollowView(this)
        super.onStart()
    }

    override fun onStop() {
        toUnfollowView( this)
        super.onStop()
    }

    override fun updateView() {
        setContent()
        Toast.makeText(activity,"fupdated",Toast.LENGTH_SHORT).show()
    }
}
