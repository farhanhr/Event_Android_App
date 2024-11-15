package com.example.dicodingeventsubs.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.dicodingeventsubs.data.database.FavoriteEvent
import com.example.dicodingeventsubs.data.database.FavoriteEventDao
import com.example.dicodingeventsubs.data.database.FavoriteEventRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteEventRepository(application: Application) {
    private val mFavoriteEventDao: FavoriteEventDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavoriteEventRoomDatabase.getDatabase(application)
        mFavoriteEventDao = db.favoriteEventDao()
    }

    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvent>> = mFavoriteEventDao.getAllFavoriteEvents()

    fun insert(favoriteEvent: FavoriteEvent) {
        executorService.execute { mFavoriteEventDao.insert(favoriteEvent) }
    }

    fun delete(favoriteEvent: FavoriteEvent) {
        executorService.execute { mFavoriteEventDao.delete(favoriteEvent) }
    }

    fun isFavorite(id: Int) = mFavoriteEventDao.isFavorite(id)



}