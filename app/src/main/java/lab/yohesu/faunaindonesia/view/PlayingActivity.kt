package lab.yohesu.faunaindonesia.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import lab.yohesu.faunaindonesia.databinding.ActivityPlayingBinding

class PlayingActivity : AppCompatActivity() {

    lateinit var binding: ActivityPlayingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}