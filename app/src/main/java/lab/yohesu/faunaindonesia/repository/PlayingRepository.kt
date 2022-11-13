package lab.yohesu.faunaindonesia.repository

import android.content.Context
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import lab.yohesu.faunaindonesia.model.PlayingModel
import lab.yohesu.faunaindonesia.service.JsonReader
import lab.yohesu.faunaindonesia.service.State
import lab.yohesu.faunaindonesia.utils.JsonConstanta

class PlayingRepository {

    suspend fun getQuestionLevelOne(ctx: Context) : Flow<State<PlayingModel>> {
        return flow {
            val result = JsonReader().getJsonFile(ctx = ctx, fileName = JsonConstanta.PLAYING_LEVEL_ONE)
            emit(State.success(Gson().fromJson(result, PlayingModel::class.java)))
        }.flowOn(Dispatchers.IO)
    }

}