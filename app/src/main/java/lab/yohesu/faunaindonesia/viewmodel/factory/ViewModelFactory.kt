package lab.yohesu.faunaindonesia.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import lab.yohesu.faunaindonesia.database.DatabaseHelper
import lab.yohesu.faunaindonesia.viewmodel.LeaderboardViewModel

class ViewModelFactory(private val dbHelper: DatabaseHelper) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LeaderboardViewModel::class.java)) {
            return LeaderboardViewModel(dbHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}