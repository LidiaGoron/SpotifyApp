package com.plcoding.spotifycloneyt.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.spotifycloneyt.exoplayer.MusicService
import com.plcoding.spotifycloneyt.exoplayer.MusicServiceConnection
import com.plcoding.spotifycloneyt.exoplayer.currentPlaybackPosition
import com.plcoding.spotifycloneyt.other.Constants.UPDATE_PLAYER_POSITION_INTERVAL
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SongViewModel @ViewModelInject constructor(
    musicServiceConnection: MusicServiceConnection

) : ViewModel(){

    private val playbackState = musicServiceConnection.playbackState

    private val _curSongDuration = MutableLiveData<Long>()
    val curSongDuration : LiveData<Long> = _curSongDuration

    private val _curPlayerPosition = MutableLiveData<Long>()
    val curPlayerPosition : LiveData<Long> = _curPlayerPosition

    init{
        updateCurrentPlayerPosition()
    }


    private fun updateCurrentPlayerPosition()
    {
        viewModelScope.launch {
            while (true)
            {
                val position = playbackState.value?.currentPlaybackPosition
                if(curPlayerPosition.value !=position){
                    _curPlayerPosition.postValue(position)
                    _curSongDuration.postValue(MusicService.currentSongDuration)
                }

                delay(UPDATE_PLAYER_POSITION_INTERVAL)

            }
        }
    }
}