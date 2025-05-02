package com.example.unihub.presentation.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentRequestsListBinding
import com.example.unihub.presentation.profile.CreateRequestFragmentDirections
import com.example.unihub.presentation.requests.RequestItemAdapter
import com.example.unihub.utils.RcViewItemClickIdCallback
import com.example.unihub.utils.RcViewItemClickIdStringCallback
import com.example.unihub.utils.provideNavigationHost

class RequestsListFragment : Fragment() {

    private lateinit var binding: FragmentRequestsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequestsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)

        val adapterClub = RequestItemAdapter()
        val adapterEvent = RequestItemAdapter()

        adapterClub.submitList(listOf("Club1", "Club2", "Club3", "Club4", "Club5", "Club6", "Club7", "Club8", "Club9", "Club10"))
        adapterEvent.submitList(listOf("Event1", "Event2", "Event3", "Event4", "Event5", "Event6", "Event7", "Event8", "Event9", "Event10"))

        adapterClub.setOnItemClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int) {
                    findNavController().navigate(RequestsListFragmentDirections.actionRequestsListFragmentToCreateClubRequestFragment(id, false))
                }
            }
        )

        adapterEvent.setOnItemClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int) {
                    findNavController().navigate(RequestsListFragmentDirections.actionRequestsListFragmentToCreateEventRequestFragment(id, false))
                }
            }
        )

        binding.run {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            rvEventRequestsList.adapter = adapterEvent
            rvClubRequestsList.adapter = adapterClub

            tvClubRequests.setOnClickListener {
                idClubRequests.visibility = View.VISIBLE
                idEventRequests.visibility = View.INVISIBLE

                rvEventRequestsList.visibility = View.GONE
                rvClubRequestsList.visibility = View.VISIBLE
            }

            tvEventRequests.setOnClickListener {
                idClubRequests.visibility = View.INVISIBLE
                idEventRequests.visibility = View.VISIBLE

                rvEventRequestsList.visibility = View.VISIBLE
                rvClubRequestsList.visibility = View.GONE
            }
        }
    }

}