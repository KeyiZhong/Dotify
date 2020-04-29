package com.kz25.dotify

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.fragment_song_list.*
import java.util.*
import java.util.Collections.shuffle
import kotlin.collections.ArrayList

class SongListFragment: Fragment() {
    private lateinit var songListAdapter: SongListAdapter

    private var onSongClickListener: OnSongClickListener? = null

    private lateinit var songList:List<Song>

    companion object {
        val TAG: String = SongListFragment::class.java.simpleName

        const val ARG_SONG_LIST = "arg_song_list"
        const val SONG_LIST = "song_list"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSongClickListener) {
            onSongClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedInstanceState != null) {
            val songList = savedInstanceState.getParcelableArrayList<Song>(SONG_LIST)
            if (songList != null) {
                this.songList = songList
            }
        }else {
            arguments?.let{args ->
                val songList = args.getParcelableArrayList<Song>(ARG_SONG_LIST)
                if (songList != null) {
                    this.songList = songList
                }
            }
        }
        return layoutInflater.inflate(R.layout.fragment_song_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putParcelableArrayList(SONG_LIST, ArrayList(songList))
        }
        super.onSaveInstanceState(outState)
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