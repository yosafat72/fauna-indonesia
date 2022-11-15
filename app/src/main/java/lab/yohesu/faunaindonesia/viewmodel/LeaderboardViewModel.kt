package lab.yohesu.faunaindonesia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import lab.yohesu.faunaindonesia.database.DatabaseHelper
import lab.yohesu.faunaindonesia.model.UIModel
import lab.yohesu.faunaindonesia.repository.LeaderboardRepository
import lab.yohesu.faunaindonesia.service.State
import lab.yohesu.faunaindonesia.service.Status

class LeaderboardViewModel(dbHelper: DatabaseHelper) : ViewModel(){

    private var repository = LeaderboardRepository(dbHelper)

    val state = MutableStateFlow(
        State(
            Status.IDLE,
            UIModel<Any>(),
            ""
        )
    )

    fun fetchAllLeaderboards(){
        state.value = State.loading()
        viewModelScope.launch {
            repository.getAllLeaderboards()
                .catch {
                    state.value = it.localizedMessage?.let { it1 -> State.error(it1) }!!
                }
                .collectLatest {
                    val model = UIModel<Any>(dataModel = it.data)
                    state.value = State.success(model)
                }

        }
    }

}