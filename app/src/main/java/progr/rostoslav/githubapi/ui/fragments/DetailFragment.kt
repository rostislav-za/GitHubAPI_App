package progr.rostoslav.githubapi.ui.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.include_recycler.*
import progr.rostoslav.githubapi.ui.DataManager
import progr.rostoslav.githubapi.R
import progr.rostoslav.githubapi.entities.Commit
import progr.rostoslav.githubapi.entities.Rep
import progr.rostoslav.githubapi.ui.FollowerView
import progr.rostoslav.githubapi.ui.recycler.adapters.CommitAdapter

class DetailFragment : Fragment(R.layout.fragment_detail), FollowerView {
    val commitAdapter = CommitAdapter()
    var list = listOf<Commit>()
    var repos_Info: Rep = DataManager.getRepInfo()

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
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        val divider = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        rv.layoutManager = LinearLayoutManager(activity)
        commitAdapter.setList(list)
        rv.adapter = commitAdapter
        rv.addItemDecoration(divider)
        setContent()
    }

    fun setContent() {
        repos_Info = DataManager.getRepInfo()
        fr_tv_rep_name.text = repos_Info.title
        fr_tv_author_name.text = repos_Info.author + "/"
        fr_iv_fav.setImageResource(repos_Info.isSavedRes)
        repos_Info.lang
        fr_tv_info.text = "(${repos_Info.lang}) ${repos_Info.description}"
        fr_iv_author.apply {
            transitionName = repos_Info.title
            Glide.with(context)
                .load(repos_Info.autor_img)
                .apply(RequestOptions.circleCropTransform())
                .into(this)
        }
        list = repos_Info.commits
        commitAdapter.setList(list)
    }

    override fun updateView() = setContent()
}

