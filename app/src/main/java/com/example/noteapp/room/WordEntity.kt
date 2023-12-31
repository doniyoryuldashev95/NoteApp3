package com.example.noteapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "word_entity")
data class WordEntity(

    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0,

    var title: String = "",
    var description: String? = "",
    @ColumnInfo("color", defaultValue = "")
    var color:String? = ""
) : Serializable