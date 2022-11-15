package lab.yohesu.faunaindonesia.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import lab.yohesu.faunaindonesia.model.UIModel
import lab.yohesu.faunaindonesia.repository.LearningRepository
import lab.yohesu.faunaindonesia.service.State
import lab.yohesu.faunaindonesia.service.Status

class LearningViewModel : ViewModel() {

    private val repository = LearningRepository()

    val state = MutableStateFlow(
        State(
            Status.IDLE,
            UIModel<Any>(),
            ""
        )
    )

    fun fetchLearning(ctx: Context){
        state.value = State.loading()
        viewModelScope.launch {
            repository.getlearning(ctx = ctx)
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