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

    fun getCardById(id:Int): CardList?{
        return cardDao.getCardById(id)
    }

    fun getAllSubs(): List<SubsList>{
        return subDao.getAllSubs()
    }


    suspend fun insertSubscription(subsList: SubsList){
        withContext(Dispatchers.IO){
            subDao.insert(subsList)
        }
    }

    suspend fun deleteSubscription(subsList: SubsList){
        withContext(Dispatchers.IO){
            subDao.deleteSubscription(subsList)
        }
    }

    suspend fun updateSubscription(subList: SubsList){
        withContext(Dispatchers.IO){
            subDao.updateSubscription(subList)
        }
    }

    fun sumPriceByCategory(category : String): Float{
        return subDao.sumPriceByCategory(category)
    }

    fun getSubscriptionById(id:Int): SubsList? {
        return  subDao.getSubscriptionById(id)
    }

}