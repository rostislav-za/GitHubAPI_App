package progr.rostoslav.githubapi.ui.fragments.bases

import androidx.fragment.app.Fragment
import progr.rostoslav.githubapi.FollowerView

abstract class BaseFragment : Fragment(){
    open fun setContent(){}
    override fun onStart() {
        super.onStart()
    }
}
