package com.wilsut.footballleague.main

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.wilsut.footballleague.R
import com.wilsut.footballleague.api.ApiRepository
import com.wilsut.footballleague.api.TheSportDBApi
import com.wilsut.footballleague.db.Favorite
import com.wilsut.footballleague.db.database
import com.wilsut.footballleague.model.Event
import com.wilsut.footballleague.presenter.EventPresenter
import com.wilsut.footballleague.util.invisible
import com.wilsut.footballleague.util.visible
import com.wilsut.footballleague.view.EventView
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class EventDetailActivity : AppCompatActivity(), EventView {

    private lateinit var badgeHome: ImageView
    private lateinit var badgeAway: ImageView
    private lateinit var linearLayout: LinearLayout
    private lateinit var fab: FloatingActionButton
    private var event: Event = Event()
    private var isFavorite: Boolean = false
    private lateinit var presenter: EventPresenter
    private lateinit var progressBar: ProgressBar

    private fun getTeamBadge(teamName: String?, imageView: ImageView) {
        val queue = Volley.newRequestQueue(this)
        val url = TheSportDBApi.getTeam(teamName)

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                val jsonObject = JSONObject(response)
                if (!jsonObject.isNull("teams")) {
                    val jsonArray = jsonObject.getJSONArray("teams")
                    val teamJson = jsonArray.getJSONObject(0)
                    val badge = teamJson["strTeamBadge"].toString()
                    badge.let { Picasso.get().load(badge).into(imageView) }
                }
            },
            Response.ErrorListener { })

        queue.add(stringRequest)
    }

    private fun favoriteState() {
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs(
                    "(EVENT_ID = {id})",
                    "id" to event.idEvent.toString()
                )
            val favorite = result.parseList(classParser<Favorite>())
            if (favorite.isNotEmpty()) isFavorite = true
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    Favorite.TABLE_FAVORITE,
                    Favorite.EVENT_ID to event.idEvent,
                    Favorite.EVENT to event.event,
                    Favorite.DATE to event.dateEvent,
                    Favorite.HOME_TEAM to event.homeTeam,
                    Favorite.AWAY_TEAM to event.awayTeam,
                    Favorite.HOME_SCORE to event.homeScore,
                    Favorite.AWAY_SCORE to event.awayScore,
                    Favorite.HOME_GOAL_DETAILS to event.homeGoalDetails,
                    Favorite.AWAY_GOAL_DETAILS to event.awayGoalDetails,
                    Favorite.HOME_LINE_GK to event.homeLineGk,
                    Favorite.AWAY_LINE_GK to event.awayLineGk,
                    Favorite.HOME_LINE_DEF to event.homeLineDef,
                    Favorite.AWAY_LINE_DEF to event.awayLineDef,
                    Favorite.HOME_LINE_MID to event.homeLineMid,
                    Favorite.AWAY_LINE_MID to event.awayLineMid,
                    Favorite.HOME_LINE_FOR to event.homeLineFor,
                    Favorite.AWAY_LINE_FOR to event.awayLineFor,
                    Favorite.HOME_SUBS to event.homeSubs,
                    Favorite.AWAY_SUBS to event.awaySubs
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
                    Favorite.TABLE_FAVORITE, "(EVENT_ID = {id})",
                    "id" to event.idEvent.toString()
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val request = ApiRepository()
        val gson = Gson()
        presenter = EventPresenter(
            this,
            request,
            gson
        )
        event = intent.getParcelableExtra<Event>("event")
        favoriteState()


        scrollView {
            lparams(matchParent, matchParent) {
                setPadding(dip(16), 0, dip(16), 0)
            }

            linearLayout = linearLayout {
                lparams(matchParent, wrapContent) {
                    orientation = LinearLayout.VERTICAL
                }

                textView {
                    var sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                    val date = sdf.parse(event.dateEvent)
                    sdf = SimpleDateFormat("E, d MMM yyyy", Locale.US)
                    text = sdf.format(date)
                    gravity = Gravity.CENTER
                    textAppearance = android.R.style.TextAppearance_Medium
                }.lparams(matchParent, wrapContent)

                //  Badge
                linearLayout {
                    lparams(matchParent, wrapContent) {
                        orientation = LinearLayout.HORIZONTAL
                    }

                    badgeHome = imageView {
                    }.lparams(dip(50), dip(50)) {
                        weight = 1F
                        gravity = Gravity.START
                    }

                    badgeAway = imageView {
                    }.lparams(dip(50), dip(50)) {
                        weight = 1F
                        gravity = Gravity.END
                    }
                }

                //  Team
                linearLayout {
                    lparams(matchParent, wrapContent) {
                        orientation = LinearLayout.HORIZONTAL
                    }

                    textView {
                        text = event.homeTeam
                        gravity = Gravity.START
                        textAppearance = android.R.style.TextAppearance_Medium
                    }.lparams(dip(0), wrapContent) {
                        weight = 1F
                    }

                    textView("VS") {
                        gravity = Gravity.CENTER
                        textAppearance = android.R.style.TextAppearance_Medium
                    }.lparams(dip(0), wrapContent) {
                        weight = 1F
                    }

                    textView {
                        text = event.awayTeam
                        gravity = Gravity.END
                        textAppearance = android.R.style.TextAppearance_Medium
                    }.lparams(dip(0), wrapContent) {
                        weight = 1F
                    }
                }

                view {
                    backgroundResource = R.color.colorPrimary
                }.lparams(matchParent, dip(1)) {
                    setMargins(
                        0,
                        dip(8),
                        0,
                        dip(8)
                    )
                }

                //  Goals
                linearLayout {
                    lparams(matchParent, wrapContent) {
                        orientation = LinearLayout.HORIZONTAL
                    }

                    textView {
                        text = event.homeScore
                        gravity = Gravity.START
                        textAppearance = android.R.style.TextAppearance_Medium
                    }.lparams(dip(0), wrapContent) {
                        weight = 1F
                    }

                    textView("Goals") {
                        gravity = Gravity.CENTER
                    }.lparams(dip(0), wrapContent) {
                        weight = 1F
                    }

                    textView {
                        text = event.awayScore
                        gravity = Gravity.END
                        textAppearance = android.R.style.TextAppearance_Medium
                    }.lparams(dip(0), wrapContent) {
                        weight = 1F
                    }
                }

                //  Scorer
                linearLayout {
                    lparams(matchParent, wrapContent) {
                        orientation = LinearLayout.HORIZONTAL
                    }

                    textView {
                        text = event.homeGoalDetails
                        gravity = Gravity.START
                    }.lparams(dip(0), wrapContent) {
                        weight = 1F
                    }

                    textView("Scorer") {
                        gravity = Gravity.CENTER
                    }.lparams(dip(0), wrapContent) {
                        weight = 1F
                    }

                    textView {
                        text = event.awayGoalDetails
                        gravity = Gravity.END
                    }.lparams(dip(0), wrapContent) {
                        weight = 1F
                    }
                }

                view {
                    backgroundResource = R.color.colorPrimary
                }.lparams(matchParent, dip(1)) {
                    setMargins(
                        0,
                        dip(8),
                        0,
                        dip(8)
                    )
                }

                //  Line Up GK
                linearLayout {
                    lparams(matchParent, wrapContent) {
                        orientation = LinearLayout.HORIZONTAL
                        setMargins(
                            0,
                            dip(8),
                            0,
                            dip(8)
                        )
                    }

                    textView {
                        text = event.homeLineGk
                        gravity = Gravity.START
                    }.lparams(dip(0), wrapContent) {
                        weight = 0.5F
                    }

                    textView("Goal Keeper") {
                        gravity = Gravity.CENTER
                    }.lparams(dip(0), wrapContent) {
                        weight = 0.5F
                    }

                    textView {
                        text = event.awayLineGk
                        gravity = Gravity.END
                    }.lparams(dip(0), wrapContent) {
                        weight = 0.5F
                    }
                }

                //  Line Up Defense
                linearLayout {
                    lparams(matchParent, wrapContent) {
                        orientation = LinearLayout.HORIZONTAL
                        setMargins(
                            0,
                            dip(8),
                            0,
                            dip(8)
                        )
                    }

                    textView {
                        text = event.homeLineDef
                        gravity = Gravity.START
                    }.lparams(dip(0), wrapContent) {
                        weight = 0.5F
                    }

                    textView("Defense") {
                        gravity = Gravity.CENTER
                    }.lparams(dip(0), wrapContent) {
                        weight = 0.5F
                    }

                    textView {
                        text = event.awayLineDef
                        gravity = Gravity.END
                    }.lparams(dip(0), wrapContent) {
                        weight = 0.5F
                    }
                }

                //  Line Up Midfield
                linearLayout {
                    lparams(matchParent, wrapContent) {
                        orientation = LinearLayout.HORIZONTAL
                        setMargins(
                            0,
                            dip(8),
                            0,
                            dip(8)
                        )
                    }

                    textView {
                        text = event.homeLineMid
                        gravity = Gravity.START
                    }.lparams(dip(0), wrapContent) {
                        weight = 0.5F
                    }

                    textView("Midfield") {
                        gravity = Gravity.CENTER
                    }.lparams(dip(0), wrapContent) {
                        weight = 0.5F
                    }

                    textView {
                        text = event.awayLineMid
                        gravity = Gravity.END
                    }.lparams(dip(0), wrapContent) {
                        weight = 0.5F
                    }
                }

                //  Line Up Forward
                linearLayout {
                    lparams(matchParent, wrapContent) {
                        orientation = LinearLayout.HORIZONTAL
                        setMargins(
                            0,
                            dip(8),
                            0,
                            dip(8)
                        )
                    }

                    textView {
                        text = event.homeLineFor
                        gravity = Gravity.START
                    }.lparams(dip(0), wrapContent) {
                        weight = 1F
                    }

                    textView("Forward") {
                        gravity = Gravity.CENTER
                    }.lparams(dip(0), wrapContent) {
                        weight = 1F
                    }

                    textView {
                        text = event.awayLineFor
                        gravity = Gravity.END
                    }.lparams(dip(0), wrapContent) {
                        weight = 1F
                    }
                }

                //  Line Up Subs
                linearLayout {
                    lparams(matchParent, wrapContent) {
                        orientation = LinearLayout.HORIZONTAL
                        setMargins(
                            0,
                            dip(8),
                            0,
                            dip(8)
                        )
                    }

                    textView {
                        text = event.homeSubs
                        gravity = Gravity.START
                    }.lparams(dip(0), wrapContent) {
                        weight = 1F
                    }

                    textView("Substitute") {
                        gravity = Gravity.CENTER
                    }.lparams(dip(0), wrapContent) {
                        weight = 1F
                    }

                    textView {
                        text = event.awaySubs
                        gravity = Gravity.END
                    }.lparams(dip(0), wrapContent) {
                        weight = 1F
                    }
                }

                relativeLayout {
                    lparams(matchParent, wrapContent)

                    progressBar = progressBar {
                    }.lparams {
                        centerHorizontally()
                    }
                }

                fab = floatingActionButton {
                    imageResource = R.drawable.ic_add_to_favorites
                    onClick {
                        favorite()
                    }
                }.lparams {
                    rightMargin = dip(16)
                    bottomMargin = dip(16)
                }

                presenter.getEventDetails(event.idEvent)
            }
        }

        getTeamBadge(event.homeTeam, badgeHome)
        getTeamBadge(event.awayTeam, badgeAway)
        setFavorite()
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showEventList(data: List<Event>) {
        event = data[0]
    }
}