package lab.yohesu.faunaindonesia.utils

import android.app.Activity
import android.media.AudioAttributes
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

    fun playMusicQuestion(url: String){
        mediaPlayer.stop()
        mediaPlayer.release()
        mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )
        try {
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepareAsync()
            mediaPlayer.start()
        }catch (e: java.lang.Exception){
            e.printStackTrace()
        }
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