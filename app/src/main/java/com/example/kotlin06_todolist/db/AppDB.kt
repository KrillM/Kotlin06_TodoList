package com.example.kotlin06_todolist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(TodoEntity::class), version = 1) // 조건 1
abstract class AppDB : RoomDatabase() { // 조건 2

    abstract fun getTodoDao() : TodoDao // 조건 3

    companion object{
        val dbName = "db_todo" // 데이터베이스 이름으로 임의로 지정하여도 무관하다.
        var appDb : AppDB? = null

        fun getInstance(context : Context) : AppDB? {
            if(appDb == null){
                appDb = Room.databaseBuilder(context,
                    AppDB::class.java,
                    dbName).fallbackToDestructiveMigration().build()
            }
            return appDb
        }

    }
}