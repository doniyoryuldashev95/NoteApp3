package com.example.noteapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "word_entity")
data class WordEntity(

    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,

    val title: String = "",
    val description: String? = "",
    @ColumnInfo("color", defaultValue = "")
    val color:String? = ""
) : Serializable