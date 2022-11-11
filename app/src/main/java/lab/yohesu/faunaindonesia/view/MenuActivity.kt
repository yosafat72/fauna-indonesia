package lab.yohesu.faunaindonesia.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import lab.yohesu.faunaindonesia.databinding.ActivityMenuBinding
import lab.yohesu.faunaindonesia.utils.AlertHelper

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    //timer
    var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnExit.setOnClickListener {
            AlertHelper().AlertClose(this)
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
}