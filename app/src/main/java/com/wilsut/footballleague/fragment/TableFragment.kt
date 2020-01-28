package com.wilsut.footballleague.fragment

import android.graphics.Typeface
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
import com.wilsut.footballleague.adapter.TableAdapter
import com.wilsut.footballleague.api.ApiRepository
import com.wilsut.footballleague.model.Table
import com.wilsut.footballleague.presenter.TablePresenter
import com.wilsut.footballleague.util.invisible
import com.wilsut.footballleague.util.visible
import com.wilsut.footballleague.view.TableView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TableFragment : Fragment(), TableView {

    private var tables: MutableList<Table> = mutableListOf()
    private lateinit var presenter: TablePresenter
    private lateinit var adapter: TableAdapter
    private lateinit var listTable: RecyclerView
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
        presenter = TablePresenter(
            this,
            request,
            gson
        )

        return TableFragmentUI().createView(AnkoContext.create(ctx, this))
    }

    inner class TableFragmentUI : AnkoComponent<TableFragment> {
        override fun createView(ui: AnkoContext<TableFragment>) = with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL
                topPadding = dip(16)
                leftPadding = dip(16)
                rightPadding = dip(16)

                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    padding = dip(16)
                    orientation = LinearLayout.HORIZONTAL

                    textView {
                        id = R.id.table_name
                        textSize = 16f
                        text = "Name"
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams {
                        margin = dip(10)
                        width = dip(110)
                    }

                    textView {
                        id = R.id.table_played
                        textSize = 10f
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                        text = "Played"
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams {
                        margin = dip(10)
                        width = dip(35)
                    }

                    textView {
                        id = R.id.table_win
                        textSize = 10f
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                        text = "Win"
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams {
                        margin = dip(10)
                        width = dip(30)
                    }

                    textView {
                        id = R.id.table_draw
                        textSize = 10f
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                        text = "Draw"
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams {
                        margin = dip(10)
                        width = dip(30)
                    }

                    textView {
                        id = R.id.table_loss
                        textSize = 10f
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                        text = "Loss"
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams {
                        margin = dip(10)
                        width = dip(30)
                    }

                    textView {
                        id = R.id.table_total
                        textSize = 10f
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                        text = "Total"
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams {
                        margin = dip(10)
                        width = dip(30)
                    }
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

                        listTable = recyclerView {
                            lparams(width = matchParent, height = wrapContent)
                            layoutManager = LinearLayoutManager(context)
                        }

                        progressBar = progressBar {
                        }.lparams {
                            centerHorizontally()
                        }
                    }
                }

                adapter = TableAdapter(tables)

                listTable.adapter = adapter

                presenter.getTables(idLeague)

                swipeRefresh.onRefresh {
                    presenter.getTables(idLeague)
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

    override fun showTableList(data: List<Table>) {
        swipeRefresh.isRefreshing = false
        tables.clear()
        tables.addAll(data)
        adapter.notifyDataSetChanged()
    }
}