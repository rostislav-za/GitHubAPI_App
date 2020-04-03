package progr.rostoslav.githubapi.ui.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
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


class DetailFragment :BaseFragment() {
    val commitAdapter =
        CommitAdapter()
    val list=ArrayList<Commit>()
    var repos_Info: RepInfo = DataManager.getRepInfo()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_detail, container, false)


    override fun init() {
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        updateView()
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = commitAdapter
        super.init()
    }

    override fun setContent() {
        list.clear()
        repos_Info = DataManager.getRepInfo()
        list.addAll(repos_Info.commits)
        fr_tv_rep_name.text= repos_Info.title
        fr_tv_author_name.text= repos_Info.login
        fr_lang.text= repos_Info.lang
        fr_tv_info.text= repos_Info.toString()
        //  fr_iv_author.transitionName = repos_Info.title
        fr_iv_author.apply {
            transitionName = repos_Info.title
            Glide.with(context)
                .load("https://sun9-59.userapi.com/c857536/v857536300/e2f7d/NN5SjYymVew.jpg")
                .apply(RequestOptions.circleCropTransform())
                .into(this)
        }
        //Glide.with(imageView).load("https://sun9-28.userapi.com/4GMFmo1ULP3-EL7h42Qck-gqHOutuAsexp4R0Q/WUX0vDNR0RM.jpg").centerCrop().into(imageView)
        //   Glide.with(fr_iv_author).load(repos_Info.avatar_url).into(fr_iv_author)
        super.setContent()
    }

}

