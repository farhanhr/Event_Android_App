package com.example.dicodingeventsubs.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingeventsubs.data.database.FavoriteEvent
import com.example.dicodingeventsubs.data.repository.FavoriteEventRepository

class DetailViewModel(application: Application) : ViewModel() {
    private val mFavoriteEventRepository: FavoriteEventRepository = FavoriteEventRepository(application)
    fun insert(favoriteEvent: FavoriteEvent) {
        mFavoriteEventRepository.insert(favoriteEvent)
    }

    fun delete(favoriteEvent: FavoriteEvent) {
        mFavoriteEventRepository.delete(favoriteEvent)
    }

    fun isFavorite(id: Int): LiveData<FavoriteEvent> = mFavoriteEventRepository.isFavorite(id)

}