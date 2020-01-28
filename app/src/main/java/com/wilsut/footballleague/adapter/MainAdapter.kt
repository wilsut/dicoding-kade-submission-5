package com.wilsut.footballleague.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.wilsut.footballleague.R.id.team_badge
import com.wilsut.footballleague.R.id.team_name
import com.wilsut.footballleague.model.League
import org.jetbrains.anko.*

class MainAdapter(private val leagues: List<League>, private val listener: (League) -> Unit) :
    RecyclerView.Adapter<LeagueViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        return LeagueViewHolder(
            LeagueUI().createView(
                AnkoContext.create(
                    parent.context,
                    parent
                )
            )
        )
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(leagues[position], listener)
    }

    override fun getItemCount(): Int = leagues.size

}

class LeagueUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = team_badge
                }.lparams {
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = team_name
                    textSize = 16f
                }.lparams {
                    margin = dip(15)
                }

            }
        }
    }

}

class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val teamBadge: ImageView = view.find(team_badge)
    private val teamName: TextView = view.find(team_name)

    fun bindItem(leagues: League, listener: (League) -> Unit) {
        Picasso.get().load(leagues.badge).into(teamBadge)
        teamName.text = leagues.league
        itemView.setOnClickListener {
            listener(leagues)
        }
    }
}