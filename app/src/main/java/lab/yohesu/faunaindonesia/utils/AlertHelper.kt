package lab.yohesu.faunaindonesia.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import lab.yohesu.faunaindonesia.databinding.DialogExitBinding
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

}
