package lab.yohesu.faunaindonesia.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import lab.yohesu.faunaindonesia.R
import lab.yohesu.faunaindonesia.database.DatabaseBuilder
import lab.yohesu.faunaindonesia.database.DatabaseHelperImp
import lab.yohesu.faunaindonesia.databinding.ActivityPlayingBinding
import lab.yohesu.faunaindonesia.model.LeaderboardDataModel
import lab.yohesu.faunaindonesia.model.PlayingDataModel
import lab.yohesu.faunaindonesia.model.PlayingModel
import lab.yohesu.faunaindonesia.model.UIModel
import lab.yohesu.faunaindonesia.service.Status
import lab.yohesu.faunaindonesia.utils.AlertClickListener
import lab.yohesu.faunaindonesia.utils.AlertHelper
import lab.yohesu.faunaindonesia.utils.MusicHelper
import lab.yohesu.faunaindonesia.viewmodel.PlayingViewModel
import lab.yohesu.faunaindonesia.viewmodel.factory.ViewModelFactory

class PlayingActivity : AppCompatActivity(), AlertClickListener {

    private lateinit var binding: ActivityPlayingBinding
    private val viewModel : PlayingViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(DatabaseHelperImp(DatabaseBuilder.getInstance(applicationContext))))[PlayingViewModel::class.java]
    }
    private val alertHelper: AlertHelper by lazy {
        AlertHelper()
    }

    private val musicHelper: MusicHelper by lazy {
        MusicHelper()
    }

    private var gameLevel: Int = 0

    private var tempOption: String? = ""
    private var tempScope = 0
    private var arrPosition = 0
    private var tempArrQuestion: List<PlayingDataModel?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayingBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val intent = intent
        gameLevel = intent.getIntExtra("gameLevel", 1)

        alertHelper.listener = this

        observeViewModel()

        if (gameLevel == 1){
            viewModel.fetchQuestionLevelOne(this)
        }else if(gameLevel == 2){
            viewModel.fetchQuestionLevelTwo(this)
        }

        binding.btnNext.setOnClickListener {
            if (arrPosition < (tempArrQuestion?.size ?: 0) - 1){
                tempArrQuestion?.get(arrPosition)?.let { calculateScore(it) }
                arrPosition++
                tempArrQuestion?.get(arrPosition)?.let { it1 -> setToView(it1) }
            }else{
                tempArrQuestion?.get(arrPosition)?.let { calculateScore(it) }
                alertHelper.AlertGameOver(this, tempScope)
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
                    Status.SUCCESS_INSERT_TO_ROOM -> onSuccessInsert(it.data)
                    else -> {}
                }
            }
        }
    }

    private fun onSuccessInsert(model: UIModel<Any>?) {
        Log.d("RESULT_INSERT", model.toString())
        val intent = Intent(this, MenuActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun onLoading() {
    }

    private fun onSuccess(model: UIModel<Any>?) {
        Log.d("RESULT", model.toString())
        if (model != null) {
            val objects = model.dataModel  as PlayingModel
            tempArrQuestion = objects.data
            tempArrQuestion?.first()?.let { setToView(model = it) }
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

        when (gameLevel) {
            1 -> {
                model.imageQuestion.let { Glide.with(this).load(it).fitCenter().into(binding.imgQuestion) }
            }
            2 -> {
                binding.imgQuestion.setImageResource(R.drawable.sound_wave)
                model.soundQuestion.let {
                    if (it != null) {
                        musicHelper.playMusicQuestion(it)
                    }
                }
            }
            3 -> {
                model.imageQuestion.let { Glide.with(this).load(it).fitCenter().into(binding.imgQuestion) }
            }
            else -> {
                model.imageQuestion.let { Glide.with(this).load(it).fitCenter().into(binding.imgQuestion) }
            }
        }


    }

    override fun onCloseAlertGameOver(name: String, score: Int) {
        viewModel.insertIntoRoom(
            LeaderboardDataModel(
                id = (0..1000).shuffled().last(),
                name = name,
                score = score,
                level = gameLevel,
            )
        )
    }

    override fun onResume() {
        super.onResume()
        if(gameLevel == 2){
            musicHelper.resumeMusic()
        }
    }

    override fun onPause() {
        if(gameLevel == 2){
            musicHelper.pauseMusic()
        }
        super.onPause()
    }

    override fun onDestroy() {
        if (gameLevel == 2){
            musicHelper.stopMusic()
        }
        super.onDestroy()
    }
}

