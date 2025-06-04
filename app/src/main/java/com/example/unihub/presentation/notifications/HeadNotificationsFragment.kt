package com.example.unihub.presentation.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unihub.R
import com.example.unihub.databinding.FragmentActivitiesBinding
import com.example.unihub.databinding.FragmentHeadNotificationsBinding
import com.example.unihub.utils.RcViewItemClickIdCallback
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import kotlin.getValue

class HeadNotificationsFragment : Fragment() {
    private lateinit var binding: FragmentHeadNotificationsBinding
    private val notificationsViewModel: NotificationsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHeadNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(false)
        val sharedProvider = SharedProvider(requireContext())

        notificationsViewModel.getPendingTickets(sharedProvider.getToken())

        val notificationsAdapter = HeadNotificationsItemAdapter()

        notificationsViewModel.getPendingTicketsResponse.observe(viewLifecycleOwner) {
            notificationsAdapter.submitList(it)
        }

        notificationsAdapter.setOnItemClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int?) {
                    findNavController().navigate(HeadNotificationsFragmentDirections.actionHeadNotificationsFragmentToHeadTicketFragment(id!!))
                }
            }
        )

        binding.run {
            rvNotifications.adapter = notificationsAdapter
            rvNotifications.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}