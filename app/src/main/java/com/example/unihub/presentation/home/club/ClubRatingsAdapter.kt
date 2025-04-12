package com.example.unihub.presentation.home.club

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unihub.data.model.Club
import com.example.unihub.databinding.ItemEventCardBinding
import com.example.unihub.databinding.ItemRatingProgressBinding
import com.example.unihub.utils.RcViewItemClickIdStringCallback

open class ClubRatingsAdapter: RecyclerView.Adapter<ClubRatingsAdapter.RatingsViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Club>() {
        override fun areItemsTheSame(
            oldItem: Club,
            newItem: Club
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Club,
            newItem: Club
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Club>) {
        differ.submitList(list)
    }


    inner class RatingsViewHolder(
        var binding: ItemRatingProgressBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun onBind(club: Club) {
            binding.run {
                tvClubName.text = club.name
                progressBar.progress = (club.rating.toInt()/5)*100
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RatingsViewHolder {
        return RatingsViewHolder(
            ItemRatingProgressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: RatingsViewHolder,
        position: Int
    ) {
        holder.onBind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}