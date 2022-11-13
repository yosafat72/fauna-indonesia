package lab.yohesu.faunaindonesia.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import lab.yohesu.faunaindonesia.databinding.ActivityMenuBinding
import lab.yohesu.faunaindonesia.utils.AlertHelper
import lab.yohesu.faunaindonesia.utils.MusicHelper

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private val musicHelper: MusicHelper by lazy { MusicHelper() }
    private val alertHelper: AlertHelper by lazy { AlertHelper() }

    //timer
    var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        musicHelper.playMusic(this)

        binding.btnExit.setOnClickListener {
            alertHelper.AlertClose(this)
        }

        binding.btnStudy.setOnClickListener {
            startActivity(Intent(this, LearningActivity::class.java))
        }

        binding.btnPlay.setOnClickListener {
            alertHelper.AlertPlayingLevel(this)
        }

        binding.btnLeaderboard.setOnClickListener {
            alertHelper.AlertLeaderBoardLevel(this)
        }

    }

    override fun onBackPressed() {
        if (backPressedTime + 3000 > System.currentTimeMillis()){
            super.onBackPressed()
            finish()
        }else{
            Toast.makeText(this, "Press back again to leave the app.", Toast.LENGTH_LONG).show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    override fun onResume() {
        super.onResume()
        musicHelper.resumeMusic()
    }

    override fun onPause() {
        musicHelper.pauseMusic()
        super.onPause()
    }

    override fun onDestroy() {
        musicHelper.stopMusic()
        super.onDestroy()
    }
}