package com.wilsut.footballleague.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.wilsut.footballleague.R
import com.wilsut.footballleague.model.Table
import org.jetbrains.anko.*

class TableAdapter(private val tables: List<Table>) :
    RecyclerView.Adapter<TableViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewHolder {
        return TableViewHolder(
            TableUI().createView(
                AnkoContext.create(
                    parent.context,
                    parent
                )
            )
        )
    }

    override fun onBindViewHolder(holder: TableViewHolder, position: Int) {
        holder.bindItem(tables[position])
    }

    override fun getItemCount(): Int = tables.size

}

class TableUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                textView {
                    id = R.id.table_name
                    textSize = 16f
                }.lparams {
                    margin = dip(10)
                    width = dip(110)
                }

                textView {
                    id = R.id.table_played
                    textSize = 16f
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                }.lparams {
                    margin = dip(10)
                    width = dip(35)
                }

                textView {
                    id = R.id.table_win
                    textSize = 16f
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                }.lparams {
                    margin = dip(10)
                    width = dip(30)
                }

                textView {
                    id = R.id.table_draw
                    textSize = 16f
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                }.lparams {
                    margin = dip(10)
                    width = dip(30)
                }

                textView {
                    id = R.id.table_loss
                    textSize = 16f
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                }.lparams {
                    margin = dip(10)
                    width = dip(30)
                }

                textView {
                    id = R.id.table_total
                    textSize = 16f
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                }.lparams {
                    margin = dip(10)
                    width = dip(30)
                }
            }
        }
    }

}

class TableViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name: TextView = view.find(R.id.table_name)
    private val played: TextView = view.find(R.id.table_played)
    private val win: TextView = view.find(R.id.table_win)
    private val draw: TextView = view.find(R.id.table_draw)
    private val loss: TextView = view.find(R.id.table_loss)
    private val total: TextView = view.find(R.id.table_total)

    fun bindItem(tables: Table) {
        name.text = tables.name
        played.text = tables.played.toString()
        win.text = tables.win.toString()
        draw.text = tables.draw.toString()
        loss.text = tables.loss.toString()
        total.text = tables.total.toString()
    }
}