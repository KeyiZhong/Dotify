package com.kz25.dotify.fragment

import androidx.recyclerview.widget.DiffUtil
import com.kz25.dotify.manager.model.Song


class SongDiffCallback (
    private val oldSongs: List<Song> ,
            private val newSongs: List<Song>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldSongs.size

    override fun getNewListSize(): Int = newSongs.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldSong = oldSongs[oldItemPosition]
        val newSong = newSongs[newItemPosition]

        return oldSong.id == newSong.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldSong = oldSongs[oldItemPosition]
        val newSong = newSongs[newItemPosition]
        return oldSong.artist == newSong.artist && oldSong.title == newSong.title
    }


}