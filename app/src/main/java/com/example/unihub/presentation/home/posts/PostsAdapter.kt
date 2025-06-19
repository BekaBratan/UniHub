package com.example.unihub.presentation.home.posts

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unihub.R
import com.example.unihub.data.model.post.PostsResponseItem
import com.example.unihub.databinding.ItemPostCardBinding
import com.example.unihub.utils.RcViewItemClickIdCallback
import com.example.unihub.utils.RcViewItemClickIdStringCallback
import com.google.android.material.imageview.ShapeableImageView
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

open class PostsAdapter(val isProfile: Boolean = false): RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

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

    var listenerClickDelete: RcViewItemClickIdCallback? = null
    fun setOnDeleteClickListener(listener: RcViewItemClickIdCallback) {
        this.listenerClickDelete = listener
    }

    var listenerClickRepost: RcViewItemClickIdCallback? = null
    fun setOnRepostClickListener(listener: RcViewItemClickIdCallback) {
        this.listenerClickRepost = listener
    }

    var listenerClickShare: RcViewItemClickIdStringCallback? = null
    fun setOnShareClickListener(listener: RcViewItemClickIdStringCallback) {
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
                tvTime.text = getTimeAgo(posts.createdAt.toString())
                tvPostName.text = posts.content
                var isLiked = false

                val clubImageView: ShapeableImageView = binding.clubAvatar.root.findViewById(R.id.ivClubAvatar)

                val index = (posts.id ?: 1) % 6

                if (posts.image?.isEmpty() == true) {
                    if (index == 1) {
                        clubImageView.setImageResource(R.drawable.club_1)
                        ivPostImage.setImageResource(R.drawable.post_1)
                    } else if (index == 2) {
                        clubImageView.setImageResource(R.drawable.club_2)
                        ivPostImage.setImageResource(R.drawable.post_2)
                    } else if (index == 3) {
                        clubImageView.setImageResource(R.drawable.club_3)
                        ivPostImage.setImageResource(R.drawable.post3)
                    } else if (index == 4) {
                        clubImageView.setImageResource(R.drawable.club_4)
                        ivPostImage.setImageResource(R.drawable.post_4)
                    } else if (index == 5) {
                        clubImageView.setImageResource(R.drawable.club_5)
                        ivPostImage.setImageResource(R.drawable.post_5)
                    } else if (index == 6) {
                        clubImageView.setImageResource(R.drawable.club_6)
                        ivPostImage.setImageResource(R.drawable.example_post)
                    }
                } else {
                    val base64String = posts.image ?: ""
                    val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                    ivPostImage.setImageBitmap(bitmap)
                }

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
                    listenerClickShare?.onClick("${posts.club} \n ${posts.content}")
                }

                if (isProfile)
                    btnDelete.visibility = View.VISIBLE
                else
                    btnDelete.visibility = View.GONE

                btnDelete.setOnClickListener {
                    listenerClickDelete?.onClick(posts.id)
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

    @SuppressLint("NewApi")
    fun getTimeAgo(isoTime: String): String {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        val updatedTime = ZonedDateTime.parse(isoTime, formatter)
        val now = ZonedDateTime.now(ZoneId.of("UTC"))
        val duration = Duration.between(updatedTime, now)

        return when {
            duration.toMinutes() < 1 -> "just now"
            duration.toMinutes() < 60 -> "${duration.toMinutes()} minutes ago"
            duration.toHours() < 24 -> "${duration.toHours()} hours ago"
            duration.toDays() < 7 -> "${duration.toDays()} days ago"
            else -> updatedTime.toLocalDate().toString() // Or format if needed
        }
    }
}