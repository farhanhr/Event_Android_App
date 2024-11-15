package com.example.dicodingeventsubs.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteEvent (
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var name: String = "",
    var mediaCover: String = ""
)