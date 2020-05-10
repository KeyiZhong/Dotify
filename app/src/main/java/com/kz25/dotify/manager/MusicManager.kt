package com.kz25.dotify.manager

import com.kz25.dotify.manager.model.Song
import com.kz25.dotify.manager.model.SongList

class MusicManager {

    lateinit var songList: List<Song>

    var current:Song? = null

    var playing:Boolean = false

    fun getCurrentSong():Song?{
        return current
    }

    fun nextSong() {

    }

    fun prevSong() {

    }

    fun play() {

    }

    fun pause() {

    }

    fun isPlaying():Boolean {
        return playing
    }

    fun setSongList(songList: SongList) {
        this.songList = songList.songs
    }
}