package com.example.noteapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WordDAO {
    @Query("SELECT * FROM word_entity")
    fun getAll(): List<WordEntity>

    @Query("SELECT * FROM word_entity WHERE uid IN (:wordIds)")
    fun loadAllByIds(wordIds: IntArray): List<WordEntity>

    @Query("SELECT * FROM word_entity WHERE title LIKE :first AND " +
            "description LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): WordEntity

    @Insert
    fun insertUser(words: WordEntity)

    @Delete
    fun delete(words: WordEntity)
}