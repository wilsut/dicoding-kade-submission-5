package com.wilsut.footballleague.presenter

import com.google.gson.Gson
import com.wilsut.footballleague.api.ApiRepository
import com.wilsut.footballleague.api.TheSportDBApi
import com.wilsut.footballleague.model.TableResponse
import com.wilsut.footballleague.util.CoroutineContextProvider
import com.wilsut.footballleague.view.TableView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TablePresenter(
    private val view: TableView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getTables(idLeague: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequestAsync(TheSportDBApi.getTables(idLeague)).await(),
                TableResponse::class.java
            )

            view.showTableList(data.table)
            view.hideLoading()
        }
    }
}