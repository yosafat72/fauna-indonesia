package lab.yohesu.faunaindonesia.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import lab.yohesu.faunaindonesia.databinding.DialogExitBinding
import lab.yohesu.faunaindonesia.databinding.DialogGameOverBinding
import lab.yohesu.faunaindonesia.databinding.DialogLeaderboardLevelBinding
import lab.yohesu.faunaindonesia.databinding.DialogPlayingLevelBinding
import lab.yohesu.faunaindonesia.view.LeaderboardActivity
import lab.yohesu.faunaindonesia.view.MenuActivity
import lab.yohesu.faunaindonesia.view.PlayingActivity
import kotlin.system.exitProcess

class AlertHelper {

    fun AlertClose(ctx: Context){
        val dialog = Dialog(ctx)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)

        val dialogBinding = DialogExitBinding.inflate(LayoutInflater.from(ctx))

        dialog.setContentView(dialogBinding.root)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.btnExit.setOnClickListener {
            exitProcess(-1)
        }

        dialogBinding.btnDismiss.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun AlertPlayingLevel(activity: Activity){
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)

        val dialogBinding = DialogPlayingLevelBinding.inflate(LayoutInflater.from(activity))

        dialog.setContentView(dialogBinding.root)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes = layoutParams

        dialogBinding.btnLevel01.setOnClickListener {
            val intent = Intent(activity, PlayingActivity::class.java)
            activity.startActivity(intent)
        }

        dialogBinding.btnLevel02.setOnClickListener {
            val intent = Intent(activity, PlayingActivity::class.java)
            activity.startActivity(intent)
        }

        dialogBinding.btnLevel03.setOnClickListener {
            val intent = Intent(activity, PlayingActivity::class.java)
            activity.startActivity(intent)
        }

        dialogBinding.btnDismiss.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun AlertLeaderBoardLevel(activity: Activity){
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)

        val dialogBinding = DialogLeaderboardLevelBinding.inflate(LayoutInflater.from(activity))

        dialog.setContentView(dialogBinding.root)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes = layoutParams

        dialogBinding.btnLevel01.setOnClickListener {
            val intent = Intent(activity, LeaderboardActivity::class.java)
            activity.startActivity(intent)
        }

        dialogBinding.btnLevel02.setOnClickListener {
            val intent = Intent(activity, PlayingActivity::class.java)
            activity.startActivity(intent)
        }

        dialogBinding.btnLevel03.setOnClickListener {
            val intent = Intent(activity, PlayingActivity::class.java)
            activity.startActivity(intent)
        }

        dialogBinding.btnDismiss.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    @SuppressLint("SetTextI18n")
    fun AlertGameOver(activity: Activity, score: Int){
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)

        val dialogBinding = DialogGameOverBinding.inflate(LayoutInflater.from(activity))

        dialog.setContentView(dialogBinding.root)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes = layoutParams

        dialogBinding.txtScore.text = "Your score is $score"

        dialogBinding.btnBackToMenu.setOnClickListener {
            val intent = Intent(activity, MenuActivity::class.java)
            activity.startActivity(intent)
            activity.finishAffinity()
        }

        dialog.show()
    }

}
