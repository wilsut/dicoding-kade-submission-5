package com.wilsut.footballleague.main

import android.os.Bundle
import android.support.v4.app.FragmentTabHost
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.wilsut.footballleague.fragment.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.fragmentTabHost

class LeagueActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LeagueActivityUI().setContentView(this)
    }

    inner class LeagueActivityUI : AnkoComponent<LeagueActivity> {

        private lateinit var frameTab: FrameLayout
        private lateinit var tabHost: FragmentTabHost
        private lateinit var leagueName: String
        private lateinit var idLeague: String

        override fun createView(ui: AnkoContext<LeagueActivity>) = with(ui) {
            linearLayout {
                lparams(matchParent, matchParent) {
                    orientation = LinearLayout.VERTICAL
                }

                tabHost = fragmentTabHost {
                    id = android.R.id.tabhost

                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                        lparams(matchParent, matchParent)

                        horizontalScrollView {
                            lparams(matchParent, wrapContent)

                            tabWidget {
                                id = android.R.id.tabs
                            }
                        }

                        frameLayout {
                            id = android.R.id.tabcontent
                        }

                        frameTab = frameLayout {
                            id = android.R.id.tabcontent
                        }
                    }
                }

                leagueName = intent.getStringExtra("league_name")
                idLeague = intent.getStringExtra("id_league")
                val badge = intent.getStringExtra("badge")
                val bundle = Bundle()
                bundle.putString("league_name", leagueName)
                bundle.putString("id_league", idLeague)
                bundle.putString("badge", badge)
                tabHost.setup(ctx, supportFragmentManager, android.R.id.tabcontent)
                tabHost.addTab(
                    tabHost.newTabSpec("Team")
                        .setIndicator("Team"),
                    TeamFragment::class.java, bundle
                )
                tabHost.addTab(
                    tabHost.newTabSpec("Detail")
                        .setIndicator("Detail"),
                    LeagueDetailFragment::class.java, bundle
                )
                tabHost.addTab(
                    tabHost.newTabSpec("Previous Match")
                        .setIndicator("Previous Match"),
                    PreviousEventFragment::class.java, bundle
                )
                tabHost.addTab(
                    tabHost.newTabSpec("Next Match")
                        .setIndicator("Next Match"),
                    NextEventFragment::class.java, bundle
                )
                tabHost.addTab(
                    tabHost.newTabSpec("Classement")
                        .setIndicator("Classement"),
                    TableFragment::class.java, bundle
                )
            }
        }
    }
}