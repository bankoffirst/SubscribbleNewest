package com.example.subscribble.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
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

@Entity(tableName = "subscription",
//    foreignKeys = [ForeignKey(
//        entity = CardList::class,
//        parentColumns = ["card_id"],
//        childColumns = ["cardId"],
//        onDelete = ForeignKey.CASCADE
//    )],
//    indices = [Index(value = ["cardId"])]
)
data class SubsList(
    @ColumnInfo(name = "sub_id")
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,

//    @ColumnInfo(name = "cardId")
//    val cardId:Int,

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
    val type:String,

    @ColumnInfo(name = "card_name")
    val cardName: String
)



