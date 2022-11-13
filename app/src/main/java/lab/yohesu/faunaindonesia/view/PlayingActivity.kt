package lab.yohesu.faunaindonesia.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import lab.yohesu.faunaindonesia.databinding.ActivityPlayingBinding
import lab.yohesu.faunaindonesia.model.PlayingModel
import lab.yohesu.faunaindonesia.service.Status
import lab.yohesu.faunaindonesia.viewmodel.PlayingViewModel

class PlayingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayingBinding
    private val viewModel : PlayingViewModel by lazy {
        ViewModelProvider(this)[PlayingViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()

        viewModel.fetchQuestionLevelOne(this)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(lifecycle).collectLatest {
                when(it.status){
                    Status.LOADING -> onLoading()
                    Status.SUCCESS -> onSuccess(it.data)
                    Status.ERROR -> onError(it.message)
                    else -> {}
                }
            }
        }
    }

    private fun onLoading() {
    }

    private fun onSuccess(model: PlayingModel?) {
        Log.d("RESULT", model.toString())
    }

    private fun onError(message: String?) {
        Log.d("Running-Error", "$message")
        finish()
    }

}

