package com.wilsut.footballleague.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.wilsut.footballleague.R
import com.wilsut.footballleague.adapter.MainAdapter
import com.wilsut.footballleague.main.LeagueActivity
import com.wilsut.footballleague.model.League
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx

class LeagueFragment : Fragment() {

    private var leagues: MutableList<League> = mutableListOf()
    private lateinit var adapter: MainAdapter
    private lateinit var listLeague: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initData()

        return LeagueFragmentUI().createView(AnkoContext.create(ctx, this))
    }

    inner class LeagueFragmentUI : AnkoComponent<LeagueFragment> {
        override fun createView(ui: AnkoContext<LeagueFragment>) = with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL
                topPadding = dip(16)
                leftPadding = dip(16)
                rightPadding = dip(16)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listLeague = recyclerView {
                        id = R.id.list_league
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(context)
                    }

                }

                adapter = MainAdapter(leagues)
                {
                    startActivity<LeagueActivity>(
                        "league_name" to it.league,
                        "id_league" to it.leagueId,
                        "badge" to it.badge
                    )
                }

                listLeague.adapter = adapter
            }
        }
    }

    private fun initData() {
        val id = resources.getIntArray(R.array.id)
        val name = resources.getStringArray(R.array.league)
        val badge = resources.getStringArray(R.array.badge)
        leagues.clear()
        for (i in name.indices) {
            leagues.add(
                League(
                    leagueId = id[i].toString(),
                    league = name[i],
                    badge = badge[i]
                )
            )
        }
    }
}