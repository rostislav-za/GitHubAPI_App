package progr.rostoslav.githubapi.ui.fragments

import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.item_rep.*
import progr.rostoslav.githubapi.DataManager
import progr.rostoslav.githubapi.R
import progr.rostoslav.githubapi.data.DataRepository
import progr.rostoslav.githubapi.domain.entities.Rep
import progr.rostoslav.githubapi.ui.fragments.bases.BaseRepListFragment

class SavedRepListFragment : BaseRepListFragment(){
        override fun onItemFavClicked(model: Rep) {
            // super.onItemFavClicked(model)
            val copy =model.copy(isSaved = !model.isSaved)
            DataManager.updateRep(model, copy)
            adapter.deleteItem(model)
        }

        override fun setContent() {
            list=DataManager.getFauvReps()
            adapter.setList(list)
        }

        override fun onItemClicked(model: Rep, itemView: View) {
            DataRepository().getRepInfo(model)
            val extras = FragmentNavigatorExtras(imageView to model.title)
            DataManager.udateRepInfo(model.toRepInfo())
            findNavController().navigate(R.id.action_savedRepListFragment_to_detailFragment,null,null,extras)
        }
    }



