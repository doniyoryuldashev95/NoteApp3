package com.example.noteapp.room

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WordEntity::class]
    , version = 2
,autoMigrations = [AutoMigration (from = 1, to = 2)])
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDAO

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "android_user_control_db"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}