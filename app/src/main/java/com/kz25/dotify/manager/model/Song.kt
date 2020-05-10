package com.kz25.dotify.manager.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Song (
    val id:String,
    val title:String,
    val artist:String,
    val durationMillis:Int,
    val smallImageURL:String,
    val largeImageURL:String
):Parcelable