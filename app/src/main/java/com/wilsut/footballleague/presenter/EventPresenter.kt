package com.wilsut.footballleague.presenter

import com.google.gson.Gson
import com.wilsut.footballleague.api.ApiRepository
import com.wilsut.footballleague.api.TheSportDBApi
import com.wilsut.footballleague.view.EventView
import com.wilsut.footballleague.model.EventResponse
import com.wilsut.footballleague.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EventPresenter(
    private val view: EventView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getNextEvents(idLeague: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequestAsync(TheSportDBApi.getNextEvents(idLeague)).await(),
                EventResponse::class.java
            )

            view.showEventList(data.events)
            view.hideLoading()
        }
    }

    fun getPreviousEvents(idLeague: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequestAsync(TheSportDBApi.getPreviousEvents(idLeague)).await(),
                EventResponse::class.java
            )

            view.showEventList(data.events)
            view.hideLoading()
        }
    }

    fun searchEvents(query: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequestAsync(TheSportDBApi.searchEvents(query)).await(),
                EventResponse::class.java
            )

            view.showEventList(data.event)
            view.hideLoading()
        }
    }

    fun getEventDetails(id: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequestAsync(TheSportDBApi.getEventDetails(id)).await(),
                EventResponse::class.java
            )

            view.showEventList(data.events)
            view.hideLoading()
        }
    }
}