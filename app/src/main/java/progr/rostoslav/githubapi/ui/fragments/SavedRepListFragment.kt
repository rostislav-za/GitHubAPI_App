package progr.rostoslav.githubapi.ui.fragments

import android.view.View
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.include_recycler.*
import kotlinx.android.synthetic.main.item_rep.*
import progr.rostoslav.githubapi.ui.DataManager
import progr.rostoslav.githubapi.R
import progr.rostoslav.githubapi.data.DataRepository
import progr.rostoslav.githubapi.entities.Rep
import progr.rostoslav.githubapi.entities.toRepInfo
import progr.rostoslav.githubapi.ui.ActionProvider
import progr.rostoslav.githubapi.ui.fragments.bases.BaseRepListFragment
import progr.rostoslav.githubapi.ui.recycler.RepItemTouchHelperCallback
import progr.rostoslav.githubapi.ui.recycler.bases.BaseAdapterCallback

class SavedRepListFragment : BaseRepListFragment(){
//        override fun onItemSavedClicked(rep: Rep) {
//            (activity as ActionProvider).repIsSavedChanged(rep)
//           if(!rep.isSaved) adapter.deleteItem(rep)
//        }


    override fun init() {
        adapter.attachCallback(object : BaseAdapterCallback<Rep> {
            override fun onItemClick(model: Rep, view: View){
//                onItemClicked(model, view)
                (activity as ActionProvider).repItemClicked(model)
                val extras = FragmentNavigatorExtras(imageView to model.title)
                findNavController().navigate(R.id.action_savedRepListFragment_to_detailFragment,null,null,extras)
            }
            override fun onSavedClick(model: Rep, view: View) {
//                onItemSavedClicked(model)
                (activity as ActionProvider).repIsSavedChanged(model)
                if(!model.isSaved) adapter.deleteItem(model)
/*        TODO прикрутить diffutils, уточнить, почему выдает ошибку.
       val ind = MainActivity.rep_list.indexOf(model)
        MainActivity.rep_list[ind] = copy
        adapter.updateData( MainActivity.rep_list)*/

            }
        })
        val divider = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = adapter
        rv.addItemDecoration(divider)
        val touchCallback = RepItemTouchHelperCallback(adapter) {
            (activity as ActionProvider).repIsSavedChanged(it)
            adapter.updateItem(it, it.copy(isSaved = !it.isSaved))
        }
        val touchHelper = ItemTouchHelper(touchCallback)
        touchHelper.attachToRecyclerView(rv)
        srl.setOnRefreshListener { refreshSwyped() }
        srl.isRefreshing = true
    }

    override fun setContent() {
            srl.isRefreshing = false
            list= DataManager.getFauvReps()
            adapter.setList(list)
        }

//        override fun onItemClicked(model: Rep, itemView: View) {
//            DataRepository().getRepInfo(model)
//            val extras = FragmentNavigatorExtras(imageView to model.title)
//            DataManager.udateRepInfo(model.toRepInfo())
//            findNavController().navigate(R.id.action_savedRepListFragment_to_detailFragment,null,null,extras)
//        }
    }



