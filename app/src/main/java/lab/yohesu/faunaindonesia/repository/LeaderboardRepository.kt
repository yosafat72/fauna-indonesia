package lab.yohesu.faunaindonesia.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import lab.yohesu.faunaindonesia.database.DatabaseHelper
import lab.yohesu.faunaindonesia.database.LeaderboardDao
import lab.yohesu.faunaindonesia.model.LeaderboardDataModel
import lab.yohesu.faunaindonesia.model.LeaderboardModel
import lab.yohesu.faunaindonesia.service.State

class LeaderboardRepository(private val helper: DatabaseHelper) {

    suspend fun getAllLeaderboards() : Flow<State<LeaderboardModel>>{
        return flow {
            val result = helper.getAllLeaderboards()
            val converters = LeaderboardModel(success = true, message = "Success", data = result)
            emit(State.success(converters))
        }
    }

    suspend fun deleteLeaderboard(model: LeaderboardDataModel) : Flow<State<LeaderboardModel>>{
        return flow {
            helper.deleteLeaderboard(model)
            val converters = LeaderboardModel(success = true, message = "Success Deleted", data = null)
            emit(State.success(converters))
        }
    }

}