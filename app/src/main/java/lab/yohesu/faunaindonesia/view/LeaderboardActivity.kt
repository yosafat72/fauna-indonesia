package lab.yohesu.faunaindonesia.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import lab.yohesu.faunaindonesia.adapter.LeaderboardAdapter
import lab.yohesu.faunaindonesia.adapter.callback.SwipeToDeleteCallback
import lab.yohesu.faunaindonesia.database.DatabaseBuilder
import lab.yohesu.faunaindonesia.database.DatabaseHelperImp
import lab.yohesu.faunaindonesia.databinding.ActivityLeaderboardBinding
import lab.yohesu.faunaindonesia.model.LeaderboardDataModel
import lab.yohesu.faunaindonesia.model.LeaderboardModel
import lab.yohesu.faunaindonesia.model.UIModel
import lab.yohesu.faunaindonesia.service.Status
import lab.yohesu.faunaindonesia.viewmodel.LeaderboardViewModel
import lab.yohesu.faunaindonesia.viewmodel.factory.ViewModelFactory

class LeaderboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLeaderboardBinding

    var tempArrLeaderboard: List<LeaderboardDataModel?>? = null

    private val viewModel: LeaderboardViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(DatabaseHelperImp(DatabaseBuilder.getInstance(applicationContext))))[LeaderboardViewModel::class.java]
    }

    private lateinit var leaderboardAdapter: LeaderboardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()

        viewModel.fetchAllLeaderboards()

        setupRecycler()

    }

    private fun setupRecycler(){
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerLeaderboard.layoutManager = layoutManager

        //Swipe
        val swipeHandler = object : SwipeToDeleteCallback(context = this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                leaderboardAdapter.removeAt(viewHolder.adapterPosition)
//                Log.d("DATA", viewHolder.itemId.toString())
//                viewModel.deleteLeaderboard(viewHolder.itemId.toInt())
                val position = viewHolder.adapterPosition
                val item = leaderboardAdapter.data?.get(position)
                leaderboardAdapter.removeAt(position)
                if (item != null) {
                    viewModel.deleteLeaderboard(item)
                }
                Log.d("DATA", item.toString())
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerLeaderboard)

    }

    private fun observeViewModel(){
        lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(lifecycle).collectLatest {
                when(it.status){
                    Status.LOADING -> onLoading()
                    Status.SUCCESS -> onSuccess(it.data)
                    Status.ERROR -> onError(it.message)
                    Status.SUCCESS_DELETED_TO_ROOM -> onDeleted(it.data)
                    else -> {}
                }
            }
        }
    }

    private fun onDeleted(model: UIModel<Any>?) {
        Log.d("RESULT_Deleted", model.toString())
        val dataModel = model?.dataModel as LeaderboardModel
        tempArrLeaderboard = dataModel.data
        leaderboardAdapter = LeaderboardAdapter(dataModel.data)
    }

    private fun onError(message: String?) {
        Log.d("Running-Error", "$message")
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onSuccess(model: UIModel<Any>?) {
        Log.d("RESULT", model.toString())
        if (model != null) {
            val dataModel = model.dataModel as LeaderboardModel
            tempArrLeaderboard = dataModel.data
            leaderboardAdapter = LeaderboardAdapter(dataModel.data)
            binding.recyclerLeaderboard.adapter = leaderboardAdapter
        }
    }

    private fun onLoading() {
    }
}