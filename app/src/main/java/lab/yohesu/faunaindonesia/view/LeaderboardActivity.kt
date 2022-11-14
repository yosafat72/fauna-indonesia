package lab.yohesu.faunaindonesia.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import lab.yohesu.faunaindonesia.database.DatabaseBuilder
import lab.yohesu.faunaindonesia.database.DatabaseHelper
import lab.yohesu.faunaindonesia.database.DatabaseHelperImp
import lab.yohesu.faunaindonesia.databinding.ActivityLeaderboardBinding
import lab.yohesu.faunaindonesia.model.LeaderboardModel
import lab.yohesu.faunaindonesia.service.Status
import lab.yohesu.faunaindonesia.viewmodel.LeaderboardViewModel
import lab.yohesu.faunaindonesia.viewmodel.factory.ViewModelFactory

class LeaderboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLeaderboardBinding

    private val viewModel: LeaderboardViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(DatabaseHelperImp(DatabaseBuilder.getInstance(applicationContext))))[LeaderboardViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()

        viewModel.fetchAllLeaderboards()

    }

    private fun observeViewModel(){
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

    private fun onError(message: String?) {
        Log.d("Running-Error", "$message")
    }

    private fun onSuccess(model: LeaderboardModel?) {
        Log.d("RESULT", model.toString())
    }

    private fun onLoading() {
    }
}