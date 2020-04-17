package com.kz25.dotify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.kz25.dotify.MainActivity.Companion.SONG_KEY
import kotlinx.android.synthetic.main.activity_song_list.*
import java.util.Collections.shuffle

class SongListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)
        title = "All Songs"

        var listOfSong: List<Song> = SongDataProvider.getAllSongs()

        val songAdapter = SongListAdapter(listOfSong)

        songAdapter.onSongClickListener = { song: Song ->
            tvSongPlaying.text = "${song.title} - ${song.artist}"
            tvSongPlaying.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)

                intent.putExtra(SONG_KEY, song)
                startActivity(intent)
            }
        }

        songAdapter.onSongLongClickListner = {song:Song ->
            val index: Int = listOfSong.indexOf(song)
            val newSong = listOfSong.toMutableList().apply {
                removeAt(index)
            }
            listOfSong = newSong
            songAdapter.change(newSong)
            Toast.makeText(this, "You have deleted ${song.title}", Toast.LENGTH_SHORT).show()
        }

        rvSongs.adapter = songAdapter

        btnShuffle.setOnClickListener{
            val newSong = listOfSong.toList()
            shuffle(newSong)
            songAdapter.change(newSong)
            listOfSong = newSong
        }
    }
}
