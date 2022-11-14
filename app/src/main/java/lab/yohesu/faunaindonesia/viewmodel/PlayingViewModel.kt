package lab.yohesu.faunaindonesia.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import lab.yohesu.faunaindonesia.database.DatabaseHelper
import lab.yohesu.faunaindonesia.model.LeaderboardDataModel
import lab.yohesu.faunaindonesia.model.PlayingModel
import lab.yohesu.faunaindonesia.repository.PlayingRepository
import lab.yohesu.faunaindonesia.service.State
import lab.yohesu.faunaindonesia.service.Status

class PlayingViewModel(dbHelper: DatabaseHelper) : ViewModel() {

    private val repository = PlayingRepository(dbHelper)

    val state = MutableStateFlow(
        State(
            Status.IDLE,
            PlayingModel(),
            ""
        )
    )

    fun fetchQuestionLevelOne(ctx: Context){
        state.value = State.loading()
        viewModelScope.launch {
            repository.getQuestionLevelOne(ctx = ctx)
                .catch {
                    state.value = it.localizedMessage?.let { it1 -> State.error(it1) }!!
                }
                .collectLatest {
                    state.value = State.success(it.data)
                }
        }
    }

    fun insertIntoRoom(data: LeaderboardDataModel){
        state.value = State.loading()
        viewModelScope.launch {
            repository.insertLeaderboard(data = data)
                .catch {
                    state.value = it.localizedMessage?.let { it1 -> State.error(it1) }!!
                }
                .collectLatest {
                    val model = PlayingModel(success = it.data?.success, message = it.data?.message)
                    state.value = State.successInsertToRoom(model)
                }
        }
    }

}