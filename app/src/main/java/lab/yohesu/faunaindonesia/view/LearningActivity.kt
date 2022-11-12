package lab.yohesu.faunaindonesia.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import lab.yohesu.faunaindonesia.databinding.ActivityLearningBinding
import lab.yohesu.faunaindonesia.model.LearningDataModel
import lab.yohesu.faunaindonesia.model.LearningModel
import lab.yohesu.faunaindonesia.service.Status
import lab.yohesu.faunaindonesia.viewmodel.LearningViewModel

class LearningActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLearningBinding

    private val viewModel : LearningViewModel by lazy {
        ViewModelProvider(this)[LearningViewModel::class.java]
    }

    private var arrPosition = 0
    private var tempArrAnimal = listOf<LearningDataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()

        viewModel.fetchLearning()

        binding.btnPrev.setOnClickListener {
            if(arrPosition > 0){
                arrPosition--
                setLearning(tempArrAnimal[arrPosition])
            }
        }
        binding.btnNext.setOnClickListener {
            if (arrPosition < tempArrAnimal.size - 1){
                arrPosition++
                setLearning(tempArrAnimal[arrPosition])
            }
        }

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

    private fun onSuccess(data: LearningModel?) {
        if (data != null) {
            tempArrAnimal = data.learningData
            setLearning(data.learningData.first())
        }
    }

    private fun onLoading() {

    }

    private fun setLearning(data: LearningDataModel){
        data.animalImage.let { it?.let { it1 -> binding.imgAnimal.setImageResource(it1) } }
        data.animalName.let { it?.let { it1 -> binding.txtAnimal.setText(it1) } }
        data.animalSite.let { it?.let { it1 -> loadLearningWeb(it1) } }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadLearningWeb(url: String){
        url.let {
            binding.webView.webViewClient = WebViewClient()
            binding.webView.loadUrl(it)
            binding.webView.settings.javaScriptEnabled = true
            binding.webView.settings.setSupportZoom(true)
        }
    }
}