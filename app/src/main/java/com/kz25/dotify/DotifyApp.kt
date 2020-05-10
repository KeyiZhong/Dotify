package com.kz25.dotify

import android.app.Application
import com.kz25.dotify.manager.ApiManager
import com.kz25.dotify.manager.MusicManager
import com.kz25.dotify.manager.model.SongList

class DotifyApp: Application() {
    lateinit var musicManager: MusicManager

    lateinit var apiManager: ApiManager

    override fun onCreate() {
        super.onCreate()

        apiManager = ApiManager(this)
        musicManager = MusicManager()

    }

}