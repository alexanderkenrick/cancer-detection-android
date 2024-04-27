package com.dicoding.asclepius.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class History(
    @PrimaryKey(autoGenerate = false)
    var uuid: String,
    var imageUri: String? = null,
    var prediction: String? = null,
    var score: String? = null,
    var date: Long? = null
) : Parcelable