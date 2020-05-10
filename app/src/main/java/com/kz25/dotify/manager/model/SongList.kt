package com.kz25.dotify.manager.model

data class SongList(
    val title: String,
    val numOfSongs: Int,
    val songs: List<Song>
)