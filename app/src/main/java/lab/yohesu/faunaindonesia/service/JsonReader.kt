package lab.yohesu.faunaindonesia.service

import android.content.Context
import android.util.Log
import java.io.IOException

class JsonReader {
    fun getJsonFile(ctx: Context, fileName: String) : String{
        lateinit var jsonString: String
        try {
            jsonString = ctx.assets.open(fileName)
                .bufferedReader()
                .use { it.readText() }
        }catch (IoException: IOException){
            IoException.localizedMessage?.let { Log.d("Exception", it) }
        }
        return jsonString
    }
}