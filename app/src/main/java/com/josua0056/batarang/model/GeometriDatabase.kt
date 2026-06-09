package com.josua0056.batarang.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Geometri::class], version = 1, exportSchema = false)
abstract class GeometriDatabase : RoomDatabase() {
    abstract fun geometriDao(): GeometriDao

    companion object {
        @Volatile
        private var Instance: GeometriDatabase? = null

        fun getDatabase(context: Context): GeometriDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, GeometriDatabase::class.java, "geometri_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
