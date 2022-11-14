package lab.yohesu.faunaindonesia.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import lab.yohesu.faunaindonesia.model.LeaderboardDataModel
import lab.yohesu.faunaindonesia.model.LeaderboardModel

@Database(entities = [LeaderboardDataModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dao(): LeaderboardDao

}