package com.wilsut.footballleague.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.wilsut.footballleague.R
import com.wilsut.footballleague.fragment.EventMenuFragment
import com.wilsut.footballleague.fragment.LeagueFragment
import com.wilsut.footballleague.fragment.TeamMenuFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.design.bottomNavigationView

class MainActivity : AppCompatActivity() {

    companion object {
        const val fl_container = 1
        const val navigation = 2
    }

    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        relativeLayout {
            lparams(matchParent, matchParent)

            frameLayout {
                id = fl_container
            }.lparams(matchParent, wrapContent) {
                alignParentTop()
                alignParentLeft()
                alignParentRight()
                topOf(navigation)
            }

            bottomNavigation = bottomNavigationView {
                id = navigation
                inflateMenu(R.menu.navigation)
            }.lparams(dip(0), wrapContent) {
                alignParentBottom()
                alignParentLeft()
                alignParentRight()
            }

            bottomNavigation.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_league -> {
                        val menu1 = LeagueFragment()
                        supportFragmentManager.beginTransaction().replace(fl_container, menu1)
                            .commit()
                    }
                    R.id.navigation_match -> {
                        val menu2 = EventMenuFragment()
                        supportFragmentManager.beginTransaction().replace(fl_container, menu2)
                            .commit()
                    }
                    R.id.navigation_team -> {
                        val menu3 = TeamMenuFragment()
                        supportFragmentManager.beginTransaction().replace(fl_container, menu3)
                            .commit()
                    }
                }
                true
            }

            bottomNavigation.selectedItemId = R.id.navigation_league
        }
    }
}
