package com.example.unihub.presentation.home.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unihub.R
import com.example.unihub.data.model.Clubs
import com.example.unihub.databinding.ItemRecInlineBinding
import com.example.unihub.utils.RcViewItemClickIdCallback
import com.example.unihub.utils.RcViewItemClickIdStringCallback
import javax.sql.RowSet

open class RecInlineAdapter: RecyclerView.Adapter<RecInlineAdapter.ClubsViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Clubs>() {
        override fun areItemsTheSame(
            oldItem: Clubs,
            newItem: Clubs
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Clubs,
            newItem: Clubs
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Clubs>) {
        differ.submitList(list)
    }

    var listenerClickAtItem: RcViewItemClickIdStringCallback? = null
    fun setOnFollowClickListener(listener: RcViewItemClickIdStringCallback) {
        this.listenerClickAtItem = listener
    }

    inner class ClubsViewHolder(
        var binding: ItemRecInlineBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        @SuppressLint("UseCompatLoadingForDrawables", "ResourceAsColor")
        fun onBind(clubs: Clubs) {
            binding.run {
                tvRecClub.text = clubs.name
                var isFollowed = clubs.isFollowed

//                if (clubs.image.isEmpty())
//                    ivRecClub.setImageResource(R.drawable.puzzle_club)
//                else
//                    Glide.with(itemView.context)
//                        .load(clubs.image)
//                        .into(ivRecClub)

                if (isFollowed) {
                    btnFollow.text = "Unfollow"
                    btnFollow.setTextColor(R.color.blue_900)
                    btnFollow.backgroundTintList = null
                }
                else {
                    btnFollow.text = "Follow"
                    btnFollow.setTextColor(R.color.white_1000)
                    btnFollow.backgroundTintList = itemView.context.getColorStateList(R.color.blue_800)
                }

                btnFollow.setOnClickListener {
                    if (isFollowed) {
                        btnFollow.text = "Unfollow"
                        btnFollow.setTextColor(R.color.blue_900)
                        btnFollow.backgroundTintList = null
                    }
                    else {
                        btnFollow.text = "Follow"
                        btnFollow.setTextColor(R.color.white_1000)
                        btnFollow.backgroundTintList = itemView.context.getColorStateList(R.color.blue_800)
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