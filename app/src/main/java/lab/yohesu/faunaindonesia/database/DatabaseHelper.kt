package lab.yohesu.faunaindonesia.database

import lab.yohesu.faunaindonesia.model.LeaderboardDataModel

interface DatabaseHelper {

    suspend fun getAllLeaderboards() : List<LeaderboardDataModel>

}