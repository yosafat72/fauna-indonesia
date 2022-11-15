package lab.yohesu.faunaindonesia.database

import lab.yohesu.faunaindonesia.model.LeaderboardDataModel

class DatabaseHelperImp(private val appDatabase: AppDatabase) : DatabaseHelper {

    override suspend fun getAllLeaderboards() = appDatabase.dao().getAllLeaderboards()
    override suspend fun insertLeaderboard(data: LeaderboardDataModel) = appDatabase.dao().insertLeaderboard(data)
    override suspend fun deleteLeaderboard(data: LeaderboardDataModel) = appDatabase.dao().deleteLeaderboard(data)
    override suspend fun deleteById(id: Int) = appDatabase.dao().deleteById(id)

}