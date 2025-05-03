package com.example.unihub.presentation.home.club

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unihub.R
import com.example.unihub.data.model.club.ClubEventsResponseItem
import com.example.unihub.data.model.event.EventsResponseItem
import com.example.unihub.data.model.post.Club
import com.example.unihub.data.model.post.PostsResponseItem
import com.example.unihub.data.model.post.User
import com.example.unihub.databinding.FragmentClubPageBinding
import com.example.unihub.presentation.home.posts.PostsAdapter
import com.example.unihub.utils.CustomDividerItemDecoration
import com.example.unihub.utils.RcViewItemClickIdCallback
import com.example.unihub.utils.provideNavigationHost

class ClubPageFragment : Fragment() {

    private lateinit var binding: FragmentClubPageBinding
    private val clubViewModel: ClubViewModel by viewModels()
    private val args: ClubPageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClubPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(false)

        var clubMotto = ""
        var clubInfo = ""

        if (args.type == "book"){
            showCustomDialogBox()
            binding.run {
                idAll.visibility = View.INVISIBLE
                idBooking.visibility = View.VISIBLE
                idRating.visibility = View.INVISIBLE

                rvPosts.visibility = View.GONE
                rvEvents.visibility = View.VISIBLE
                llRatings.visibility = View.GONE
            }
        }


        val postsAdapter = PostsAdapter()

        postsAdapter.submitList(
            listOf(
                PostsResponseItem(
                    id = 3,
                    club = Club(
                        id = 3,
                        name = "Music Club"
                    ),
                    content = "Open air concert! Join us for a night of live music and fun.",
                    createdAt = "4h",
                    image = "sample_post_image_url",
                    likes = 200,
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
                    content = "Feel music with us! Join our club for weekly jam sessions and more.",
                    createdAt = "4h",
                    image = "sample_post_image_url",
                    likes = 200,
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

                }
            }
        )

        val eventsAdapter = EventCardsAdapter()

        eventsAdapter.submitList(
            listOf(
                ClubEventsResponseItem(
                    createdAt = "6h",
                    eventDate = "18th May, 2025",
                    eventName = "Air festival",
                    id = 1,
                    location = "SDU Red Hall",
                    shortDescription = "Join us for an unforgettable air festival at SDU Life! ",
                ),
                ClubEventsResponseItem(
                    createdAt = "18h",
                    eventDate = "18th Jan, 2025",
                    eventName = "Air festival",
                    id = 2,
                    location = "SDU Life",
                    shortDescription = "Don't miss out on this exciting event!",
                ),
            )
        )

        eventsAdapter.setOnCardClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int?) {
                    findNavController().navigate(
                        ClubPageFragmentDirections.actionClubPageFragmentToBookingFragment(id!!)
                    )
                }
            }
        )

        val ratingsAdapter = ClubRatingsAdapter()

        val verticalLinearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.run {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnFollow.setOnClickListener {
                btnFollow.text = "Unfollow"
                btnFollow.setTextColor(ContextCompat.getColor(binding.root.context, R.color.blue_800))
                btnFollow.backgroundTintList = null
            }

            tvAll.setOnClickListener {
                idAll.visibility = View.VISIBLE
                idBooking.visibility = View.INVISIBLE
                idRating.visibility = View.INVISIBLE

                rvPosts.visibility = View.VISIBLE
                rvEvents.visibility = View.GONE
                llRatings.visibility = View.GONE
            }

            tvBooking.setOnClickListener {
                idAll.visibility = View.INVISIBLE
                idBooking.visibility = View.VISIBLE
                idRating.visibility = View.INVISIBLE

                rvPosts.visibility = View.GONE
                rvEvents.visibility = View.VISIBLE
                llRatings.visibility = View.GONE
            }

            tvRating.setOnClickListener {
                idAll.visibility = View.INVISIBLE
                idBooking.visibility = View.INVISIBLE
                idRating.visibility = View.VISIBLE

                rvPosts.visibility = View.GONE
                rvEvents.visibility = View.GONE
                llRatings.visibility = View.VISIBLE
            }

            llClubMoreInfo.setOnClickListener {
                showCustomDialogBox(clubMotto, clubInfo)
            }

            rvPosts.layoutManager = verticalLinearLayoutManager
            rvPosts.adapter = postsAdapter
            rvPosts.addItemDecoration(
                CustomDividerItemDecoration(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.divider_1dp
                    )!!
                )
            )

            rvEvents.adapter = eventsAdapter

            rvRatings.adapter = ratingsAdapter
        }

    }

    private fun showCustomDialogBox(clubMotto: String, clubInfo: String) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_detailed_info)
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)

        val btnClose: ImageButton = dialog.findViewById(R.id.btnClose)
        val tvClubMotto: TextView = dialog.findViewById(R.id.tvClubMotto)
        val tvClubInfo: TextView = dialog.findViewById(R.id.tvClubInfo)

        tvClubInfo.text = clubInfo
        tvClubMotto.text = clubMotto

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showCustomDialogBox() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_log_out)
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)

        val tvTitle: TextView = dialog.findViewById(R.id.tvTitle)
        val btnDismiss: TextView = dialog.findViewById(R.id.btnNo)
        val btnLogout: TextView = dialog.findViewById(R.id.btnYes)

        tvTitle.text = "Your request has been sended, please wait."
        btnDismiss.text = "OK"

        btnLogout.visibility = View.GONE

        btnDismiss.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}