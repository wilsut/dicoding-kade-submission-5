package com.wilsut.footballleague.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.google.gson.Gson
import com.wilsut.footballleague.R.color.colorAccent
import com.wilsut.footballleague.adapter.TeamAdapter
import com.wilsut.footballleague.api.ApiRepository
import com.wilsut.footballleague.view.TeamView
import com.wilsut.footballleague.main.TeamActivity
import com.wilsut.footballleague.model.Team
import com.wilsut.footballleague.presenter.TeamPresenter
import com.wilsut.footballleague.util.invisible
import com.wilsut.footballleague.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamFragment : Fragment(), TeamView {

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamPresenter
    private lateinit var adapter: TeamAdapter
    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private var leagueName: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        leagueName = arguments?.getString("league_name")
        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamPresenter(
            this,
            request,
            gson
        )

        return TeamFragmentUI().createView(AnkoContext.create(ctx, this))
    }

    inner class TeamFragmentUI : AnkoComponent<TeamFragment> {
        override fun createView(ui: AnkoContext<TeamFragment>) = with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL
                topPadding = dip(16)
                leftPadding = dip(16)
                rightPadding = dip(16)

                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(
                        colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light
                    )

                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        listTeam = recyclerView {
                            lparams(width = matchParent, height = wrapContent)
                            layoutManager = LinearLayoutManager(context)
                        }

                        progressBar = progressBar {
                        }.lparams {
                            centerHorizontally()
                        }
                    }
                }

                adapter = TeamAdapter(teams) {
                    startActivity(intentFor<TeamActivity>("team" to it))
                }

                listTeam.adapter = adapter

                presenter.getTeamList(leagueName)

                swipeRefresh.onRefresh {
                    presenter.getTeamList(leagueName)
                }
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
