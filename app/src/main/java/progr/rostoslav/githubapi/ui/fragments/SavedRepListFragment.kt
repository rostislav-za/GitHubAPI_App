package progr.rostoslav.githubapi.ui.fragments

import android.view.View
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_rep_list.*
import kotlinx.android.synthetic.main.include_recycler.*
import kotlinx.android.synthetic.main.item_rep.*
import progr.rostoslav.githubapi.ui.DataManager
import progr.rostoslav.githubapi.R
import progr.rostoslav.githubapi.entities.Rep
import progr.rostoslav.githubapi.ui.FollowerView
import progr.rostoslav.githubapi.ui.activityes.ActionProvider
import progr.rostoslav.githubapi.ui.fragments.bases.BaseRepListFragment
import progr.rostoslav.githubapi.ui.recycler.RepItemTouchHelperCallback
import progr.rostoslav.githubapi.ui.recycler.bases.BaseAdapterCallback

class SavedRepListFragment : BaseRepListFragment(), FollowerView {
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
     override fun init() {
        adapter.attachCallback(object : BaseAdapterCallback<Rep> {
            override fun onItemClick(model: Rep, view: View) {
                (activity as ActionProvider).repItemClicked(model)
                val extras = FragmentNavigatorExtras(imageView to model.title)
                findNavController().navigate(
                    R.id.action_savedRepListFragment_to_detailFragment,
                    null,
                    null,
                    extras
                )
            }

            override fun onSavedClick(model: Rep, view: View) {
                (activity as ActionProvider).repIsSavedChanged(model)
                updateItem(model)
            }
        })
        val divider = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = adapter
        rv.addItemDecoration(divider)
        val touchCallback = RepItemTouchHelperCallback(adapter) {
            (activity as ActionProvider).repIsSavedChanged(it)
            adapter.deleteItem(it)
        }
        val touchHelper = ItemTouchHelper(touchCallback)
        touchHelper.attachToRecyclerView(rv)
        srl.setOnRefreshListener { refreshSwyped() }
        srl.isRefreshing = true
    }

    override fun updateItem(item: Rep) {
        val list = adapter.getList()
        val last_r = list.findLast { (it.title == item.title) && (it.author == item.author) }
        if (last_r != null) adapter.deleteItem(last_r)
    }

    override fun setContent() {
        srl.isRefreshing = false
        list = DataManager.getSavedReps()
        adapter.setList(list)
    }
}



