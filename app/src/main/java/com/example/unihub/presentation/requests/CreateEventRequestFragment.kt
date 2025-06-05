package com.example.unihub.presentation.requests

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.unihub.R
import com.example.unihub.databinding.FragmentCreateEventRequestBinding
import com.example.unihub.presentation.profile.RequestViewModel
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class CreateEventRequestFragment : Fragment() {

    private lateinit var binding: FragmentCreateEventRequestBinding
    private val viewModel: RequestViewModel by viewModels()
    private val args: CreateEventRequestFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateEventRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)
        val sharedProvider = SharedProvider(requireContext())

        viewModel.getEventByID(sharedProvider.getToken(), args.id)

        binding.run {
            viewModel.getEventsDetailResponse.observe(viewLifecycleOwner) { requestDetails->
                if (requestDetails != null) {
                    tvSender.text = getString(R.string.sender) + requestDetails.clubHead
                    tvCreateDate.text = getString(R.string.date_of_create) + formatIsoDate(requestDetails.createdAt.toString())
                    tvComment.text = getString(R.string.comment) + requestDetails.comment
                    etEventName.setText(requestDetails.eventName)
                    etGoal.setText(requestDetails.goal)
                    etDate.setText(requestDetails.eventDate)
                    etShedule.setText(requestDetails.schedule)
                    etSponsorship.setText(requestDetails.sponsorship)
                    etDescription.setText(requestDetails.shortDescription)
                    etOrganizers.setText(requestDetails.organizers)
                    etLocation.setText(requestDetails.location)
                    etHead.setText(requestDetails.clubHead)
                    etPhone.setText(requestDetails.phone)
                    etComment.setText(requestDetails.comment)
                }
            }

            val items = listOf(getString(R.string.approved), getString(R.string.cancelled))
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, items)
            etStatus.setAdapter(adapter)
//            acTemplate.setText("Choose template", false)
            etStatus.setOnClickListener {
                etStatus.showDropDown()
            }

            if (args.fromAdmin) {
                tvComment.visibility = View.GONE
                etStatus.visibility = View.VISIBLE
            } else {
                tvComment.visibility = View.VISIBLE
                etStatus.visibility = View.GONE
            }

            btnBack.setOnClickListener{
                findNavController().popBackStack()
            }

            btnSend.setOnClickListener {
                val status = etStatus.text.toString()
                if (status == getString(R.string.approved)) {
                    viewModel.approveEventRequest(sharedProvider.getToken(), args.id)
                } else {
                    viewModel.rejectEventRequest(sharedProvider.getToken(), args.id)
                }
                findNavController().navigate(CreateEventRequestFragmentDirections.actionCreateEventRequestFragmentToRequestConfirmSuccessFragment())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatIsoDate(isoString: String): String {
        val zonedDateTime = ZonedDateTime.parse(isoString)
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.getDefault())
        return zonedDateTime.format(formatter)
    }
}