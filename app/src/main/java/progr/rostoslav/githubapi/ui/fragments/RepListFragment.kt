package progr.rostoslav.githubapi.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_rep_list.*
import kotlinx.android.synthetic.main.include_recycler.*
import kotlinx.android.synthetic.main.item_rep.*
import progr.rostoslav.githubapi.Action
import progr.rostoslav.githubapi.R
import progr.rostoslav.githubapi.entities.Rep
import progr.rostoslav.githubapi.ui.DataManager
import progr.rostoslav.githubapi.ui.FollowerView
import progr.rostoslav.githubapi.ui.activityes.ActionProvider
import progr.rostoslav.githubapi.ui.recycler.adapters.RepAdapter
import progr.rostoslav.githubapi.ui.recycler.bases.BaseAdapterCallback

class RepListFragment : Fragment(R.layout.fragment_rep_list), FollowerView {
    val adapter = RepAdapter()
    lateinit var list: ArrayList<Rep>
    override fun onStart() {
        init()
        toFollowView(this)
        setContent()
        super.onStart()
    }

    override fun onStop() {
        toUnfollowView(this)
        super.onStop()
    }

    fun init() {
        adapter.attachCallback(object : BaseAdapterCallback<Rep> {
            override fun onItemClick(model: Rep, view: View) {
                (activity as ActionProvider).reduce(Action.UIRepClickedAction(model))
                val extras = FragmentNavigatorExtras(imageView to model.title)
                findNavController().navigate(
                    R.id.action_repListFragment_to_detailFragment, null, null, extras
                )
            }

            override fun onSavedClick(model: Rep, view: View) {
                (activity as ActionProvider).reduce(Action.UIRepSavedChangedAction(model))
                updateItem(model)
            }
        })
        val divider = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = adapter
        rv.addItemDecoration(divider)
        srl.setOnRefreshListener { (activity as ActionProvider).reduce(Action.UIRefreshedListAction()) }
        srl.isRefreshing = true
    }

    override fun updateView() = setContent()

    fun setContent() {
        srl.isRefreshing = false
        list = DataManager.getReps()
        adapter.setList(list)
    }

     fun updateItem(item: Rep) {
        val list = adapter.getList()
        val last_r = list.findLast { (it.title == item.title) && (it.author == item.author) }
        if (last_r != null) adapter.updateItem(last_r, item.copy(isSaved = !item.isSaved))
    }

}
