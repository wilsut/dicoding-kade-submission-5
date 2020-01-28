package com.wilsut.footballleague.model

import com.google.gson.annotations.SerializedName

data class League(
    @SerializedName("idLeague") val leagueId: String? = null,
    @SerializedName("strLeague") val league: String? = null,
    @SerializedName("strCountry") val country: String? = null,
    @SerializedName("strBadge") val badge: String? = null,
    @SerializedName("strDescriptionEN") val description: String? = null
)