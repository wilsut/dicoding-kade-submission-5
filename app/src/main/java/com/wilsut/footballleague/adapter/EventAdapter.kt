package com.wilsut.footballleague.adapter

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.wilsut.footballleague.model.Event
import org.jetbrains.anko.*

class EventAdapter(private val events: List<Event>, private val listener: (Event) -> Unit) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): EventViewHolder {
        return EventViewHolder(EventUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bindItems(events[position])
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvEvent = itemView.findViewById<TextView>(EventUI.tv_event)
        private val tvDate = itemView.findViewById<TextView>(EventUI.tv_date)
        private val tvHome = itemView.findViewById<TextView>(EventUI.tv_home)
        private val tvAway = itemView.findViewById<TextView>(EventUI.tv_away)
        private val tvScoreHome = itemView.findViewById<TextView>(EventUI.tv_home_score)
        private val tvScoreAway = itemView.findViewById<TextView>(EventUI.tv_away_score)

        fun bindItems(event: Event) {
            tvEvent.text = event.event
            tvDate.text = event.dateEvent
            tvHome.text = event.homeTeam
            tvAway.text = event.awayTeam
            event.awayScore.let {
                tvScoreAway.text = event.awayScore
            }
            event.homeScore.let {
                tvScoreHome.text = event.homeScore
            }
            itemView.setOnClickListener {
                listener(event)
            }
        }
    }
}

class EventUI : AnkoComponent<ViewGroup> {

    companion object {
        const val tv_date = 1
        const val tv_home = 2
        const val tv_away = 3
        const val tv_home_score = 4
        const val tv_away_score = 5
        const val tv_event = 6
    }

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        linearLayout {
            lparams(matchParent, wrapContent) {
                orientation = LinearLayout.VERTICAL
                padding = (dip(8))
            }

            textView {
                id = tv_event
                gravity = Gravity.CENTER
            }.lparams(matchParent, wrapContent)

            textView {
                id = tv_date
                gravity = Gravity.CENTER
            }.lparams(matchParent, wrapContent)

            linearLayout {
                lparams(matchParent, wrapContent) {
                    orientation = LinearLayout.HORIZONTAL
                }

                textView {
                    id = tv_home
                    gravity = Gravity.END
                    typeface = Typeface.DEFAULT_BOLD
                }.lparams(dip(0), wrapContent) {
                    weight = 1F
                }

                textView {
                    id = tv_home_score
                    gravity = Gravity.CENTER
                }.lparams(dip(0), wrapContent) {
                    weight = 0.5F
                }

                textView("VS") {
                    gravity = Gravity.CENTER
                }.lparams(dip(0), wrapContent) {
                    weight = 0.5F
                }

                textView {
                    id = tv_away_score
                    gravity = Gravity.CENTER
                }.lparams(dip(0), wrapContent) {
                    weight = 0.5F
                }

                textView {
                    id = tv_away
                    gravity = Gravity.START
                    typeface = Typeface.DEFAULT_BOLD
                }.lparams(dip(0), wrapContent) {
                    weight = 1F
                }
            }
        }
    }
}