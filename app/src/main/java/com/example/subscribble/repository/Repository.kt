package com.example.subscribble.repository

import com.example.subscribble.database.CardDao
import com.example.subscribble.database.CardList
import com.example.subscribble.database.SubDao
import com.example.subscribble.database.SubsList
import com.example.subscribble.database.UsageDao
import com.example.subscribble.database.UsageList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(
    private val cardDao: CardDao,
    private val subDao: SubDao,
    private val testDao: UsageDao
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
            cardDao.deleteCards(cardList)
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

    fun getSubCardByName(name: String): SubsList?{
        return subDao.getSubCardByName(name)
    }

    fun getSubscriptionByCategory(category: String): SubsList?{
        return subDao.getSubscriptionByCategory(category)
    }

    fun getAllTests(): List<UsageList>{
        return testDao.getAllTests()
    }

    suspend fun insertTest(usageList: UsageList){
        withContext(Dispatchers.IO){
            testDao.insert(usageList)
        }
    }

    suspend fun updateUsage(usageList: UsageList){
        withContext(Dispatchers.IO){
            testDao.update(usageList)
        }
    }

    fun getPriceByMusic(name: String): Float {
        return subDao.getPriceByMusic(name)
    }
    fun getNameByName(name: String): String {
        return subDao.getNameByName(name)
    }
}
