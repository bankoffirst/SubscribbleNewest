package com.example.subscribble.database.module

import android.content.Context
import androidx.room.Room
import com.example.subscribble.database.CardDao
import com.example.subscribble.database.SubDao
import com.example.subscribble.database.SubscriptionDatabase
import com.example.subscribble.database.UsageDao
import com.example.subscribble.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun getRepository(cardDao: CardDao,subDao: SubDao, usageDao: UsageDao): Repository{
        return Repository(cardDao,subDao, usageDao)
    }

    @Singleton
    @Provides
    fun getUsageDao(database: SubscriptionDatabase): UsageDao{
        return database.usageDao()
    }

    @Singleton
    @Provides
    fun getCardDao(database: SubscriptionDatabase): CardDao{
        return database.cardDao()
    }

    @Singleton
    @Provides
    fun getSubDao(database: SubscriptionDatabase): SubDao{
        return database.subDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): SubscriptionDatabase{
        return Room.databaseBuilder(
            context.applicationContext, SubscriptionDatabase::class.java, "subscription_db"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }

}