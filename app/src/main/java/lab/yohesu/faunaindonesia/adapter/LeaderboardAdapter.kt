package lab.yohesu.faunaindonesia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import lab.yohesu.faunaindonesia.databinding.LeaderboardItemBinding
import lab.yohesu.faunaindonesia.model.LeaderboardDataModel

class LeaderboardAdapter(var data: List<LeaderboardDataModel>) : RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LeaderboardItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LeaderboardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       with(holder){
            with(data[position]){
                binding.txtName.text = this.name
                binding.txtScore.text = this.score.toString()
            }
       }
    }


}