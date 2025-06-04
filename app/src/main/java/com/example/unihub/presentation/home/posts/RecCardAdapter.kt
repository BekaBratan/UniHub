package com.example.unihub.presentation.home.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContentProviderCompat.requireContext
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

//    var listenerClickAtFollow: RcViewItemClickIdCallback? = null
//    fun setOnFollowClickListener(listener: RcViewItemClickIdCallback) {
//        this.listenerClickAtFollow = listener
//    }

    var listenerClickAtItem: RcViewItemClickIdCallback? = null
    fun setOnClubClickListener(listener: RcViewItemClickIdCallback) {
        this.listenerClickAtItem = listener
    }

    inner class ClubsViewHolder(
        var binding: ItemRecCardBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun onBind(clubs: ClubsResponseItem) {
            binding.run {
                tvRecClub.text = clubs.name
                var isFollowed = false

                ivRecClub.setImageResource(R.drawable.puzzle_club)

                if (isFollowed)
                    btnFollow.text = "Unfollow"
                else
                    btnFollow.text = "Follow"

                btnFollow.setOnClickListener {
                    if (isFollowed) {
                        btnFollow.backgroundTintList =
                            AppCompatResources.getColorStateList(binding.root.context, R.color.blue_200)
                        btnFollow.text = "Unfollow"
                        btnFollow.setTextColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.blue_800
                            )
                        )
                    }
                    else {
                        btnFollow.backgroundTintList =
                            AppCompatResources.getColorStateList(binding.root.context, R.color.blue_800)
                        btnFollow.text = "Follow"
                        btnFollow.setTextColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.white_1000
                            )
                        )
                    }
                    isFollowed = !isFollowed
                }

                root.setOnClickListener {
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