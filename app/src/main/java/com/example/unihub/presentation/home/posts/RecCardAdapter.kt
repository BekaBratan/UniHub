package com.example.unihub.presentation.home.posts

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unihub.R
import com.example.unihub.data.model.club.ClubsResponseItem
import com.example.unihub.databinding.ItemRecCardBinding
import com.example.unihub.utils.RcViewItemClickIdCallback

open class RecCardAdapter: RecyclerView.Adapter<RecCardAdapter.ClubsViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<ClubsResponseItem>() {
        override fun areItemsTheSame(
            oldItem: ClubsResponseItem,
            newItem: ClubsResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ClubsResponseItem,
            newItem: ClubsResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<ClubsResponseItem>) {
        differ.submitList(list)
    }

    var listenerClickAtItem: RcViewItemClickIdCallback? = null
    fun setOnFollowClickListener(listener: RcViewItemClickIdCallback) {
        this.listenerClickAtItem = listener
    }

    inner class ClubsViewHolder(
        var binding: ItemRecCardBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        @SuppressLint("ResourceAsColor")
        fun onBind(clubs: ClubsResponseItem) {
            binding.run {
                tvRecClub.text = clubs.name
                var isFollowed = false

                if (clubs.id == 1)
                    ivRecClub.setImageResource(R.drawable.club_1)
                else if (clubs.id == 2)
                    ivRecClub.setImageResource(R.drawable.club_2)
                else if (clubs.id == 3)
                    ivRecClub.setImageResource(R.drawable.club_3)
                else if (clubs.id == 4)
                    ivRecClub.setImageResource(R.drawable.club_4)
                else if (clubs.id == 5)
                    ivRecClub.setImageResource(R.drawable.club_5)
                else if (clubs.id == 6)
                    ivRecClub.setImageResource(R.drawable.club_6)
                else if (clubs.id == 7)
                    ivRecClub.setImageResource(R.drawable.club_7)
                else if (clubs.id == 8)
                    ivRecClub.setImageResource(R.drawable.club_8)

                if (isFollowed) {
                    btnFollow.text = "Unfollow"
                    btnFollow.setBackgroundResource(R.drawable.sh_14dp)
                    btnFollow.backgroundTintList = null
                    btnFollow.setTextColor(ContextCompat.getColor(binding.root.context, R.color.blue_800))
                }
                else {
                    btnFollow.text = "Follow"
                    btnFollow.setBackgroundResource(R.drawable.sh_rounded)
                    btnFollow.backgroundTintList = binding.root.context.getColorStateList(R.color.blue_800)
                    btnFollow.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white_1000))
                }

                btnFollow.setOnClickListener {
                    if (isFollowed) {
                        btnFollow.text = "Unfollow"
                        btnFollow.setBackgroundResource(R.drawable.sh_14dp)
                        btnFollow.backgroundTintList = null
                        btnFollow.setTextColor(ContextCompat.getColor(binding.root.context, R.color.blue_800))
                    }
                    else {
                        btnFollow.text = "Follow"
                        btnFollow.setBackgroundResource(R.drawable.sh_rounded)
                        btnFollow.backgroundTintList = binding.root.context.getColorStateList(R.color.blue_800)
                        btnFollow.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white_1000))
                    }
                    isFollowed = !isFollowed
                    listenerClickAtItem?.onClick(clubs.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClubsViewHolder {
        return ClubsViewHolder(
            ItemRecCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: ClubsViewHolder,
        position: Int
    ) {
        holder.onBind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}