package com.wilsut.footballleague.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTabHost
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.fragmentTabHost

class TeamMenuFragment : Fragment() {

    private lateinit var frameTab: FrameLayout
    private lateinit var tabHost: FragmentTabHost

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return TeamMenuFragmentUI().createView(AnkoContext.create(ctx, this))
    }

    inner class TeamMenuFragmentUI : AnkoComponent<TeamMenuFragment> {
        override fun createView(ui: AnkoContext<TeamMenuFragment>) = with(ui) {
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

                tabHost.setup(ctx, childFragmentManager, android.R.id.tabcontent)
                tabHost.addTab(
                    tabHost.newTabSpec("Search Team")
                        .setIndicator("Search Team"),
                    SearchTeamFragment::class.java, null
                )
                tabHost.addTab(
                    tabHost.newTabSpec("Favorite Team")
                        .setIndicator("Favorite Team"),
                    FavoriteTeamFragment::class.java, null
                )
            }
        }
    }
}