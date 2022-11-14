package lab.yohesu.faunaindonesia.database

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    private var instance: AppDatabase? = null

    fun getInstance(ctx: Context): AppDatabase{
        if(instance == null){
            synchronized(AppDatabase::class){
                if (instance == null){
                    instance = buildRoomDB(context = ctx)
                }
            }
        }
        return instance!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "db_fauna_indonesia"
        ).allowMainThreadQueries().build()

}