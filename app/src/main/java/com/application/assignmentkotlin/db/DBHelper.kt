package com.application.assignmentkotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaskDataToSave::class], version = 1, exportSchema = false )
 abstract class DBHelper : RoomDatabase() {

    abstract fun taskDao(): TaskDao
   // abstract fun ownerDao(): OwnerDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: DBHelper? = null

        fun getDatabase(context: Context): DBHelper {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DBHelper::class.java,
                    "TaskAssignment"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}