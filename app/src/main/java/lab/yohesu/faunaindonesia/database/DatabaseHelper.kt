package lab.yohesu.faunaindonesia.database

import lab.yohesu.faunaindonesia.model.LeaderboardDataModel

interface DatabaseHelper {

    suspend fun getAllLeaderboards() : List<LeaderboardDataModel>
    suspend fun insertLeaderboard(data: LeaderboardDataModel)
    suspend fun deleteLeaderboard(data: LeaderboardDataModel)

    suspend fun deleteById(id: Int)

}