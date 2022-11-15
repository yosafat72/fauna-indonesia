package lab.yohesu.faunaindonesia.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import lab.yohesu.faunaindonesia.databinding.ActivityLearningBinding
import lab.yohesu.faunaindonesia.model.LearningDataModel
import lab.yohesu.faunaindonesia.model.LearningModel
import lab.yohesu.faunaindonesia.model.UIModel
import lab.yohesu.faunaindonesia.service.Status
import lab.yohesu.faunaindonesia.viewmodel.LearningViewModel

class LearningActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLearningBinding

    private val viewModel : LearningViewModel by lazy {
        ViewModelProvider(this)[LearningViewModel::class.java]
    }

    private var arrPosition = 0
    private var tempArrAnimal: List<LearningDataModel?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()

        viewModel.fetchLearning(ctx = this)

        binding.btnPrev.setOnClickListener {
            if(arrPosition > 0){
                arrPosition--
                tempArrAnimal?.get(arrPosition)?.let { it1 -> setLearning(it1) }
            }
        }
        binding.btnNext.setOnClickListener {
            if (arrPosition < (tempArrAnimal?.size ?: 0) - 1){
                arrPosition++
                tempArrAnimal?.get(arrPosition)?.let { it1 -> setLearning(it1) }
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
        finish()
    }

    private fun onSuccess(model: UIModel<Any>?) {
        Log.d("RESULT", model.toString())
        if (model != null) {
            val dataModel = model.dataModel as LearningModel
            tempArrAnimal = dataModel.data
            tempArrAnimal?.first()?.let { setLearning(it) }
        }
    }

    private fun onLoading() {

    }

    private fun setLearning(model: LearningDataModel){
        model.image.let { it?.let { it1 -> Glide.with(this).load(it1).fitCenter().into(binding.imgAnimal) } }
        model.name.let { it?.let { it1 -> binding.txtAnimal.text = it1 } }
        model.site.let { it?.let { it1 -> loadLearningWeb(it1) } }
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