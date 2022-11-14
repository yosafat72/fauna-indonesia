package lab.yohesu.faunaindonesia.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import lab.yohesu.faunaindonesia.database.DatabaseHelper
import lab.yohesu.faunaindonesia.viewmodel.LeaderboardViewModel
import lab.yohesu.faunaindonesia.viewmodel.PlayingViewModel

class ViewModelFactory(private val dbHelper: DatabaseHelper) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LeaderboardViewModel::class.java)) {
            return LeaderboardViewModel(dbHelper) as T
        }
        if(modelClass.isAssignableFrom(PlayingViewModel::class.java)){
            return PlayingViewModel(dbHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}