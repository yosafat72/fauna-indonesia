package lab.yohesu.faunaindonesia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import lab.yohesu.faunaindonesia.model.LearningModel
import lab.yohesu.faunaindonesia.repository.LearningRepository
import lab.yohesu.faunaindonesia.service.State
import lab.yohesu.faunaindonesia.service.Status

class LearningViewModel : ViewModel() {

    private val repository = LearningRepository()

    val state = MutableStateFlow(
        State(
            Status.IDLE,
            LearningModel(),
            ""
        )
    )

    fun fetchLearning(){
        state.value = State.loading()
        viewModelScope.launch {
            repository.getlearning()
                .catch {
                    state.value = it.localizedMessage?.let { it1 -> State.error(it1) }!!
                }
                .collectLatest {
                    state.value = State.success(it.data)
                }
        }
    }
}