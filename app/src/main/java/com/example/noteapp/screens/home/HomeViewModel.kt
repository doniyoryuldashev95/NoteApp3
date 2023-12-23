package com.example.noteapp.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.noteapp.room.WordEntity

class HomeViewModel : ViewModel() {


private val mutableLiveData = MutableLiveData<ArrayList<WordEntity>>()

    fun setValue(data:ArrayList<WordEntity>){
        mutableLiveData.value = data
    }

    fun getValue():LiveData<ArrayList<WordEntity>> {
        return mutableLiveData
    }
}