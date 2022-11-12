package lab.yohesu.faunaindonesia.model

data class LearningModel(
    val learningData: List<LearningDataModel>
)

data class LearningDataModel(
    val animalName: Int = 0,
    val animalImage: Int? = 0,
    val animalSound: String? = null,
    val animalSite: String? = null
)