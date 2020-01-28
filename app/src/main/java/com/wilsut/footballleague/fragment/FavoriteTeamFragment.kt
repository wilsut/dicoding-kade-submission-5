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
import com.wilsut.footballleague.R
import com.wilsut.footballleague.adapter.TeamAdapter
import com.wilsut.footballleague.db.database
import com.wilsut.footballleague.main.TeamActivity
import com.wilsut.footballleague.model.Team
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavoriteTeamFragment : Fragment() {

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var adapter: TeamAdapter
    private lateinit var listTeam: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return TeamFragmentUI().createView(AnkoContext.create(ctx, this))
    }

    private fun showFavorite() {
        teams.clear()
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(com.wilsut.footballleague.db.Team.TABLE_TEAM)
            val team = result.parseList(classParser<com.wilsut.footballleague.db.Team>())
            teams.addAll(team.map { Team(it.idTeam, it.name, it.badge, it.description) })
            adapter.notifyDataSetChanged()
        }
    }

    inner class TeamFragmentUI : AnkoComponent<FavoriteTeamFragment> {
        override fun createView(ui: AnkoContext<FavoriteTeamFragment>) = with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL
                topPadding = dip(16)
                leftPadding = dip(16)
                rightPadding = dip(16)

                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(
                        R.color.colorAccent,
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
                    }
                }

                adapter = TeamAdapter(teams) {
                    startActivity(intentFor<TeamActivity>("team" to it))
                }

                listTeam.adapter = adapter

                showFavorite()

                swipeRefresh.onRefresh {
                    showFavorite()
                }
            }
        }
    }
}