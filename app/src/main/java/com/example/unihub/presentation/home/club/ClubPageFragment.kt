package com.example.unihub.presentation.home.club

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unihub.R
import com.example.unihub.data.model.post.Club
import com.example.unihub.data.model.post.PostsResponseItem
import com.example.unihub.data.model.post.User
import com.example.unihub.data.model.poster.PostersByClubResponseItem
import com.example.unihub.databinding.FragmentClubPageBinding
import com.example.unihub.presentation.club.ClubViewModel
import com.example.unihub.presentation.home.posts.PostsAdapter
import com.example.unihub.presentation.home.posts.PostsViewModel
import com.example.unihub.utils.CustomDividerItemDecoration
import com.example.unihub.utils.RcViewItemClickIdCallback
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import java.time.ZonedDateTime

class ClubPageFragment : Fragment() {

    private lateinit var binding: FragmentClubPageBinding
    private val clubViewModel: ClubViewModel by viewModels()
    private val postsViewModel: PostsViewModel by viewModels()
    private val args: ClubPageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClubPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(false)
        val sharedProvider = SharedProvider(requireContext())

        var clubMotto = ""
        var clubInfo = ""
        var headName = ""

        var followed = false

        postsViewModel.getPostsList(sharedProvider.getToken())
        clubViewModel.getClubDetails(sharedProvider.getToken(), args.id)
        clubViewModel.getPosterByClub(sharedProvider.getToken(), args.id)

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

        clubViewModel.clubResponse.observe(viewLifecycleOwner) {
            binding.run {
                tvClubName.text = it.name
                tvClubTime.text = ZonedDateTime.parse(it.createdAt).year.toString()

                clubMotto = it.goal
                clubInfo = it.description
                headName = it.head.name + " " + it.head.surname
            }
        }

        val postsAdapter = PostsAdapter()

        postsViewModel.getPostListResponse.observe(viewLifecycleOwner) { postList->
            postsAdapter.submitList(postList)
        }

        postsAdapter.setOnLikeClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int?) {
                    postsViewModel.likePost(sharedProvider.getToken(), id!!)
                }
            }
        )

        val eventsAdapter = EventCardsAdapter()

//        eventsAdapter.submitList(
//            listOf(
//                PostersByClubResponseItem(
//                    description = "A seminar on AI and its impact on future careers.",
//                    eventDate = "2025-05-15",
//                    eventTitle = "Future of AI in Academia",
//                    id = 1,
//                    image = "https://university.edu/images/events/ai_seminar.png",
//                    location = "Main Auditorium, SDU University",
//                    price = 1000,
//                    seats = 100,
//                    seatsLeft = 42,
//                    time = "14:00"
//                ),
//                PostersByClubResponseItem(
//                    description = "Annual cultural festival featuring music, dance, food stalls, and student performances.",
//                    eventDate = "2025-06-01",
//                    eventTitle = "SDU Cultural Fest 2025",
//                    id = 2,
//                    image = "https://university.edu/images/events/cultural_fest.png",
//                    location = "Campus Courtyard, SDU University",
//                    price = 2500,
//                    seats = 300,
//                    seatsLeft = 180,
//                    time = "17:30"
//                )
//            )
//        )

        clubViewModel.postersByClubResponse.observe(viewLifecycleOwner) {
            eventsAdapter.submitList(it)
        }

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
                if (followed) {
                    btnFollow.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.blue_800)
                    btnFollow.text = getString(R.string.follow)
                    btnFollow.setTextColor(ContextCompat.getColor(requireContext(), R.color.white_1000))
                } else {
                    btnFollow.backgroundTintList = null
                    btnFollow.text = getString(R.string.unfollow)
                    btnFollow.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_800))
                }
                followed = !followed
            }

            tvAll.setOnClickListener {
                idAll.visibility = View.VISIBLE
                idBooking.visibility = View.INVISIBLE
//                idRating.visibility = View.INVISIBLE

                rvPosts.visibility = View.VISIBLE
                rvEvents.visibility = View.GONE
//                llRatings.visibility = View.GONE
            }

            tvBooking.setOnClickListener {
                idAll.visibility = View.INVISIBLE
                idBooking.visibility = View.VISIBLE
//                idRating.visibility = View.INVISIBLE

                rvPosts.visibility = View.GONE
                rvEvents.visibility = View.VISIBLE
//                llRatings.visibility = View.GONE
            }

            tvRating.setOnClickListener {
                idAll.visibility = View.INVISIBLE
                idBooking.visibility = View.INVISIBLE
//                idRating.visibility = View.VISIBLE

                rvPosts.visibility = View.GONE
                rvEvents.visibility = View.GONE
//                llRatings.visibility = View.VISIBLE
            }

            llClubMoreInfo.setOnClickListener {
                showCustomDialogBox(clubMotto, clubInfo, headName)
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

    private fun showCustomDialogBox(clubMotto: String, clubInfo: String, headName: String) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_detailed_info)
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)

        val btnClose: ImageButton = dialog.findViewById(R.id.btnClose)
        val tvClubMotto: TextView = dialog.findViewById(R.id.tvClubMotto)
        val tvClubInfo: TextView = dialog.findViewById(R.id.tvClubInfo)
        val tvHeadName: TextView = dialog.findViewById(R.id.tvHeadName)

        tvClubInfo.text = clubInfo
        tvClubMotto.text = clubMotto
        tvHeadName.text = headName

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    @SuppressLint("SetTextI18n")
    private fun showCustomDialogBox() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_log_out)
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)

        val tvTitle: TextView = dialog.findViewById(R.id.tvTitle)
        val btnDismiss: TextView = dialog.findViewById(R.id.btnNo)
        val btnLogout: TextView = dialog.findViewById(R.id.btnYes)

        tvTitle.text = getString(R.string.your_request_has_been_sended_please_wait)
        btnDismiss.text = getString(R.string.ok)

        btnLogout.visibility = View.GONE

        btnDismiss.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}