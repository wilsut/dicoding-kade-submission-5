package com.wilsut.footballleague.db

data class Team(
    val id: Long?,
    val idTeam: String?,
    val name: String?,
    val badge: String?,
    val description: String?
) {

    companion object {
        const val TABLE_TEAM: String = "TABLE_TEAM"
        const val ID: String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
        const val NAME: String = "NAME"
        const val BADGE: String = "BADGE"
        const val DESCRIPTION: String = "DESCRIPTION"
    }
}