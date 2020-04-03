package progr.rostoslav.githubapi.ui.fragments.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.include_recycler.*
import kotlinx.android.synthetic.main.item_rep.*
import progr.rostoslav.githubapi.ui.DataManager
import progr.rostoslav.githubapi.R
import progr.rostoslav.githubapi.entities.Rep
import progr.rostoslav.githubapi.ui.ActionProvider
import progr.rostoslav.githubapi.ui.ActivityView
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
//                onItemClicked(model, view)
                (activity as ActionProvider).repItemClicked(model)
                val extras = FragmentNavigatorExtras(imageView to model.title)
                findNavController().navigate(
                    R.id.action_repListFragment_to_detailFragment, null, null, extras
                )
            }

            override fun onSavedClick(model: Rep, view: View) {
//                onItemSavedClicked(model)
                (activity as ActionProvider).repIsSavedChanged(model)
                adapter.updateItem(model, model.copy(isSaved = !model.isSaved))
/*        TODO прикрутить diffutils, уточнить, почему выдает ошибку.
       val ind = MainActivity.rep_list.indexOf(model)
        MainActivity.rep_list[ind] = copy
        adapter.updateData( MainActivity.rep_list)*/

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
        (activity as ActivityView).refreshData()
        Toast.makeText(activity, "update", Toast.LENGTH_SHORT).show()
    }
}