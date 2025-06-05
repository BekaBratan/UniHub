package com.example.unihub.presentation.home.search

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unihub.R
import com.example.unihub.data.model.club.ClubsResponseItem
import com.example.unihub.databinding.ItemRecInlineBinding
import com.example.unihub.utils.RcViewItemClickIdCallback

open class RecInlineAdapter: RecyclerView.Adapter<RecInlineAdapter.ClubsViewHolder>() {

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
        var binding: ItemRecInlineBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        @SuppressLint("UseCompatLoadingForDrawables", "ResourceAsColor")
        fun onBind(clubs: ClubsResponseItem) {
            binding.run {
                tvRecClub.text = clubs.name
                var isFollowed = false

//                if (clubs.image.isEmpty())
//                    ivRecClub.setImageResource(R.drawable.puzzle_club)
//                else
//                    Glide.with(itemView.context)
//                        .load(clubs.image)
//                        .into(ivRecClub)

                if (isFollowed) {
                    btnFollow.backgroundTintList = itemView.context.getColorStateList(R.color.blue_200)
                    btnFollow.setTextColor(ContextCompat.getColor(itemView.context, R.color.blue_900))
                    btnFollow.text = root.context.getString(R.string.unfollow)
                }
                else {
                    btnFollow.backgroundTintList = itemView.context.getColorStateList(R.color.blue_800)
                    btnFollow.setTextColor(ContextCompat.getColor(itemView.context, R.color.white_1000))
                    btnFollow.text = root.context.getString(R.string.follow)
                }

                btnFollow.setOnClickListener {
                    if (isFollowed) {
                        btnFollow.text = root.context.getString(R.string.unfollow)
                        btnFollow.setTextColor(ContextCompat.getColor(itemView.context, R.color.blue_900))
                        btnFollow.backgroundTintList = itemView.context.getColorStateList(R.color.blue_200)
                    }
                    else {
                        btnFollow.text = root.context.getString(R.string.follow)
                        btnFollow.setTextColor(ContextCompat.getColor(itemView.context, R.color.white_1000))
                        btnFollow.backgroundTintList = itemView.context.getColorStateList(R.color.blue_800)
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
            ItemRecInlineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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