package com.wilsut.footballleague.presenter

import com.google.gson.Gson
import com.wilsut.footballleague.api.ApiRepository
import com.wilsut.footballleague.api.TheSportDBApi
import com.wilsut.footballleague.view.LeagueDetailView
import com.wilsut.footballleague.model.LeagueResponse
import com.wilsut.footballleague.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeagueDetailPresenter(
    private val view: LeagueDetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getLeagueDetails(id: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequestAsync(TheSportDBApi.getLeagueDetails(id)).await(),
                LeagueResponse::class.java
            )

            view.showLeagueDetail(data.leagues)
            view.hideLoading()
        }
    }

}