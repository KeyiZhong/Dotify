package com.kz25.dotify.activity

import com.kz25.dotify.DotifyApp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kz25.dotify.R
import com.kz25.dotify.fragment.NowPlayingFragment
import com.kz25.dotify.fragment.OnSongClickListener
import com.kz25.dotify.fragment.SongListFragment
import com.kz25.dotify.manager.ApiManager
import com.kz25.dotify.manager.MusicManager
import com.kz25.dotify.manager.model.Song
import com.kz25.dotify.manager.model.SongList
import kotlinx.android.synthetic.main.activity_ultimate_main.*

class UltimateMainActivity : AppCompatActivity(),
    OnSongClickListener {

    lateinit var apiManager: ApiManager

    lateinit var musicManager: MusicManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ultimate_main)

        title = "All Songs"

        apiManager = (application as DotifyApp).apiManager
        musicManager = (application as DotifyApp).musicManager

        apiManager.fetchSongs({songList: SongList ->
            musicManager.setSongList(songList)
            val listFragment = SongListFragment.getInstance()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragContainer, listFragment, SongListFragment.TAG)
                .addToBackStack(SongListFragment.TAG)
                .commit()
        })



        btnShuffle.setOnClickListener {
            getSongListFragment()?.shuffleList()
        }



        supportFragmentManager.addOnBackStackChangedListener {
            supportActionBar?.setDisplayHomeAsUpEnabled(supportFragmentManager.backStackEntryCount > 0)
        }
        if(supportFragmentManager.backStackEntryCount > 0) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(
        NowPlayingFragment.TAG
    ) as? NowPlayingFragment

    private fun getSongListFragment() = supportFragmentManager.findFragmentById(R.id.fragContainer) as? SongListFragment

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        tvSongPlaying.visibility = View.VISIBLE
        btnShuffle.visibility = View.VISIBLE
        return super.onNavigateUp()
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

    private fun onMiniClicked(song: Song) {
        tvSongPlaying.setOnClickListener {
            var nowPlayingFragment = getNowPlayingFragment()
            if (nowPlayingFragment == null) {
                nowPlayingFragment =
                    NowPlayingFragment()
                val argumentBundle = Bundle().apply {
                    putParcelable(NowPlayingFragment.ARG_SONG, song)
                }
                nowPlayingFragment.arguments = argumentBundle

                supportFragmentManager
                    .beginTransaction()
                    .add(
                        R.id.fragContainer, nowPlayingFragment,
                        NowPlayingFragment.TAG
                    )
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
        this.musicManager.current = song
        tvSongPlaying.text = "${song.title} - ${song.artist}"
    }
}
