package com.wilsut.footballleague.view

import com.wilsut.footballleague.model.League

interface LeagueDetailView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueDetail(data: List<League>)
}