package com.example.unihub.presentation.home.club

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.unihub.R
import com.example.unihub.databinding.FragmentBookingBinding
import com.example.unihub.utils.provideNavigationHost
import kotlin.getValue

class BookingFragment : Fragment() {

    private lateinit var binding: FragmentBookingBinding
    private val clubViewModel: ClubViewModel by viewModels()
    private val args: BookingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)
        clubViewModel.getEventById(args.id)

        clubViewModel.eventResponse.observe(viewLifecycleOwner) {
            binding.run {
                tvTitle.text = it.name
                tvLocation.text = it.location
                tvDescription.text = it.description
                var dateTime = it.date.split("T")
                var time = dateTime[1].split(".")[0].split(":")
                tvDate.text = dateTime[0]
                tvTime.text = "${time[0]}:${time[1]}"
                tvPrice.text = "${it.price} KZT/Person"
            }
        }

        binding.run {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnGetTicket.setOnClickListener {
                findNavController().navigate(BookingFragmentDirections.actionBookingFragmentToRegisterBookingFragment(args.id))
            }
        }
    }

}