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

class EventMenuFragment : Fragment() {

    private lateinit var frameTab: FrameLayout
    private lateinit var tabHost: FragmentTabHost

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return EventMenuFragmentUI().createView(AnkoContext.create(ctx, this))
    }

    inner class EventMenuFragmentUI : AnkoComponent<EventMenuFragment> {
        override fun createView(ui: AnkoContext<EventMenuFragment>) = with(ui) {
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
                    tabHost.newTabSpec("Search Match")
                        .setIndicator("Search Match"),
                    SearchEventFragment::class.java, null
                )
                tabHost.addTab(
                    tabHost.newTabSpec("Favorite Match")
                        .setIndicator("Favorite Match"),
                    FavoriteEventFragment::class.java, null
                )
            }
        }
    }
}