package com.wilsut.footballleague.view

import com.wilsut.footballleague.model.Team

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}