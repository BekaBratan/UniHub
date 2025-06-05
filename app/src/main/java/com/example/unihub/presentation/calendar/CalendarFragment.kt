package com.example.unihub.presentation.calendar

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.data.model.calendar.Event
import com.example.unihub.databinding.FragmentCalendarBinding
import com.example.unihub.presentation.profile.ProfileViewModel
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import kotlin.getValue
import java.time.Duration

class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding
    private val calendarViewModel: CalendarViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedProvider = SharedProvider(requireContext())
        provideNavigationHost()?.hideBottomNavigationBar(false)

        calendarViewModel.getCombinedCalendar(sharedProvider.getToken())

        var calendarItems: List<Event?> = emptyList()

        calendarViewModel.combinedCalendarResponse.observe(viewLifecycleOwner) { response ->
            calendarItems = response.events!!
        }

        val calendar = Calendar.getInstance()

        var yearSelected = calendar.get(Calendar.YEAR);
        var monthSelected = calendar.get(Calendar.MONTH);
        var daySelected = calendar.get(Calendar.DAY_OF_MONTH);
        setMonthAndYear(monthSelected, yearSelected)



        binding.run {
            llYear.setOnClickListener {
                val datePickerDialog = DatePickerDialog(requireContext(), { _: DatePicker, selectedYear: Int, selectedMonth: Int, daySelected: Int ->
                    calendar.set(Calendar.YEAR, selectedYear)
                    calendar.set(Calendar.DAY_OF_MONTH, daySelected)
                    calendar.set(Calendar.MONTH, selectedMonth)
                    viewCalendar.date = calendar.timeInMillis

                    setMonthAndYear(selectedMonth, selectedYear)
                }, yearSelected, monthSelected, daySelected)
                datePickerDialog.show()
            }

            viewCalendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
                clearAllEventCards()

                var date = ""
                var day = ""
                if (dayOfMonth>9)
                    day = "$dayOfMonth"
                else
                    day = "0$dayOfMonth"

                if (month+1>9)
                    date = "$day/${month+1}/$year"
                else
                    date = "$day/0${month+1}/$year"
                Toast.makeText(requireContext(), date, Toast.LENGTH_SHORT).show()
                val newCalendarItems = calendarItems.filter { it?.date == date }
                newCalendarItems.forEach {
                    addEventCard(
                        time = it?.time ?: it?.startTime ?: getString(R.string.no_time),
                        id = it?.id.toString(),
                        title = it?.title.toString(),
                        duration = it?.endTime?.let { endTime ->
                            val startTime = it.startTime ?: "00:00"
                            val durationHours = calculateDuration(startTime, endTime)
                            getString(R.string.hours, durationHours)
                        } ?: getString(R.string._1_hour)
                    )
                }
            }

            btnPlus.setOnClickListener {
                findNavController().navigate(CalendarFragmentDirections.actionCalendarFragmentToAddEventFragment())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun calculateDuration(start: String, end: String): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val startTime = LocalTime.parse(start, formatter)
        val endTime = LocalTime.parse(end, formatter)

        val duration = Duration.between(startTime, endTime)
        val hours = duration.toHours()
        val minutes = duration.toMinutes() % 60

        return "${hours}h ${minutes}m"
    }

    private fun setMonthAndYear(month: Int, year: Int) {
        binding.tvYear.text = "${getMonthName(month)} $year"
    }

    private fun getMonthName(month: Int): String {
        return when (month) {
            0 -> getString(R.string.january)
            1 -> getString(R.string.february)
            2 -> getString(R.string.march)
            3 -> getString(R.string.april)
            4 -> getString(R.string.may)
            5 -> getString(R.string.june)
            6 -> getString(R.string.july)
            7 -> getString(R.string.august)
            8 -> getString(R.string.september)
            9 -> getString(R.string.october)
            10 -> getString(R.string.november)
            11 -> getString(R.string.december)
            else -> {
                ""
            }
        }
    }


    fun addEventCard(time: String, id: String, title: String = getString(R.string.sample_event), duration: String = getString(R.string._1_hour)) {
        val eventCardLayout = LayoutInflater.from(requireContext()).inflate(R.layout.item_calendar_card, binding.clEventsTime, false)

        val icEvent = eventCardLayout.findViewById<ImageButton>(R.id.icEvent)
        val tvEventName = eventCardLayout.findViewById<TextView>(R.id.tvEventName)
        val tvEventTime = eventCardLayout.findViewById<TextView>(R.id.tvEventTime)
        val tvEventDuration = eventCardLayout.findViewById<TextView>(R.id.tvEventDuration)

        tvEventName.text = title
        tvEventTime.text = time
        tvEventDuration.text = duration

        eventCardLayout.setOnClickListener {
            findNavController().navigate(CalendarFragmentDirections.actionCalendarFragmentToEventsFragment(id))
        }

        val hour = time.substringBefore(":").toIntOrNull() ?: return

        when (hour) {
            8 -> binding.llTime8am.addView(eventCardLayout)
            9 -> binding.llTime9am.addView(eventCardLayout)
            10 -> binding.llTime10am.addView(eventCardLayout)
            11 -> binding.llTime11am.addView(eventCardLayout)
            12 -> binding.llTime12am.addView(eventCardLayout)
            13 -> binding.llTime1pm.addView(eventCardLayout)
            14 -> binding.llTime2pm.addView(eventCardLayout)
            15 -> binding.llTime3pm.addView(eventCardLayout)
            16 -> binding.llTime4pm.addView(eventCardLayout)
            17 -> binding.llTime5pm.addView(eventCardLayout)
            18 -> binding.llTime6pm.addView(eventCardLayout)
            19 -> binding.llTime7pm.addView(eventCardLayout)
            20 -> binding.llTime8pm.addView(eventCardLayout)
            else -> return
        }
    }

    fun clearAllEventCards() {
        binding.llTime8am.removeAllViews()
        binding.llTime9am.removeAllViews()
        binding.llTime10am.removeAllViews()
        binding.llTime11am.removeAllViews()
        binding.llTime12am.removeAllViews()
        binding.llTime1pm.removeAllViews()
        binding.llTime2pm.removeAllViews()
        binding.llTime3pm.removeAllViews()
        binding.llTime4pm.removeAllViews()
        binding.llTime5pm.removeAllViews()
        binding.llTime6pm.removeAllViews()
        binding.llTime7pm.removeAllViews()
        binding.llTime8pm.removeAllViews()
    }
}