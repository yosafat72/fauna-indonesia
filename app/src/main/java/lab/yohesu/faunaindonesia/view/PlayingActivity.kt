package lab.yohesu.faunaindonesia.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import lab.yohesu.faunaindonesia.databinding.ActivityPlayingBinding
import lab.yohesu.faunaindonesia.model.PlayingDataModel
import lab.yohesu.faunaindonesia.model.PlayingModel
import lab.yohesu.faunaindonesia.service.Status
import lab.yohesu.faunaindonesia.viewmodel.PlayingViewModel

class PlayingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayingBinding
    private val viewModel : PlayingViewModel by lazy {
        ViewModelProvider(this)[PlayingViewModel::class.java]
    }

    private var tempOption: String? = ""
    private var tempScope = 0
    private var arrPosition = 0
    private var tempArrQuestion: List<PlayingDataModel?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()

        viewModel.fetchQuestionLevelOne(this)

        binding.btnNext.setOnClickListener {
            if (arrPosition < (tempArrQuestion?.size ?: 0) - 1){
                tempArrQuestion?.get(arrPosition)?.let { calculateScore(it) }
                arrPosition++
                tempArrQuestion?.get(arrPosition)?.let { it1 -> setToView(it1) }
            }else{
                tempArrQuestion?.get(arrPosition)?.let { calculateScore(it) }
            }
            clearRadio()
        }

        binding.radioGroup.setOnCheckedChangeListener { _ , checkedId ->
            val radio = binding.root.findViewById<com.jbvincey.nestedradiobutton.NestedRadioButton>(checkedId)
            tempOption = radio.text.toString()
        }
    }

    private fun clearRadio(){
        binding.radioOptionA.isChecked = false
        binding.radioOptionB.isChecked = false
        binding.radioOptionC.isChecked = false
        binding.radioOptionD.isChecked = false
    }

    @SuppressLint("SetTextI18n")
    private fun calculateScore(model: PlayingDataModel){
        if(tempOption.equals(model.correctAnswer)){
            binding.txtScore.text = "Score : " + (tempScope + model.correctScore!!).toString()
            tempScope += model.correctScore
        }else{
            binding.txtScore.text = "Score : " + (tempScope + model.wrongScore!!).toString()
            tempScope += model.wrongScore
        }
        tempOption = ""
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
        if (model != null) {
            tempArrQuestion = model.data!!
            model.data.first()?.let { setToView(model = it) }
        }
    }

    private fun onError(message: String?) {
        Log.d("Running-Error", "$message")
        finish()
    }

    private fun setToView(model: PlayingDataModel){
        model.question.let { binding.txtQuestion.text = it }
        model.optionA.let { binding.radioOptionA.text = it }
        model.optionB.let { binding.radioOptionB.text = it }
        model.optionC.let { binding.radioOptionC.text = it }
        model.optionD.let { binding.radioOptionD.text = it }
        model.imageQuestion.let { Glide.with(this).load(it).fitCenter().into(binding.imgQuestion) }

    }

}

