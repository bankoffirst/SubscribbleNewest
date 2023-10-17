package com.example.subscribble.repository

import com.example.subscribble.database.CardDao
import com.example.subscribble.database.CardList
import com.example.subscribble.database.SubDao
import com.example.subscribble.database.SubsList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(
    private val cardDao: CardDao,
    private val subDao: SubDao
) {

    fun getAllCards(): List<CardList>{
        return cardDao.getAllCards()
    }

    suspend fun insertCard(cardList: CardList){
        withContext(Dispatchers.IO){
            cardDao.insert(cardList)
        }
    }

    suspend fun deleteCard(cardList: CardList){
        withContext(Dispatchers.IO){
            cardDao.delete(cardList)
        }
    }

    suspend fun updateCard(cardList: CardList){
        withContext(Dispatchers.IO){
            cardDao.update(cardList)
        }
    }

    fun getAllSubs(): List<SubsList>{
        return subDao.getAllSubs()
    }

    suspend fun insertSubscription(subsList: SubsList){
        withContext(Dispatchers.IO){
            subDao.insert(subsList)
        }
    }

    suspend fun deleteSubscription(id: Int){
        withContext(Dispatchers.IO){
            subDao.delete(id)
        }
    }

    suspend fun updateSubscription(subList: SubsList){
        withContext(Dispatchers.IO){
            subDao.update(subList)
        }
    }

    fun sumPriceByCategory(category : String): Int{
        return subDao.sumPriceByCategory(category)
    }

}