package com.wilsut.footballleague.db

data class Favorite(
    val id: Long?,
    val idEvent: String?,
    val event: String?,
    val dateEvent: String?,
    val homeTeam: String?,
    val awayTeam: String?,
    val homeScore: String?,
    val awayScore: String?,
    val homeGoalDetails: String?,
    val awayGoalDetails: String?,
    val homeLineGk: String?,
    val awayLineGk: String?,
    val homeLineDef: String?,
    val awayLineDef: String?,
    val homeLineMid: String?,
    val awayLineMid: String?,
    val homeLineFor: String?,
    val awayLineFor: String?,
    val homeSubs: String?,
    val awaySubs: String?
) {

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val EVENT: String = "EVENT"
        const val DATE: String = "DATE"
        const val HOME_TEAM: String = "HOME_TEAM"
        const val AWAY_TEAM: String = "AWAY_TEAM"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_SCORE: String = "AWAY_SCORE"
        const val HOME_GOAL_DETAILS: String = "HOME_GOAL_DETAILS"
        const val AWAY_GOAL_DETAILS: String = "AWAY_GOAL_DETAILS"
        const val HOME_LINE_GK: String = "HOME_LINE_GK"
        const val AWAY_LINE_GK: String = "AWAY_LINE_GK"
        const val HOME_LINE_DEF: String = "HOME_LINE_DEF"
        const val AWAY_LINE_DEF: String = "AWAY_LINE_DEF"
        const val HOME_LINE_MID: String = "HOME_LINE_MID"
        const val AWAY_LINE_MID: String = "AWAY_LINE_MID"
        const val HOME_LINE_FOR = "HOME_LINE_FOR"
        const val AWAY_LINE_FOR = "AWAY_LINE_FOR"
        const val HOME_SUBS = "HOME_SUBS"
        const val AWAY_SUBS = "AWAY_SUBS"
    }
}