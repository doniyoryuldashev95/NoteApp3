package com.example.noteapp.firebase

data class Note(
    val should: Should? = null,
    val shouldNot: ShouldNot? = null,
    val toDo: ToDo? = null
)
