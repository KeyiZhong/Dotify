package com.kz25.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var plays: Int = Random.nextInt(0,1000)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvPlays.text = "${plays.toString()} ${tvPlays.text.split(" ")[1]}"
        val song = intent.getParcelableExtra<Song>(SONG_KEY)
        ivAlbumCover.setImageResource(song.largeImageID)
        tvArtists.text = song.artist
        tvSongTitle.text = song.title
    }

    fun start(view: View) {
        plays++
        tvPlays.text = "${plays.toString()} ${tvPlays.text.split(" ")[1]}"
    }

    fun next(view: View) {
        toastText("Skipping to next track")
    }

    fun previous(view: View) {
        toastText("Skipping to previous track")
    }

    fun toastText(text:String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val SONG_KEY = "SONG_KEY"
    }
}
