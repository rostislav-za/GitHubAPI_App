package progr.rostoslav.githubapi.ui.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.include_recycler.*
import progr.rostoslav.githubapi.ui.DataManager
import progr.rostoslav.githubapi.R
import progr.rostoslav.githubapi.entities.Commit
import progr.rostoslav.githubapi.entities.RepInfo
import progr.rostoslav.githubapi.ui.fragments.bases.BaseFragment
import progr.rostoslav.githubapi.ui.recycler.adapters.CommitAdapter


class DetailFragment : BaseFragment() {
    val commitAdapter = CommitAdapter()
    var list = listOf<Commit>()
    var repos_Info: RepInfo = DataManager.getRepInfo()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_detail, container, false)


    override fun init() {
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        val divider = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        rv.layoutManager = LinearLayoutManager(activity)
        commitAdapter.setList(list)
        rv.adapter = commitAdapter
        rv.addItemDecoration(divider)
        super.init()
        setContent()
    }

    override fun setContent() {
        repos_Info = DataManager.getRepInfo()
        fr_tv_rep_name.text = repos_Info.title
        fr_tv_author_name.text = repos_Info.login + "/"
        fr_iv_fav.setImageResource(repos_Info.isSavedRes)
        repos_Info.lang
        fr_tv_info.text = "(${repos_Info.lang}) ${repos_Info.description}"
        fr_iv_author.apply {
            transitionName = repos_Info.title
            Glide.with(context)
                .load(repos_Info.avatar_url)
                .apply(RequestOptions.circleCropTransform())
                .into(this)
        }
        list = repos_Info.commits
        commitAdapter.setList(list)
        super.setContent()
    }


}

