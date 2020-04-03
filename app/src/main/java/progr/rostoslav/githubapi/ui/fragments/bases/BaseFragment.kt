package progr.rostoslav.githubapi.ui.fragments.bases

import android.widget.Toast
import androidx.fragment.app.Fragment
import progr.rostoslav.githubapi.ui.FollowerView

abstract class BaseFragment : Fragment(), FollowerView{

    override fun onStart() {
        init()
        toFollowView(this)
        setContent()
        super.onStart()
    }

    override fun onStop() {
        toUnfollowView( this)
        super.onStop()
    }

    open fun init(){}
    open fun setContent(){}

    override fun updateView() {
        setContent()
        Toast.makeText(activity,"fupdated", Toast.LENGTH_SHORT).show()
    }
}
