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
import com.example.unihub.data.model.club.ClubsResponseItem
import com.example.unihub.data.model.club.Head
import com.example.unihub.data.model.post.Club
import com.example.unihub.data.model.post.PostsResponseItem
import com.example.unihub.data.model.post.User
import com.example.unihub.databinding.FragmentHomeBinding
import com.example.unihub.databinding.ItemPostCardBinding
import com.example.unihub.utils.CustomDividerItemDecoration
import com.example.unihub.utils.RcViewItemClickIdCallback
import com.example.unihub.utils.SpacesItemDecoration
import com.example.unihub.utils.provideNavigationHost
import com.google.android.material.imageview.ShapeableImageView
import kotlin.collections.listOf

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
        provideNavigationHost()?.setupBottomNavForRole(false)

        val firstPost = PostsResponseItem(
            id = 1,
            club = Club(
                id = 1,
                name = "International Club"
            ),
            content = "We invite you to our festival! Join us for a day of fun and excitement.",
            createdAt = "49m",
            image = "post_1",
            likes = 100,
            title = "Sample Title",
            user = User(
                id = 1,
                name = "John",
                surname = "Doe"
            )
        )

        val postsAdapter = PostsAdapter()
        postsAdapter.submitList(
            listOf(
                PostsResponseItem(
                    id = 2,
                    club = Club(
                        id = 2,
                        name = "Language Club"
                    ),
                    content = "Movie night! Join us for a screening of 'Inception' followed by a discussion.",
                    createdAt = "2h",
                    image = "post_2",
                    likes = 100,
                    title = "Sample Title",
                    user = User(
                        id = 1,
                        name = "John",
                        surname = "Doe"
                    )
                ),
                PostsResponseItem(
                    id = 3,
                    club = Club(
                        id = 3,
                        name = "Music Club"
                    ),
                    content = "Open air concert! Join us for a night of live music and fun.",
                    createdAt = "4h",
                    image = "sample_post_image_url",
                    likes = 100,
                    title = "Sample Title",
                    user = User(
                        id = 1,
                        name = "John",
                        surname = "Doe"
                    )
                ),
                PostsResponseItem(
                    id = 4,
                    club = Club(
                        id = 4,
                        name = "King Speech"
                    ),
                    content = "King speech and values of the club",
                    createdAt = "9h",
                    image = "sample_post_image_url",
                    likes = 100,
                    title = "Sample Title",
                    user = User(
                        id = 1,
                        name = "John",
                        surname = "Doe"
                    )
                ),
                PostsResponseItem(
                    id = 5,
                    club = Club(
                        id = 5,
                        name = "IT Club"
                    ),
                    content = "Join to typeracer contest, win a prize!",
                    createdAt = "14h",
                    image = "sample_post_image_url",
                    likes = 100,
                    title = "Sample Title",
                    user = User(
                        id = 1,
                        name = "John",
                        surname = "Doe"
                    )
                ),
            )
        )

        postsAdapter.setOnLikeClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int?) {

                }
            }
        )

        postsAdapter.setOnClubNameClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int?) {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToClubPageFragment(id?: 1, ""))
                }
            }
        )

        postsAdapter.setOnRepostClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int?) {
                    val repostBottomSheet = RepostBottomSheet(id?:1)
                    repostBottomSheet.show(childFragmentManager, repostBottomSheet.tag)
                }
            }
        )

        postsAdapter.setOnShareClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int?) {
                    val shareBottomSheet = ShareBottomSheet(id?:1)
                    shareBottomSheet.show(childFragmentManager, shareBottomSheet.tag)
                }
            }
        )

        val clubsAdapter = RecCardAdapter()
        clubsAdapter.submitList (
            listOf (
                ClubsResponseItem (
                    createdAt = "2023-01-01T00:00:00Z",
                    description = "Sample description",
                    goal = "Sample goal",
                    head = Head(
                        id = 1,
                        name = "John",
                        surname = "Doe"
                    ),
                    id = 1,
                    name = "International Club",
                    rating = 5
                ),
                ClubsResponseItem (
                    createdAt = "2023-01-01T00:00:00Z",
                    description = "Sample description",
                    goal = "Sample goal",
                    head = Head(
                        id = 1,
                        name = "John",
                        surname = "Doe"
                    ),
                    id = 2,
                    name = "Language Club",
                    rating = 5
                ),
                ClubsResponseItem (
                    createdAt = "2023-01-01T00:00:00Z",
                    description = "Sample description",
                    goal = "Sample goal",
                    head = Head(
                        id = 1,
                        name = "John",
                        surname = "Doe"
                    ),
                    id = 3,
                    name = "Music Club",
                    rating = 5
                ),
                ClubsResponseItem (
                    createdAt = "2023-01-01T00:00:00Z",
                    description = "Sample description",
                    goal = "Sample goal",
                    head = Head(
                        id = 1,
                        name = "John",
                        surname = "Doe"
                    ),
                    id = 4,
                    name = "King Speech",
                    rating = 5
                ),
                ClubsResponseItem (
                    createdAt = "2023-01-01T00:00:00Z",
                    description = "Sample description",
                    goal = "Sample goal",
                    head = Head(
                        id = 1,
                        name = "John",
                        surname = "Doe"
                    ),
                    id = 5,
                    name = "IT Club",
                    rating = 5
                ),
                ClubsResponseItem (
                    createdAt = "2023-01-01T00:00:00Z",
                    description = "Sample description",
                    goal = "Sample goal",
                    head = Head(
                        id = 1,
                        name = "John",
                        surname = "Doe"
                    ),
                    id = 6,
                    name = "Orlean",
                    rating = 5
                ),
                ClubsResponseItem (
                    createdAt = "2023-01-01T00:00:00Z",
                    description = "Sample description",
                    goal = "Sample goal",
                    head = Head(
                        id = 1,
                        name = "John",
                        surname = "Doe"
                    ),
                    id = 7,
                    name = "Art Club",
                    rating = 5
                ),
                ClubsResponseItem (
                    createdAt = "2023-01-01T00:00:00Z",
                    description = "Sample description",
                    goal = "Sample goal",
                    head = Head(
                        id = 1,
                        name = "John",
                        surname = "Doe"
                    ),
                    id = 8,
                    name = "Zhasa",
                    rating = 5
                ),
            )
        )

        clubsAdapter.setOnFollowClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int?) {

                }
            }
        )

        val verticalLinearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val horizontalLinearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        postBinding = binding.firstPost

        postBinding.run {
            llClubInfo
            tvClubName.text = firstPost.club?.name
            tvTime.text = firstPost.createdAt
            tvPostName.text = firstPost.content
            var isLiked = false

            ivPostImage.setImageResource(R.drawable.post_1)
            val clubAvatarImageView = view.findViewById<ShapeableImageView>(R.id.ivClubAvatar)
            clubAvatarImageView.setImageResource(R.drawable.club_1)

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
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToClubPageFragment(firstPost.club?.id ?: 1, ""))
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