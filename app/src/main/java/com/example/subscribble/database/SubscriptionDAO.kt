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

    @Query("SELECT * FROM card_list WHERE card_id =:cardId")
    fun getCard(cardId:Int):List<CardList>

}

@Dao
interface SubDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sub: SubsList)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(sub: SubsList)

    @Query("DELETE FROM subscription WHERE sub_id = :id")
    fun delete(id:Int)

    @Query("SELECT * FROM subscription")
    fun getAllSubs():List<SubsList>

    @Query("SELECT * FROM subscription WHERE sub_id =:subId")
    fun getSub(subId:Int):List<SubsList>

    @Query("SELECT SUM(sub_price) FROM subscription WHERE sub_category = :category")
    fun sumPriceByCategory(category: String): Int


}