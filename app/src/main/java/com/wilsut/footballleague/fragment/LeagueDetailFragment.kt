package com.wilsut.footballleague.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.wilsut.footballleague.api.ApiRepository
import com.wilsut.footballleague.model.League
import com.wilsut.footballleague.presenter.LeagueDetailPresenter
import com.wilsut.footballleague.util.invisible
import com.wilsut.footballleague.util.visible
import com.wilsut.footballleague.view.LeagueDetailView
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.ctx

class LeagueDetailFragment : Fragment(),
    LeagueDetailView {

    private var league: League = League()
    private lateinit var presenter: LeagueDetailPresenter
    private lateinit var progressBar: ProgressBar
    private var idLeague: String? = ""
    private lateinit var imageView: ImageView
    private lateinit var textName: TextView
    private lateinit var textDescription: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        idLeague = arguments?.getString("id_league")
        val request = ApiRepository()
        val gson = Gson()
        presenter = LeagueDetailPresenter(
            this,
            request,
            gson
        )

        return LeagueDetailFragmentUI().createView(AnkoContext.create(ctx, this))
    }

    inner class LeagueDetailFragmentUI : AnkoComponent<LeagueDetailFragment> {
        override fun createView(ui: AnkoContext<LeagueDetailFragment>) = with(ui) {
            scrollView {
                lparams(matchParent, matchParent) {
                    padding = dip(16)
                }

                linearLayout {
                    lparams(matchParent, wrapContent) {
                        orientation = LinearLayout.VERTICAL
                    }

                    imageView = imageView {
                        league.badge.let { Picasso.get().load(it).into(this) }
                    }.lparams(dip(120), dip(120)) {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }

                    textName = textView {
                        text = league.league
                        textAppearance = android.R.style.TextAppearance_Medium
                    }.lparams(wrapContent, wrapContent) {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }

                    textDescription = textView {
                        text = league.description
                    }.lparams(wrapContent, wrapContent)

                    relativeLayout {
                        lparams(matchParent, wrapContent)

                        progressBar = progressBar {
                        }.lparams {
                            centerHorizontally()
                        }
                    }
                }

                presenter.getLeagueDetails(idLeague)
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showLeagueDetail(data: List<League>) {
        league = data[0]
        league.badge.let { Picasso.get().load(it).into(imageView) }
        textName.text = league.league
        textDescription.text = league.description
    }
}