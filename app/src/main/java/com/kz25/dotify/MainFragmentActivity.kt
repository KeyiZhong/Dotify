package com.kz25.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_main_fragment.*
import kotlinx.android.synthetic.main.activity_song_list.*
import kotlinx.android.synthetic.main.activity_song_list.btnShuffle
import kotlinx.android.synthetic.main.activity_song_list.tvSongPlaying

class MainFragmentActivity : AppCompatActivity(), OnSongClickListener {

    private var current:Song? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_fragment)


        if (savedInstanceState != null) {
            with(savedInstanceState) {
                tvSongPlaying.text = getString(SONG_PLAYING)
                tvSongPlaying.visibility = getInt(INVISIBILITY)
                btnShuffle.visibility = getInt(INVISIBILITY)
                current = getParcelable(SONG)
            }
        } else {
            val songList = SongDataProvider.getAllSongs()
            val songListFragment = SongListFragment()
            val argumentBundleList = Bundle().apply {
                putParcelableArrayList(SongListFragment.ARG_SONG_LIST, ArrayList(songList))
            }
            songListFragment.arguments = argumentBundleList

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, songListFragment)
                .commit()
        }

        btnShuffle.setOnClickListener {
            getSongListFragment()?.shuffleList()
        }
        val immutableCurrent = this.current
        if (immutableCurrent != null) {
            val currentSong:Song = immutableCurrent
            onMiniClicked(currentSong)
        }

        supportFragmentManager.addOnBackStackChangedListener {
            supportActionBar?.setDisplayHomeAsUpEnabled(supportFragmentManager.backStackEntryCount > 0)
        }
        if(supportFragmentManager.backStackEntryCount > 0) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }



    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString(SONG_PLAYING, tvSongPlaying.text.toString())
            putInt(INVISIBILITY, tvSongPlaying.visibility)
            putParcelable(SONG, current)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        val nowPlayingFragment = getNowPlayingFragment()
        if (nowPlayingFragment != null) {
            supportFragmentManager
                .beginTransaction()
                .remove(nowPlayingFragment)
                .commit()
            tvSongPlaying.visibility = View.VISIBLE
            btnShuffle.visibility = View.VISIBLE
        }
        return super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        tvSongPlaying.visibility = View.VISIBLE
        btnShuffle.visibility = View.VISIBLE

        return super.onNavigateUp()
    }



    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment

    private fun getSongListFragment() = supportFragmentManager.findFragmentById(R.id.fragContainer) as? SongListFragment

    private fun onMiniClicked(song: Song) {
        tvSongPlaying.setOnClickListener {
            var nowPlayingFragment = getNowPlayingFragment()
            if (nowPlayingFragment == null) {
                nowPlayingFragment = NowPlayingFragment()
                val argumentBundle = Bundle().apply {
                    putParcelable(NowPlayingFragment.ARG_SONG, song)
                }
                nowPlayingFragment.arguments = argumentBundle

                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragContainer, nowPlayingFragment, NowPlayingFragment.TAG)
                    .addToBackStack(NowPlayingFragment.TAG)
                    .commit()
            } else {
                nowPlayingFragment.updateSong(song)
            }
            tvSongPlaying.visibility = View.GONE
            btnShuffle.visibility = View.GONE
        }
    }

    override fun onSongClicked(song: Song) {
        onMiniClicked(song)
        this.current = song
        tvSongPlaying.text = "${song.title} - ${song.artist}"
    }

    companion object {
        const val SONG_PLAYING = "songPlaying"
        const val INVISIBILITY = "invisibility"
        const val SONG = "song"
    }
}
