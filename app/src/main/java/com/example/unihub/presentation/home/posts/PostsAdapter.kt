package com.example.unihub.presentation.home.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unihub.R
import com.example.unihub.data.model.post.PostsResponseItem
import com.example.unihub.databinding.ItemPostCardBinding
import com.example.unihub.utils.RcViewItemClickIdCallback
import com.google.android.material.imageview.ShapeableImageView

open class PostsAdapter: RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<PostsResponseItem>() {
        override fun areItemsTheSame(
            oldItem: PostsResponseItem,
            newItem: PostsResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: PostsResponseItem,
            newItem: PostsResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<PostsResponseItem>) {
        differ.submitList(list)
    }

    var listenerClickLike: RcViewItemClickIdCallback? = null
    fun setOnLikeClickListener(listener: RcViewItemClickIdCallback) {
        this.listenerClickLike = listener
    }

    var listenerClickClubName: RcViewItemClickIdCallback? = null
    fun setOnClubNameClickListener(listener: RcViewItemClickIdCallback) {
        this.listenerClickClubName = listener
    }

    var listenerClickRepost: RcViewItemClickIdCallback? = null
    fun setOnRepostClickListener(listener: RcViewItemClickIdCallback) {
        this.listenerClickRepost = listener
    }

    var listenerClickShare: RcViewItemClickIdCallback? = null
    fun setOnShareClickListener(listener: RcViewItemClickIdCallback) {
        this.listenerClickShare = listener
    }

    inner class PostsViewHolder(
        var binding: ItemPostCardBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun onBind(posts: PostsResponseItem) {
            binding.run {
                tvClubName.text = posts.club?.name
                tvTime.text = posts.createdAt
                tvPostName.text = posts.content
                var isLiked = (posts.likes>300)

                if (posts.id == 1)
                    ivPostImage.setImageResource(R.drawable.post_1)
                else if (posts.id == 2)
                    ivPostImage.setImageResource(R.drawable.post_2)
                else if (posts.id == 3)
                    ivPostImage.setImageResource(R.drawable.post3)
                else if (posts.id == 4)
                    ivPostImage.setImageResource(R.drawable.post_4)
                else if (posts.id == 5)
                    ivPostImage.setImageResource(R.drawable.post_5)
                else
                    Glide.with(itemView.context)
                        .load(posts.image)
                        .into(ivPostImage)

                val clubAvatarImageView = itemView.findViewById<ShapeableImageView>(R.id.ivClubAvatar)

                if (posts.id == 1)
                    clubAvatarImageView.setImageResource(R.drawable.club_1)
                else if (posts.id == 2)
                    clubAvatarImageView.setImageResource(R.drawable.club_2)
                else if (posts.id == 3)
                    clubAvatarImageView.setImageResource(R.drawable.club_3)
                else if (posts.id == 4)
                    clubAvatarImageView.setImageResource(R.drawable.club_4)
                else if (posts.id == 5)
                    clubAvatarImageView.setImageResource(R.drawable.club_5)
                else
                    Glide.with(itemView.context)
                        .load(posts.image)
                        .into(clubAvatarImageView)



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
                    listenerClickLike?.onClick(posts.id)
                }

                tvClubName.setOnClickListener {
                    listenerClickClubName?.onClick(posts.club?.id)
                }

                btnRepost.setOnClickListener {
                    listenerClickRepost?.onClick(posts.id)
                }

                btnSend.setOnClickListener {
                    listenerClickShare?.onClick(posts.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostsViewHolder {
        return PostsViewHolder(
            ItemPostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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