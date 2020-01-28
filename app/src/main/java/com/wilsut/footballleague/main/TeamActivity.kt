package com.wilsut.footballleague.main

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.LinearLayout
import com.squareup.picasso.Picasso
import com.wilsut.footballleague.R
import com.wilsut.footballleague.db.database
import com.wilsut.footballleague.db.Team
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.sdk27.coroutines.onClick

class TeamActivity : AppCompatActivity() {

    private var team: com.wilsut.footballleague.model.Team = com.wilsut.footballleague.model.Team()
    private lateinit var linearLayout: LinearLayout
    private lateinit var fab: FloatingActionButton
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        team = intent.getParcelableExtra<com.wilsut.footballleague.model.Team>("team")
        favoriteState()

        scrollView {
            lparams(matchParent, matchParent) {
                padding = dip(16)
            }

            linearLayout = linearLayout {
                lparams(matchParent, wrapContent) {
                    orientation = LinearLayout.VERTICAL
                }

                imageView {
                    team.badge.let { Picasso.get().load(it).into(this) }
                }.lparams(dip(120), dip(120)) {
                    gravity = Gravity.CENTER_HORIZONTAL
                }

                textView {
                    text = team.name
                    textAppearance = android.R.style.TextAppearance_Medium
                }.lparams(wrapContent, wrapContent) {
                    gravity = Gravity.CENTER_HORIZONTAL
                }

                textView {
                    text = team.description
                }.lparams(wrapContent, wrapContent)

                fab = floatingActionButton {
                    imageResource = R.drawable.ic_add_to_favorites
                    onClick {
                        favorite()
                    }
                }.lparams {
                    rightMargin = dip(16)
                    bottomMargin = dip(16)
                }
            }
        }

        setFavorite()
    }

    private fun favoriteState() {
        database.use {
            val result = select(Team.TABLE_TEAM)
                .whereArgs(
                    "(TEAM_ID = {id})",
                    "id" to team.idTeam.toString()
                )
            val favorite = result.parseList(classParser<Team>())
            if (favorite.isNotEmpty()) isFavorite = true
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    Team.TABLE_TEAM,
                    Team.TEAM_ID to team.idTeam,
                    Team.NAME to team.name,
                    Team.BADGE to team.badge,
                    Team.DESCRIPTION to team.description
                )
            }
            linearLayout.snackbar("Added to favorite").show()
            isFavorite = true
            setFavorite()
        } catch (e: SQLiteConstraintException) {
            linearLayout.snackbar(e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(
                    Team.TABLE_TEAM, "(TEAM_ID = {id})",
                    "id" to team.idTeam.toString()
                )
            }
            linearLayout.snackbar("Removed to favorite").show()
            isFavorite = false
            setFavorite()
        } catch (e: SQLiteConstraintException) {
            linearLayout.snackbar(e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            fab.imageResource = R.drawable.ic_added_to_favorites
        else
            fab.imageResource = R.drawable.ic_add_to_favorites
    }

    private fun favorite() {
        if (isFavorite)
            removeFromFavorite()
        else
            addToFavorite()
    }
}
