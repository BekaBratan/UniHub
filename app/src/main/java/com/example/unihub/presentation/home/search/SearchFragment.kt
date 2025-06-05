package com.example.unihub.presentation.home.search

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unihub.data.model.club.ClubsResponseItem
import com.example.unihub.databinding.FragmentSearchBinding
import com.example.unihub.presentation.home.posts.ClubsViewModel
import com.example.unihub.presentation.home.posts.HomeFragmentDirections
import com.example.unihub.utils.RcViewItemClickIdCallback
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import kotlin.getValue

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val clubsViewModel: ClubsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedProvider = SharedProvider(requireContext())
        provideNavigationHost()?.hideBottomNavigationBar(true)

        val clubsAdapter = RecInlineAdapter()
        var filteredClubList = mutableListOf<ClubsResponseItem>()

        clubsViewModel.getClubList(sharedProvider.getToken())

        clubsViewModel.getClubListResponse.observe(viewLifecycleOwner) { clubsList ->
            filteredClubList = clubsList.toMutableList()
            clubsAdapter.submitList(filteredClubList)
        }

        clubsAdapter.setOnFollowClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int?) {
                    findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToClubPageFragment(id?: 1, ""))
                }
            }
        )

        val verticalLinearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.run {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            etSearch.setOnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                    event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {

                    clubsAdapter.submitList(filteredClubList.filter { club ->
                        club.name.contains(etSearch.text.toString().replace("\\s".toRegex(), ""), ignoreCase = true) == true
                    })

                    true
                } else {
                    false
                }
            }

            btnSearch.setOnClickListener {
                clubsAdapter.submitList(filteredClubList.filter { club ->
                    club.name.contains(etSearch.text.toString().replace("\\s".toRegex(), ""), ignoreCase = true) == true
                })
            }

            rvClubs.layoutManager = verticalLinearLayoutManager
            rvClubs.adapter = clubsAdapter
        }
    }

}