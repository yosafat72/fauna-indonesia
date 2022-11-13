package lab.yohesu.faunaindonesia.utils

import android.app.Activity
import android.media.MediaPlayer
import android.net.Uri
import lab.yohesu.faunaindonesia.R

class MusicHelper {

    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private var musicLength: Int = 0

    fun playMusic(activity: Activity){
        mediaPlayer.setDataSource(activity, Uri.parse("android.resource://"+activity.packageName+"/"+ R.raw.main_song))
        mediaPlayer.prepare()
        mediaPlayer.start()
        mediaPlayer.isLooping = true
    }

    fun pauseMusic(){
        mediaPlayer.pause()
        musicLength = mediaPlayer.currentPosition
    }

    fun resumeMusic(){
        mediaPlayer.seekTo(musicLength)
        mediaPlayer.start()
        mediaPlayer.isLooping = true
    }

    fun stopMusic(){
        mediaPlayer.stop()
        mediaPlayer.release()
        mediaPlayer = MediaPlayer()
    }

}