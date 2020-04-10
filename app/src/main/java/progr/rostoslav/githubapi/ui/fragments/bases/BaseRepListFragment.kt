package progr.rostoslav.githubapi.ui.fragments.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import progr.rostoslav.githubapi.ui.activityes.ActionProvider
import progr.rostoslav.githubapi.ui.recycler.RepItemTouchHelperCallback
import progr.rostoslav.githubapi.ui.recycler.adapters.RepAdapter
import progr.rostoslav.githubapi.ui.recycler.bases.BaseAdapterCallback

abstract class BaseRepListFragment : BaseFragment() {
    val adapter = RepAdapter()
    lateinit var list: ArrayList<Rep>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_rep_list, container, false)

    override fun init() {
        setListeners()
        initRecyclerView()

        srl.setOnRefreshListener { refreshSwyped() }
        srl.isRefreshing = true
    }

    open fun initRecyclerView() {
        val divider = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = adapter
        rv.addItemDecoration(divider)
    }

    open fun setListeners() {
        adapter.attachCallback(object : BaseAdapterCallback<Rep> {
            override fun onItemClick(model: Rep, view: View) {
                (activity as ActionProvider).repItemClicked(model)
                val extras = FragmentNavigatorExtras(imageView to model.title)
                findNavController().navigate(
                    R.id.action_repListFragment_to_detailFragment, null, null, extras
                )
            }

            override fun onSavedClick(model: Rep, view: View) {
                (activity as ActionProvider).repIsSavedChanged(model)
                updateItem(model)
            }
        })
        val touchCallback = RepItemTouchHelperCallback(adapter) {
            (activity as ActionProvider).repIsSavedChanged(it)
            adapter.updateItem(it, it.copy(isSaved = !it.isSaved))
        }
        val touchHelper = ItemTouchHelper(touchCallback)
        touchHelper.attachToRecyclerView(rv)
    }

    override fun setContent() {
        srl.isRefreshing = false
        list = DataManager.getReps()
        adapter.setList(list)
    }

    open fun refreshSwyped() {
        (activity as ActionProvider).refreshData()
    }

    open fun updateItem(item: Rep) {
        val list = adapter.getList()
        val last_r = list.findLast { (it.title == item.title) && (it.author == item.author) }
        if (last_r != null) adapter.updateItem(last_r, item.copy(isSaved = !item.isSaved))
    }
}