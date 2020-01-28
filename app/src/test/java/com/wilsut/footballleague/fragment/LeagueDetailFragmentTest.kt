package com.wilsut.footballleague.fragment

import com.google.gson.Gson
import com.wilsut.footballleague.TestContextProvider
import com.wilsut.footballleague.api.ApiRepository
import com.wilsut.footballleague.model.League
import com.wilsut.footballleague.model.LeagueResponse
import com.wilsut.footballleague.presenter.LeagueDetailPresenter
import com.wilsut.footballleague.view.LeagueDetailView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LeagueDetailFragmentTest {
    @Mock
    private lateinit var view: LeagueDetailView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: LeagueDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = LeagueDetailPresenter(
            view,
            apiRepository,
            gson,
            TestContextProvider()
        )
    }

    @Test
    fun getLeagueDetail() {
        val leagues: MutableList<League> = mutableListOf()
        val response = LeagueResponse(leagues)
        val id = "1234"

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson(
                    "",
                    LeagueResponse::class.java
                )
            ).thenReturn(response)

            presenter.getLeagueDetails(id)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showLeagueDetail(leagues)
            Mockito.verify(view).hideLoading()
        }
    }
}