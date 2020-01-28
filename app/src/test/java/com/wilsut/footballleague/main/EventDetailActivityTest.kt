package com.wilsut.footballleague.main

import com.google.gson.Gson
import com.wilsut.footballleague.TestContextProvider
import com.wilsut.footballleague.api.ApiRepository
import com.wilsut.footballleague.model.Event
import com.wilsut.footballleague.model.EventResponse
import com.wilsut.footballleague.presenter.EventPresenter
import com.wilsut.footballleague.view.EventView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class EventDetailActivityTest {
    @Mock
    private lateinit var view: EventView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: EventPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = EventPresenter(
            view,
            apiRepository,
            gson,
            TestContextProvider()
        )
    }

    @Test
    fun getEventDetail() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events, mutableListOf())
        val id = "1234"

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson(
                    "",
                    EventResponse::class.java
                )
            ).thenReturn(response)

            presenter.getEventDetails(id)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showEventList(events)
            Mockito.verify(view).hideLoading()
        }
    }
}