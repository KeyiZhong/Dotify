package com.kz25.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun start(view: View) {
        plays++
        tvPlays.text = "${plays.toString()} ${tvPlays.text.split(" ")[1]}"
    }

    fun next(view: View) {
        makeToast("Skipping to next track")
    }

    fun previous(view: View) {
        makeToast("Skipping to previous track")
    }

    fun makeToast(text:String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val SONG_KEY = "SONG_KEY"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
