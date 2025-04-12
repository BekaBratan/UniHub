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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unihub.R
import com.example.unihub.data.model.Posts
import com.example.unihub.databinding.FragmentClubPageBinding
import com.example.unihub.presentation.home.posts.PostsAdapter
import com.example.unihub.utils.CustomDividerItemDecoration
import com.example.unihub.utils.RcViewItemClickIdCallback
import com.example.unihub.utils.RcViewItemClickIdStringCallback
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

        clubViewModel.getClubById(args.id)

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

                }
            }
        )

        val eventsAdapter = EventCardsAdapter()
        val ratingsAdapter = ClubRatingsAdapter()

        val verticalLinearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.run {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
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
                clubViewModel.getEvents()
                idAll.visibility = View.INVISIBLE
                idBooking.visibility = View.VISIBLE
                idRating.visibility = View.INVISIBLE

                rvPosts.visibility = View.GONE
                rvEvents.visibility = View.VISIBLE
                llRatings.visibility = View.GONE
            }

            tvRating.setOnClickListener {
                clubViewModel.getClubs()
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

            rvEvents.layoutManager = verticalLinearLayoutManager
            rvEvents.adapter = eventsAdapter

            rvRatings.layoutManager = verticalLinearLayoutManager
            rvRatings.adapter = ratingsAdapter
        }

        clubViewModel.clubResponse.observe(viewLifecycleOwner) {
            binding.tvClubName.text = it.name
            clubMotto = it.name
            clubInfo = it.description
        }

        clubViewModel.eventsResponse.observe(viewLifecycleOwner) {
            eventsAdapter.submitList(it)
        }

        clubViewModel.clubsResponse.observe(viewLifecycleOwner) {
            ratingsAdapter.submitList(it.clubs)
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
}