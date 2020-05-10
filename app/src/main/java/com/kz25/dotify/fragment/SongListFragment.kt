package com.kz25.dotify.fragment

import com.kz25.dotify.DotifyApp
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kz25.dotify.R
import com.kz25.dotify.SongListAdapter
import com.kz25.dotify.manager.MusicManager
import com.kz25.dotify.manager.model.Song
import kotlinx.android.synthetic.main.fragment_song_list.*

class SongListFragment: Fragment() {
    private lateinit var songListAdapter: SongListAdapter

    private var onSongClickListener: OnSongClickListener? = null

    private lateinit var songList:List<Song>

    private lateinit var musicManager: MusicManager

    companion object {
        val TAG: String = SongListFragment::class.java.simpleName

        const val ARG_SONG_LIST = "arg_song_list"
        const val SONG_LIST = "song_list"

        fun getInstance(): SongListFragment {
            return SongListFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        musicManager = (context.applicationContext as DotifyApp).musicManager
        if (context is OnSongClickListener) {
            onSongClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_song_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.songList = this.musicManager.songList
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        songListAdapter = SongListAdapter(songList)

        rvSongs.adapter = songListAdapter

        songListAdapter.onSongClickListener = {song ->
            onSongClickListener?.onSongClicked(song)
        }
    }

    fun shuffleList() {
        val newSong = songList.shuffled()
        songListAdapter.change(newSong)
        songList = newSong
    }
}

interface OnSongClickListener {
    fun onSongClicked(song: Song)
}