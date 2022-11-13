package lab.yohesu.faunaindonesia.model

import com.google.gson.annotations.SerializedName

data class PlayingModel(
    @field:SerializedName("success")
    val success: Boolean? = false,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: List<PlayingDataModel?>? = null
)

data class PlayingDataModel(
    @field:SerializedName("imageQuestion")
    val imageQuestion: String? = null,

    @field:SerializedName("soundQuestion")
    val soundQuestion: String? = null,

    @field:SerializedName("question")
    val question: String? = null,

    @field:SerializedName("optionA")
    val optionA: String? = null,

    @field:SerializedName("optionB")
    val optionB: String? = null,

    @field:SerializedName("optionC")
    val optionC: String? = null,

    @field:SerializedName("optionD")
    val optionD: String? = null,

    @field:SerializedName("correctAnswer")
    val correctAnswer: String? = null,

    @field:SerializedName("correctScore")
    val correctScore: Int? = null,

    @field:SerializedName("wrongScore")
    val wrongScore: Int? = null,
)