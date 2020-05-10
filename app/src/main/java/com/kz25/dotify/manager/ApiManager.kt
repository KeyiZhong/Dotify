package com.kz25.dotify.manager

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.kz25.dotify.manager.model.ArtistsList
import com.kz25.dotify.manager.model.SongList
import com.kz25.dotify.manager.model.UserInfo

class ApiManager(context: Context) {

    private val queue: RequestQueue = Volley.newRequestQueue(context)

    fun fetchSongs(onSongReady: (SongList) -> Unit, onError: (() -> Unit)? = null) {
        val url = "https://raw.githubusercontent.com/echeeUW/codesnippets/master/musiclibrary.json"
        val request = StringRequest(
            Request.Method.GET, url,
            { response ->
                // Success
                val gson = Gson()
                val songList = gson.fromJson(response, SongList::class.java)
                onSongReady(songList)

            },
            {
                onError?.invoke()
            }
        )

        queue.add(request)
    }

    fun fetchArtists(onArtistReady: (ArtistsList) -> Unit, onError: (() -> Unit)? = null) {
        val url = "https://raw.githubusercontent.com/echeeUW/codesnippets/master/allartists.json"
        val request = StringRequest(
            Request.Method.GET, url,
            { response ->
                // Success
                val gson = Gson()
                val artistsList = gson.fromJson(response, ArtistsList::class.java )

                onArtistReady(artistsList)

            },
            {
                onError?.invoke()
            }
        )

        queue.add(request)
    }

    fun fetchUserInfo(onInfoReady: (UserInfo) -> Unit, onError: (() -> Unit)? = null) {
        val url = "https://raw.githubusercontent.com/echeeUW/codesnippets/master/user_info.json"
        val request = StringRequest(
            Request.Method.GET, url,
            { response ->
                // Success
                val gson = Gson()
                val userInfo = gson.fromJson(response, UserInfo::class.java )

                onInfoReady(userInfo)

            },
            {
                onError?.invoke()
            }
        )

        queue.add(request)
    }
}