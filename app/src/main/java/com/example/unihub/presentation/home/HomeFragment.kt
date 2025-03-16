package com.example.unihub.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.unihub.R
import com.example.unihub.data.Clubs
import com.example.unihub.data.Posts
import com.example.unihub.databinding.CardPostBinding
import com.example.unihub.databinding.FragmentHomeBinding
import com.example.unihub.utils.CustomDividerItemDecoration
import com.example.unihub.utils.RcViewItemClickIdCallback
import com.example.unihub.utils.SpacesItemDecoration
import com.example.unihub.utils.provideNavigationHost

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var postBinding: CardPostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(false)

        val firstPost = Posts()
        val postsAdapter = PostsAdapter()
        postsAdapter.submitList(List(10) { Posts() })

        postsAdapter.setOnLikeClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int) {

                }
            }
        )

        val clubsAdapter = ClubsAdapter()
        clubsAdapter.submitList(List(10) { Clubs() })

        clubsAdapter.setOnFollowClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int) {

                }
            }
        )

        val verticalLinearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val horizontalLinearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        postBinding = binding.firstPost

        postBinding.run {
            llClubInfo
            tvClubName.text = firstPost.club.name
            tvTime.text = firstPost.postedAt
            tvPostName.text = firstPost.description
            var isLiked = firstPost.isLiked

            if (firstPost.image.isEmpty())
                ivPostImage.setImageResource(R.drawable.example_post)
            else
                Glide.with(requireContext())
                    .load(firstPost.image)
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
            }
        }

        binding.run {
            rvPosts.layoutManager = verticalLinearLayoutManager
            rvPosts.adapter = postsAdapter
            rvPosts.addItemDecoration(
                CustomDividerItemDecoration(
                    getDrawable(
                        requireContext(),
                        R.drawable.divider_1dp
                    )!!
                )
            )

            rvClubs.layoutManager = horizontalLinearLayoutManager
            rvClubs.adapter = clubsAdapter
            val space = resources.getDimensionPixelSize(R.dimen.dp_12)
            rvClubs.addItemDecoration(SpacesItemDecoration(space))
        }

    }
}