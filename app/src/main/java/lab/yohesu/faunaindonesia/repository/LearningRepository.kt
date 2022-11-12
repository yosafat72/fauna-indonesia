package lab.yohesu.faunaindonesia.repository

import lab.yohesu.faunaindonesia.model.LearningModel
import lab.yohesu.faunaindonesia.service.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import lab.yohesu.faunaindonesia.R
import lab.yohesu.faunaindonesia.model.LearningDataModel
import lab.yohesu.faunaindonesia.utils.WebsiteConstanta

class LearningRepository {

    suspend fun getlearning() : Flow<State<LearningModel>>{
        return flow<State<LearningModel>> {
            val arrAnimal = listOf<LearningDataModel>(
                LearningDataModel(animalName = R.string.ceumpala_kuneng, animalImage = R.drawable.ceumpala_kuneng, animalSound = null, animalSite = WebsiteConstanta.CEUMPALA_KUNENG_WEB),
                LearningDataModel(animalName = R.string.beo_nias, animalImage = R.drawable.beo_nias, animalSound = null, animalSite = WebsiteConstanta.BEO_NIAS_WEB),
                LearningDataModel(animalName = R.string.kuau_raja, animalImage = R.drawable.kuau_raja, animalSound = null, animalSite = WebsiteConstanta.KUAU_RAJA_WEB),
                LearningDataModel(animalName = R.string.serindit, animalImage = R.drawable.serindit, animalSound = null, animalSite = WebsiteConstanta.SERINDIT_WEB),
                LearningDataModel(animalName = R.string.ikan_kakap, animalImage = R.drawable.ikan_kakap, animalSound = null, animalSite = WebsiteConstanta.IKAN_KAKAP_WEB),
            )
            val model = LearningModel(learningData = arrAnimal)
            emit(State.success(model))

        }.flowOn(Dispatchers.IO)
    }

}