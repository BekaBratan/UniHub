package com.example.unihub.presentation.notifications

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Base64
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
import com.example.unihub.databinding.FragmentBookingDetailsBinding
import com.example.unihub.databinding.FragmentHeadTicketBinding
import com.example.unihub.presentation.home.club.BookingDetailsFragmentArgs
import com.example.unihub.presentation.requests.CreateEventRequestFragmentDirections
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.getValue

class HeadTicketFragment : Fragment() {

    private lateinit var binding: FragmentHeadTicketBinding
    private val notificationsViewModel: NotificationsViewModel by viewModels()
    private val args: HeadTicketFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHeadTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)
        val sharedProvider = SharedProvider(requireContext())

        notificationsViewModel.getPendingTickets(sharedProvider.getToken())

        notificationsViewModel.getPendingTicketsResponse.observe(viewLifecycleOwner) { response ->
            val ticket = response.find { it.id == args.id }
            if (ticket != null) {
                binding.run {
                    tvSender.text = "Sender: " + sharedProvider.getName() + " " + sharedProvider.getSurname()
                    tvCreateDate.text = "Date of create: " + formatDatePlusDays(ticket.createdAt.toString())
                    tvPersonNumber.text = "Number of Person: " + ticket.numberOfPersons.toString()

                    if (ticket.paymentProof!!.isNotEmpty()) {
                        val base64String = ticket.paymentProof
                        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
                        val bitmap =
                            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                        ivTranscript.setImageBitmap(bitmap)
                    }

                    etComment.setText(ticket.poster?.eventTitle + " " + ticket.poster?.eventDate)
                }
            }
        }

        binding.run {
            val items = listOf("Approved", "Cancelled")
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, items)
            etStatus.setAdapter(adapter)
//            acTemplate.setText("Choose template", false)
            etStatus.setOnClickListener {
                etStatus.showDropDown()
            }

            btnSend.setOnClickListener {
                val status = etStatus.text.toString()
                if (status == "Approved") {
                    notificationsViewModel.approveTicketRequest(sharedProvider.getToken(), args.id)
                } else {
                    notificationsViewModel.rejectTicketRequest(sharedProvider.getToken(), args.id)
                }
                findNavController().popBackStack()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDatePlusDays(isoDate: String, daysToAdd: Long = 7): String {
        val zonedDateTime = ZonedDateTime.parse(isoDate)
            .plusDays(daysToAdd)
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.getDefault())
        return zonedDateTime.format(formatter)
    }
}