package com.example.unihub.presentation.home.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unihub.R
import com.example.unihub.data.model.Clubs
import com.example.unihub.databinding.ItemRecCardBinding
import com.example.unihub.utils.RcViewItemClickIdCallback
import com.example.unihub.utils.RcViewItemClickIdStringCallback

open class RecCardAdapter: RecyclerView.Adapter<RecCardAdapter.ClubsViewHolder>() {

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
        var binding: ItemRecCardBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun onBind(clubs: Clubs) {
            binding.run {
                tvRecClub.text = clubs.name
                var isFollowed = clubs.isFollowed

                if (clubs.image.isEmpty())
                    ivRecClub.setImageResource(R.drawable.puzzle_club)
                else
                    Glide.with(itemView.context)
                        .load(clubs.image)
                        .into(ivRecClub)

                if (isFollowed)
                    btnFollow.text = "Unfollow"
                else
                    btnFollow.text = "Follow"

                btnFollow.setOnClickListener {
                    if (isFollowed)
                        btnFollow.text = "Unfollow"
                    else
                        btnFollow.text = "Follow"
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