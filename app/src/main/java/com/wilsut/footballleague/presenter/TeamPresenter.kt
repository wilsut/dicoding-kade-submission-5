package com.wilsut.footballleague.presenter

import com.google.gson.Gson
import com.wilsut.footballleague.api.ApiRepository
import com.wilsut.footballleague.api.TheSportDBApi
import com.wilsut.footballleague.model.LeagueResponse
import com.wilsut.footballleague.view.TeamView
import com.wilsut.footballleague.model.TeamResponse
import com.wilsut.footballleague.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TeamPresenter(
    private val view: TeamView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getTeamList(league: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequestAsync(TheSportDBApi.getTeams(league)).await(),
                TeamResponse::class.java
            )

            view.showTeamList(data.teams)
            view.hideLoading()
        }
    }

    fun searchTeams(query: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequestAsync(TheSportDBApi.searchTeams(query)).await(),
                TeamResponse::class.java
            )

            view.showTeamList(data.teams)
            view.hideLoading()
        }
    }
}