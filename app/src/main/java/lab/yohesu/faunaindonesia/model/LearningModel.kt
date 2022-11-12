package lab.yohesu.faunaindonesia.model

import com.google.gson.annotations.SerializedName

data class LearningModel(
    @field:SerializedName("success")
    val success: Boolean? = false,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: List<LearningDataModel?>? = null
)

data class LearningDataModel(
    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("sound")
    val sound: String? = null,

    @field:SerializedName("site")
    val site: String? = null
)