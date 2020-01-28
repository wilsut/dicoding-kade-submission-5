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
import com.wilsut.footballleague.adapter.EventAdapter
import com.wilsut.footballleague.db.Favorite
import com.wilsut.footballleague.db.database
import com.wilsut.footballleague.main.EventDetailActivity
import com.wilsut.footballleague.model.Event
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavoriteEventFragment : Fragment() {

    private var events: MutableList<Event> = mutableListOf()
    private lateinit var adapter: EventAdapter
    private lateinit var listEvent: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return EventFragmentUI().createView(AnkoContext.create(ctx, this))
    }

    private fun showFavorite() {
        events.clear()
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            events.addAll(favorite.map {
                Event(
                    it.idEvent,
                    it.event,
                    it.homeTeam,
                    it.awayTeam,
                    it.homeScore,
                    it.awayScore,
                    it.homeGoalDetails,
                    it.homeLineGk,
                    it.homeLineDef,
                    it.homeLineMid,
                    it.homeLineFor,
                    it.homeSubs,
                    it.awayGoalDetails,
                    it.awayLineGk,
                    it.awayLineDef,
                    it.awayLineMid,
                    it.awayLineFor,
                    it.awaySubs,
                    it.dateEvent
                )
            })
            adapter.notifyDataSetChanged()
        }
    }

    inner class EventFragmentUI : AnkoComponent<FavoriteEventFragment> {
        override fun createView(ui: AnkoContext<FavoriteEventFragment>) = with(ui) {
            linearLayout {
                lparams(matchParent, matchParent) {
                    orientation = LinearLayout.VERTICAL
                    topPadding = dip(16)
                    leftPadding = dip(16)
                    rightPadding = dip(16)
                }

                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(
                        R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light
                    )

                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        listEvent = recyclerView {
                            lparams(width = matchParent, height = wrapContent)
                            layoutManager = LinearLayoutManager(context)
                        }
                    }
                }

                adapter = EventAdapter(events) {
                    startActivity(intentFor<EventDetailActivity>("event" to it))
                }

                listEvent.adapter = adapter

                showFavorite()

                swipeRefresh.onRefresh {
                    showFavorite()
                }
            }
        }
    }
}