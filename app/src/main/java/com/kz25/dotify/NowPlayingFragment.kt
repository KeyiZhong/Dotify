package com.kz25.dotify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.fragment_now_playing.*
import kotlin.random.Random

class NowPlayingFragment:Fragment() {
    private lateinit var song: Song

    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName

        const val ARG_SONG = "arg_song"
        const val NUMBER_PLAY = "number_play"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let{args ->
            val song = args.getParcelable<Song>(ARG_SONG)
            if (song != null) {
                this.song = song
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putInt(NUMBER_PLAY, tvPlays.text.split(" ")[0].toInt())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_now_playing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            tvPlays.text = "${savedInstanceState.getInt(NUMBER_PLAY)} plays"
        }else {
            val plays: Int = Random.nextInt(0, 1000)
            tvPlays.text = "${plays} plays"
        }
        ivAlbumCover.setImageResource(song.largeImageID)
        tvArtists.text = song.artist
        tvSongTitle.text = song.title

        btnPlay.setOnClickListener {
            tvPlays.text = "${tvPlays.text.split(" ")[0].toInt()+1} plays"
        }

        btnNext.setOnClickListener {
            makeToast("Skipping to next track")
        }

        btnPrevious.setOnClickListener {
            makeToast("Skipping to previous track")
        }
    }

    private fun makeToast(text:String) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }

    fun updateSong(song:Song) {
        this.song = song
        ivAlbumCover.setImageResource(song.largeImageID)
        tvArtists.text = song.artist
        tvSongTitle.text = song.title
    }


}