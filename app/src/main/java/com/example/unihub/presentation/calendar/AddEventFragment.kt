package com.example.unihub.presentation.calendar

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.data.model.calendar.CreatePersonalEvent
import com.example.unihub.databinding.FragmentAddEventBinding
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import java.util.Calendar
import kotlin.getValue

class AddEventFragment : Fragment() {

    private lateinit var binding: FragmentAddEventBinding
    private val calendarViewModel: CalendarViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForColorStateLists", "ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedProvider = SharedProvider(requireContext())
        provideNavigationHost()?.hideBottomNavigationBar(true)

        binding.run {
            btnCreate.setOnClickListener {
                val event = CreatePersonalEvent(
                    date = etDate.text.toString(),
                    endTime = etTimeEnd.text.toString(),
                    eventName = etEventName.text.toString(),
                    remindMe = swRemindMe.isChecked,
                    startTime = etTimeStart.text.toString(),
                    suggestions = etSuggestions.text.toString()
                )
                calendarViewModel.createPersonalEvent(sharedProvider.getToken(), event)
            }

            etTimeStart.setOnClickListener {
                val calendar: Calendar = Calendar.getInstance()
                val timePicker = TimePickerDialog(
                    requireContext(),
                    R.style.CustomTimePickerDialogTheme,
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                        etTimeStart.text = selectedTime
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                )
                timePicker.show()
            }

            etTimeEnd.setOnClickListener {
                val calendar: Calendar = Calendar.getInstance()
                val timePicker = TimePickerDialog(
                    requireContext(),
                    R.style.CustomTimePickerDialogTheme,
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                        etTimeEnd.text = selectedTime
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                )
                timePicker.show()
            }

            etDate.setOnClickListener {
                val calendar: Calendar = Calendar.getInstance()
                val datePicker = DatePickerDialog(
                    requireContext(),
                    R.style.CustomTimePickerDialogTheme,
                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        val selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                        binding.etDate.text = selectedDate
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                datePicker.show()
            }

            swRemindMe.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked)
                    swRemindMe.backgroundTintList = resources.getColorStateList(R.color.blue_800)
                else
                    swRemindMe.backgroundTintList = resources.getColorStateList(R.color.grey_200)
            }
        }

        calendarViewModel.messageResponse.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }
}