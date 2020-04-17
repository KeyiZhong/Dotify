package com.kz25.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song

class SongListAdapter(initialSong:List<Song>):RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {

    private var listOfSongs: List<Song> = initialSong.toList()
    var onSongClickListener: ((song: Song) -> Unit)? = null
    var onSongLongClickListner: ((song: Song) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)

        return SongViewHolder(view)
    }

    override fun getItemCount() = listOfSongs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = listOfSongs[position]
        holder.bind(song)
    }

    fun change(newSong: List<Song>) {

        val callback = SongDiffCallback(listOfSongs, newSong)
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(this)

        listOfSongs = newSong

    }

    inner class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val ivSong = itemView.findViewById<ImageView>(R.id.ivSongImage)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvArtists = itemView.findViewById<TextView>(R.id.tvArtists)


        fun bind(song: Song) {
            ivSong.setImageResource(song.smallImageID)
            tvTitle.text = song.title
            tvArtists.text = song.artist

            itemView.setOnClickListener {
                onSongClickListener?.invoke(song)
            }

            itemView.setOnLongClickListener{
                onSongLongClickListner?.invoke(song)
                true
            }
        }

    }
}