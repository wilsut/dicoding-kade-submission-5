package com.wilsut.footballleague.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) :
    ManagedSQLiteOpenHelper(ctx, "FavoriteTeam.db", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(
            Favorite.TABLE_FAVORITE, true,
            Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Favorite.EVENT_ID to TEXT + UNIQUE,
            Favorite.EVENT to TEXT,
            Favorite.DATE to TEXT,
            Favorite.HOME_TEAM to TEXT,
            Favorite.AWAY_TEAM to TEXT,
            Favorite.HOME_SCORE to TEXT,
            Favorite.AWAY_SCORE to TEXT,
            Favorite.HOME_GOAL_DETAILS to TEXT,
            Favorite.AWAY_GOAL_DETAILS to TEXT,
            Favorite.HOME_LINE_GK to TEXT,
            Favorite.AWAY_LINE_GK to TEXT,
            Favorite.HOME_LINE_DEF to TEXT,
            Favorite.AWAY_LINE_DEF to TEXT,
            Favorite.HOME_LINE_MID to TEXT,
            Favorite.AWAY_LINE_MID to TEXT,
            Favorite.HOME_LINE_FOR to TEXT,
            Favorite.AWAY_LINE_FOR to TEXT,
            Favorite.HOME_SUBS to TEXT,
            Favorite.AWAY_SUBS to TEXT
        )

        db.createTable(
            Team.TABLE_TEAM, true,
            Team.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Team.TEAM_ID to TEXT + UNIQUE,
            Team.NAME to TEXT,
            Team.BADGE to TEXT,
            Team.DESCRIPTION to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(Favorite.TABLE_FAVORITE, true)
        db.dropTable(Team.TABLE_TEAM, true)
    }
}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)