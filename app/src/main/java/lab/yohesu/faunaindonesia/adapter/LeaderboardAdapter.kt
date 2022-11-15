package lab.yohesu.faunaindonesia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieDrawable
import lab.yohesu.faunaindonesia.R
import lab.yohesu.faunaindonesia.databinding.LeaderboardItemBinding
import lab.yohesu.faunaindonesia.model.LeaderboardDataModel

class LeaderboardAdapter(var data: List<LeaderboardDataModel?>?) : RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LeaderboardItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LeaderboardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       with(holder){
            with(data?.get(position)){
                binding.txtName.text = this?.name
                binding.txtScore.text = this?.score.toString()
                when(position){
                    0 -> binding.lottieBadge.setAnimation(R.raw.winner_01)
                    1 -> binding.lottieBadge.setAnimation(R.raw.winner_02)
                    2 -> binding.lottieBadge.setAnimation(R.raw.winner_dll)
                    else -> binding.lottieBadge.setAnimation(R.raw.winner_03)
                }
                binding.lottieBadge.playAnimation()
                binding.lottieBadge.repeatCount = LottieDrawable.INFINITE
            }
       }
    }


}