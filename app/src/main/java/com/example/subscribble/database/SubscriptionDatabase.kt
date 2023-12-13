package com.example.subscribble.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [CardList::class,SubsList::class,UsageList::class],
    version = 10,
    exportSchema = false
)
abstract class SubscriptionDatabase:RoomDatabase() {

    abstract fun cardDao():CardDao
    abstract fun subDao():SubDao
    abstract fun usageDao():UsageDao

    companion object{
        @Volatile
        var INSTANCE:SubscriptionDatabase? = null
        fun getDatabase(context:Context):SubscriptionDatabase{
            return INSTANCE ?: synchronized(this){
                var instance = Room.databaseBuilder(
                    context,
                    SubscriptionDatabase::class.java,
                    "subscription_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}