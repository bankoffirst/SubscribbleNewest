package com.example.subscribble.database
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CardDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(card: CardList)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(card: CardList)

    @Delete
    suspend fun delete(card: CardList)

    @Query("SELECT * FROM card_list")
    fun getAllCards():List<CardList>

    @Query("SELECT * FROM card_list WHERE card_id =:id")
    fun getCardById(id: Int): CardList?

}

@Dao
interface SubDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sub: SubsList)

    @Update
    suspend fun updateSubscription(sub: SubsList)

    @Delete
    suspend fun deleteSubscription(sub: SubsList)

    @Query("SELECT * FROM subscription")
    fun getAllSubs():List<SubsList>

    @Query("SELECT * FROM subscription WHERE sub_id =:id")
    fun getSubscriptionById(id: Int): SubsList?

    @Query("SELECT SUM(sub_price) FROM subscription WHERE sub_category = :category")
    fun sumPriceByCategory(category: String): Float

    @Query("SELECT * FROM subscription WHERE card_name =:name")
    fun getSubCardByName(name: String): SubsList?

    @Query("SELECT * FROM subscription WHERE sub_category =:category")
    fun getSubscriptionByCategory(category: String): SubsList?

}

@Dao
interface TestDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(test: TestsList)

    @Query("SELECT * FROM test")
    fun getAllTests(): List<TestsList>

}