package com.example.unihub.presentation.home.posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.unihub.R
import com.example.unihub.data.model.Clubs
import com.example.unihub.data.model.Posts
import com.example.unihub.databinding.FragmentHomeBinding
import com.example.unihub.databinding.ItemPostCardBinding
import com.example.unihub.utils.CustomDividerItemDecoration
import com.example.unihub.utils.RcViewItemClickIdCallback
import com.example.unihub.utils.RcViewItemClickIdStringCallback
import com.example.unihub.utils.SpacesItemDecoration
import com.example.unihub.utils.provideNavigationHost

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var postBinding: ItemPostCardBinding

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

        postsAdapter.setOnClubNameClickListener(
            object : RcViewItemClickIdStringCallback {
                override fun onClick(id: String) {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToClubPageFragment(id))
                }
            }
        )

        val clubsAdapter = RecCardAdapter()
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

            tvClubName.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToClubPageFragment("67f2f4ebb2608eae089764bf"))
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

            llSearch.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
            }

            etSearch.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
            }

            btnSubscriptions.setOnClickListener {
                val color = ContextCompat.getColor(requireContext(), R.color.blue_800)
                val buttonDrawable = DrawableCompat.wrap(btnSubscriptions.background)
                DrawableCompat.setTint(buttonDrawable, color)
                btnSubscriptions.background = buttonDrawable
                btnSubscriptions.setTextColor(ContextCompat.getColor(requireContext(), R.color.white_1000))
                btnSubscriptions.isEnabled = false

                val color1 = ContextCompat.getColor(requireContext(), R.color.white_900)
                val buttonDrawable1 = DrawableCompat.wrap(btnRecommendations.background)
                DrawableCompat.setTint(buttonDrawable1, color1)
                btnRecommendations.background = buttonDrawable1
                btnRecommendations.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey_200))
                btnRecommendations.isEnabled = true

                tvWelcomeName.visibility = View.GONE
                llClubs.visibility = View.GONE
                llFirstPost.visibility = View.GONE
                llSearch.visibility = View.GONE
            }

            btnRecommendations.setOnClickListener {
                val color = ContextCompat.getColor(requireContext(), R.color.blue_800)
                val buttonDrawable = DrawableCompat.wrap(btnRecommendations.background)
                DrawableCompat.setTint(buttonDrawable, color)
                btnRecommendations.background = buttonDrawable
                btnRecommendations.setTextColor(ContextCompat.getColor(requireContext(), R.color.white_1000))
                btnRecommendations.isEnabled = false

                val color1 = ContextCompat.getColor(requireContext(), R.color.white_900)
                val buttonDrawable1 = DrawableCompat.wrap(btnSubscriptions.background)
                DrawableCompat.setTint(buttonDrawable1, color1)
                btnSubscriptions.background = buttonDrawable1
                btnSubscriptions.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey_200))
                btnSubscriptions.isEnabled = true

                tvWelcomeName.visibility = View.VISIBLE
                llClubs.visibility = View.VISIBLE
                llFirstPost.visibility = View.VISIBLE
                llSearch.visibility = View.VISIBLE
            }
        }

    }
}