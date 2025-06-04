package com.example.unihub.presentation.calendar

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
import com.example.unihub.data.model.calendar.Event
import com.example.unihub.data.model.calendar.PersonalCalendarResponseItem
import com.example.unihub.databinding.FragmentEventsBinding
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import kotlin.getValue

class EventsFragment : Fragment() {

    private lateinit var binding: FragmentEventsBinding
    private val args: EventsFragmentArgs by navArgs()
    private val calendarViewModel: CalendarViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedProvider = SharedProvider(requireContext())
        provideNavigationHost()?.hideBottomNavigationBar(false)

        calendarViewModel.getCombinedCalendar(sharedProvider.getToken())
        calendarViewModel.getPersonalCalendar(sharedProvider.getToken())

        var calendarItems: List<Event?> = emptyList()
        var personalItems: List<PersonalCalendarResponseItem> = emptyList()

        calendarViewModel.combinedCalendarResponse.observe(viewLifecycleOwner) { response ->
            calendarItems = response.events!!
            calendarViewModel.personalCalendarResponse.observe(viewLifecycleOwner) { response ->
                personalItems = response

                val event = calendarItems.find { it?.id == args.id }

                binding.run {
                    btnBack.setOnClickListener {
                        findNavController().popBackStack()
                    }

                    tvEventName.text = event?.title
                    tvEventDate.text = event?.date

                    if (event?.source == "booking") {
                        ivEventImage.setBackgroundResource(R.drawable.ic_ticket)
                        tvEventDescription.text = "${event.type} for ${event.persons} persons \n" +
                                "Location: ${event.location}"
                        tvEventTime.text = event.time
                        llSuggestion.visibility = View.GONE
                        tvSuggestion.visibility = View.GONE
                        btnDelete.visibility = View.INVISIBLE
                    } else {
                        val personalEvent = personalItems.find { it.date == event?.date && it.startTime == event?.startTime }


                        tvEventDescription.text = "${args.id}  event"
                        tvEventTime.text = "${event?.startTime} - ${event?.endTime}"
                        tvSuggestion.text = event?.suggestions ?: "${personalItems}"
                        llSuggestion.visibility = View.VISIBLE
                        tvSuggestion.visibility = View.VISIBLE
                        btnDelete.setOnClickListener {
                            calendarViewModel.deleteEvent(sharedProvider.getToken(), personalEvent?.id!!)
                            findNavController().popBackStack()
                        }
                    }
                }
            }
        }
    }
}