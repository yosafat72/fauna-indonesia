package lab.yohesu.faunaindonesia.database

import lab.yohesu.faunaindonesia.model.LeaderboardDataModel

class DatabaseHelperImp(private val appDatabase: AppDatabase) : DatabaseHelper {

    override suspend fun getAllLeaderboards() = appDatabase.dao().getAllLeaderboards()

}