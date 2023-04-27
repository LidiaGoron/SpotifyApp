package com.plcoding.spotifycloneyt.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.plcoding.spotifycloneyt.data.entities.Song
import kotlinx.coroutines.tasks.await

class MusicDataBase {

    private val firestore = FirebaseFirestore.getInstance()
    private val songCollection = firestore.collection("songs")


    suspend fun getAllSongs() : List<Song> {
        return try{
            songCollection.get().await().toObjects(Song::class.java)
        } catch (e: Exception)
        {
            emptyList()
        }

    }
}