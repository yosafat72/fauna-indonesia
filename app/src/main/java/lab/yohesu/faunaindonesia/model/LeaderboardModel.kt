package lab.yohesu.faunaindonesia.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class LeaderboardModel(
    val success: Boolean? = false,
    val message: String? = null,
    val data: List<LeaderboardDataModel?>? = null
)

@Entity(tableName = "tbl_leaderboard")
data class LeaderboardDataModel(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = 0,

    @ColumnInfo(name = "name")
    val name: String? = "",

    @ColumnInfo(name = "score")
    val score: Int? = 0,

    @ColumnInfo(name = "level")
    val level: Int? = 0

)
