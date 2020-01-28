package com.wilsut.footballleague.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team(
    @SerializedName("idTeam") val idTeam: String? = null,
    @SerializedName("strTeam") val name: String? = null,
    @SerializedName("strTeamBadge") val badge: String? = null,
    @SerializedName("strDescriptionEN") val description: String? = null,
    @SerializedName("idLeague") val idLeague: String? = null
) : Parcelable