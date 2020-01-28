package com.wilsut.footballleague.view

import com.wilsut.footballleague.model.Event

interface EventView {
    fun showLoading()
    fun hideLoading()
    fun showEventList(data: List<Event>)
}