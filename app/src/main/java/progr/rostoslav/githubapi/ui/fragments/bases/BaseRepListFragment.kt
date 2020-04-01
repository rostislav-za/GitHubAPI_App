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
import progr.rostoslav.githubapi.DataManager
import progr.rostoslav.githubapi.R
import progr.rostoslav.githubapi.data.DataRepository
import progr.rostoslav.githubapi.domain.entities.Rep
import progr.rostoslav.githubapi.ui.recycler.RepItemTouchHelperCallback
import progr.rostoslav.githubapi.ui.recycler.adapters.RepAdapter
import progr.rostoslav.githubapi.ui.recycler.bases.BaseAdapterCallback

abstract class BaseRepListFragment : BaseFragment() {
    val adapter = RepAdapter()
    lateinit var list: ArrayList<Rep>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_rep_list, container, false)
    }

    override fun onStart() {

        adapter.attachCallback(object : BaseAdapterCallback<Rep> {
            override fun onItemClick(model: Rep, view: View) = onItemClicked(model, view)
            override fun onSavedClick(model: Rep, view: View) = onItemFavClicked(model)
        })
        val divider = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = adapter
        rv.addItemDecoration(divider)
        val touchCallback = RepItemTouchHelperCallback(adapter){
            onItemFavClicked(it)
        }
        val touchHelper= ItemTouchHelper(touchCallback)
        touchHelper.attachToRecyclerView(rv)
        list = DataManager.getReps()
        adapter.setList(list)
        srl.setOnRefreshListener { updateSwyped() }
        setContent()
        super.onStart()
    }

    override fun setContent() {
        srl.isRefreshing =false
        list =DataManager.getReps()
        adapter.setList(list)

    }

    open fun onItemClicked(model: Rep, itemView: View) {
        DataRepository().getRepInfo(model)
        val extras = FragmentNavigatorExtras(imageView to model.title)
        DataManager.udateRepInfo(model.toRepInfo())
        findNavController().navigate(R.id.action_repListFragment_to_detailFragment,null,null,extras)
    }

    open fun onItemFavClicked(model: Rep) {
        val copy =model.copy(isSaved = !model.isSaved)
        DataManager.updateRep(model, copy)
        adapter.updateItem(model,copy)
        //TODO прикрутить diffutils, уточнить, почему выдает ошибку.
//       val ind = MainActivity.rep_list.indexOf(model)
//        MainActivity.rep_list[ind] = copy
//        adapter.updateData( MainActivity.rep_list)
    }
open fun updateSwyped(){
    Toast.makeText(activity, "update", Toast.LENGTH_SHORT).show()
    setContent()
}
}