package com.example.subscribble.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_list")
data class CardList(
    @ColumnInfo(name = "card_id")
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,

    @ColumnInfo(name = "card_name")
    val name:String,

    @ColumnInfo(name = "card_detail")
    val detail:String
)

@Entity(tableName = "subscription")
data class SubsList(
    @ColumnInfo(name = "sub_id")
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,

    @ColumnInfo(name = "sub_name")
    val name:String,

    @ColumnInfo(name = "sub_plan")
    val planName:String,

    @ColumnInfo(name = "sub_price")
    val price:Float,

    @ColumnInfo(name = "sub_date")
    val date:String,

    @ColumnInfo(name = "sub_note")
    val note:String,

    @ColumnInfo(name = "sub_category")
    val type:String
)



