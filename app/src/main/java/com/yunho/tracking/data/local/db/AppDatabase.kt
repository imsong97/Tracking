package com.yunho.tracking.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yunho.tracking.data.model.TrackingDataEntity

@Database(entities = [TrackingDataEntity::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): DAO

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class){
                    instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "db.db").build()
//                        .allowMainThreadQueries()
//                        .fallbackToDestructiveMigration()
                }
            }
            return instance
        }
    }
}