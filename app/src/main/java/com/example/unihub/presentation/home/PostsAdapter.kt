package com.example.unihub.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unihub.R
import com.example.unihub.data.Posts
import com.example.unihub.databinding.CardPostBinding
import com.example.unihub.utils.RcViewItemClickIdCallback

open class PostsAdapter: RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Posts>() {
        override fun areItemsTheSame(
            oldItem: Posts,
            newItem: Posts
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Posts,
            newItem: Posts
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Posts>) {
        differ.submitList(list)
    }

    var listenerClickAtItem: RcViewItemClickIdCallback? = null
    fun setOnLikeClickListener(listener: RcViewItemClickIdCallback) {
        this.listenerClickAtItem = listener
    }

    inner class PostsViewHolder(
        var binding: CardPostBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun onBind(posts: Posts) {
            binding.run {
                tvClubName.text = posts.club.name
                tvTime.text = posts.postedAt
                tvPostName.text = posts.description
                var isLiked = posts.isLiked

                Glide.with(itemView.context)
                    .load(posts.image)
                    .into(ivPostImage)

                if (isLiked)
                    btnLike.setImageResource(R.drawable.ic_liked)
                else
                    btnLike.setImageResource(R.drawable.ic_unliked)

                btnLike.setOnClickListener {
                    if (isLiked)
                        btnLike.setImageResource(R.drawable.ic_unliked)
                    else
                        btnLike.setImageResource(R.drawable.ic_liked)
                    isLiked = !isLiked
                    listenerClickAtItem?.onClick(posts.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostsViewHolder {
        return PostsViewHolder(
            CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: PostsViewHolder,
        position: Int
    ) {
        holder.onBind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}