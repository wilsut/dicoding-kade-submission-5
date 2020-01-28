package com.wilsut.footballleague.model

import com.google.gson.annotations.SerializedName

data class Table(
    @SerializedName("name") val name: String? = null,
    @SerializedName("teamid") val teamId: String? = null,
    @SerializedName("played") val played: Int? = null,
    @SerializedName("win") val win: Int? = null,
    @SerializedName("draw") val draw: Int? = null,
    @SerializedName("loss") val loss: Int? = null,
    @SerializedName("total") val total: Int? = null
)