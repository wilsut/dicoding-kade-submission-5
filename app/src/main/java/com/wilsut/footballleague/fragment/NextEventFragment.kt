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
import com.wilsut.footballleague.R
import com.wilsut.footballleague.adapter.EventAdapter
import com.wilsut.footballleague.api.ApiRepository
import com.wilsut.footballleague.main.EventDetailActivity
import com.wilsut.footballleague.model.Event
import com.wilsut.footballleague.presenter.EventPresenter
import com.wilsut.footballleague.util.invisible
import com.wilsut.footballleague.util.visible
import com.wilsut.footballleague.view.EventView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class NextEventFragment : Fragment(), EventView {

    private var events: MutableList<Event> = mutableListOf()
    private lateinit var presenter: EventPresenter
    private lateinit var adapter: EventAdapter
    private lateinit var listEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private var idLeague: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        idLeague = arguments?.getString("id_league")
        val request = ApiRepository()
        val gson = Gson()
        presenter = EventPresenter(
            this,
            request,
            gson
        )

        return EventFragmentUI().createView(AnkoContext.create(ctx, this))
    }

    inner class EventFragmentUI : AnkoComponent<NextEventFragment> {
        override fun createView(ui: AnkoContext<NextEventFragment>) = with(ui) {
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

                        progressBar = progressBar {
                        }.lparams {
                            centerHorizontally()
                        }
                    }
                }

                adapter = EventAdapter(events) {
                    startActivity(intentFor<EventDetailActivity>("event" to it))
                }
                listEvent.adapter = adapter

                presenter.getNextEvents(idLeague)

                swipeRefresh.onRefresh {
                    presenter.getNextEvents(idLeague)
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

    override fun showEventList(data: List<Event>) {
        swipeRefresh.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }
}