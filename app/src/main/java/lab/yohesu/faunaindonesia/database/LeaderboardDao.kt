package lab.yohesu.faunaindonesia.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import lab.yohesu.faunaindonesia.model.LeaderboardDataModel

@Dao
interface LeaderboardDao {

    @Insert
    fun insertLeaderboard(data: LeaderboardDataModel)

    @Update
    fun updateLeaderboard(data: LeaderboardDataModel)

    @Delete
    fun deleteLeaderboard(data: LeaderboardDataModel)

    @Query("Select * from tbl_leaderboard order by score desc")
    fun getAllLeaderboards(): List<LeaderboardDataModel>

    @Query("Delete from tbl_leaderboard where id = :id")
    fun deleteById(id: Int)

}