package lab.yohesu.faunaindonesia.repository

import android.content.Context
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import lab.yohesu.faunaindonesia.database.DatabaseHelper
import lab.yohesu.faunaindonesia.model.LeaderboardDataModel
import lab.yohesu.faunaindonesia.model.LeaderboardModel
import lab.yohesu.faunaindonesia.model.PlayingModel
import lab.yohesu.faunaindonesia.service.JsonReader
import lab.yohesu.faunaindonesia.service.State
import lab.yohesu.faunaindonesia.utils.JsonConstanta

class PlayingRepository(private val helper: DatabaseHelper) {

    suspend fun getQuestionLevelOne(ctx: Context) : Flow<State<PlayingModel>> {
        return flow {
            val result = JsonReader().getJsonFile(ctx = ctx, fileName = JsonConstanta.PLAYING_LEVEL_ONE)
            emit(State.success(Gson().fromJson(result, PlayingModel::class.java)))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun insertLeaderboard(data: LeaderboardDataModel) : Flow<State<LeaderboardModel>>{
        return flow {
            helper.insertLeaderboard(data = data)
            val converters = LeaderboardModel(success = true, message = "Success Insert Data", data = null)
            emit(State.success(converters))
        }.flowOn(Dispatchers.IO)
    }

}