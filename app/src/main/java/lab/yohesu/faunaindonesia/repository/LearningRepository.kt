package lab.yohesu.faunaindonesia.repository

import android.content.Context
import com.google.gson.Gson
import lab.yohesu.faunaindonesia.model.LearningModel
import lab.yohesu.faunaindonesia.service.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import lab.yohesu.faunaindonesia.service.JsonReader
import lab.yohesu.faunaindonesia.utils.JsonConstanta

class LearningRepository {

    suspend fun getlearning(ctx: Context) : Flow<State<LearningModel>>{
        return flow {
            val result = JsonReader().getJsonFile(ctx = ctx, fileName = JsonConstanta.LEARNING)
            emit(State.success(Gson().fromJson(result, LearningModel::class.java)))
        }.flowOn(Dispatchers.IO)
    }

}