package com.example.unihub.presentation.club

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.data.model.head.CreateEventsListReponse
import com.example.unihub.data.model.head.CreateEventsListReponseItem
import com.example.unihub.data.model.head.CreatePosterRequest
import com.example.unihub.data.model.head.MyEventsResponse
import com.example.unihub.data.model.head.MyEventsResponseItem
import com.example.unihub.databinding.FragmentCreatePosterBinding
import com.example.unihub.utils.SharedProvider
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class CreatePosterFragment : Fragment() {

    private lateinit var binding: FragmentCreatePosterBinding
    private val headViewModel: HeadViewModel by viewModels()
    private var imageBase64: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatePosterBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedProvider = SharedProvider(requireContext())

        var event = MyEventsResponseItem()

        headViewModel.getCreateEventsList(sharedProvider.getToken())

        binding.run {
            btnBack.setOnClickListener {
                findNavController().navigate(CreatePosterFragmentDirections.actionCreatePosterFragmentToCreateNewPostFragment())
            }

            headViewModel.createEventsListResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    event = it.lastOrNull() ?: MyEventsResponseItem()
//                    val (date, time) = extractDateAndTimeSafe(event.eventDate.toString())
                    btnSend.isEnabled = true
                    etDate.text = event.eventDate
                    etTime.setOnClickListener {
                        val calendar: Calendar = Calendar.getInstance()
                        val timePicker = TimePickerDialog(
                            requireContext(),
                            R.style.CustomTimePickerDialogTheme,
                            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                                val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                                binding.etTime.text = selectedTime
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true
                        )
                        timePicker.show()
                    }
                    etEventTitle.setText(event.eventName + event.id)
                    etLocation.setText(event.location)
                    etDescription.setText(event.shortDescription)
                }
            }

//            etTime.setOnClickListener {
//                val calendar: Calendar = Calendar.getInstance()
//                val timePicker = TimePickerDialog(
//                    requireContext(),
//                    R.style.CustomTimePickerDialogTheme,
//                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
//                        val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
//                        binding.etTime.text = selectedTime
//                    },
//                    calendar.get(Calendar.HOUR_OF_DAY),
//                    calendar.get(Calendar.MINUTE),
//                    true
//                )
//                timePicker.show()
//            }
//
//            etDate.setOnClickListener {
//                val calendar: Calendar = Calendar.getInstance()
//                val datePicker = DatePickerDialog(
//                    requireContext(),
//                    R.style.CustomTimePickerDialogTheme,
//                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
//                        val selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
//                        binding.etDate.text = selectedDate
//                    },
//                    calendar.get(Calendar.YEAR),
//                    calendar.get(Calendar.MONTH),
//                    calendar.get(Calendar.DAY_OF_MONTH)
//                )
//                datePicker.show()
//            }

            etMedia.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK).apply {
                    type = "image/*"
                }
                startActivityForResult(intent, REQUEST_CODE_PICK_MEDIA)
            }

            btnSend.setOnClickListener {
                val eventDate = etDate.text.toString()
                val time = etTime.text.toString()
                val media = imageBase64
                val description = etDescription.text.toString()

                headViewModel.createPoster(
                    token = sharedProvider.getToken(),
                    description = description,
                    eventDate = eventDate,
                    eventId = event.id?: 0,
                    eventTitle = event.eventName.toString(),
                    image = media,
                    location = event.location.toString(),
                    price = etPrice.text.toString().toInt(),
                    seats = etSeats.text.toString().toInt(),
                    time = time
                )
            }

            headViewModel.createPosterResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    findNavController().popBackStack()
                }
            }

            headViewModel.errorMessage.observe(viewLifecycleOwner) {
                binding.tvError.text = it.message
                binding.tvError.visibility = View.VISIBLE
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_MEDIA && resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImageUri = data?.data
            if (selectedImageUri != null) {
                val inputStream = requireContext().contentResolver.openInputStream(selectedImageUri)
                val bytes = inputStream?.readBytes()
                inputStream?.close()

                if (bytes != null) {
                    val base64String = Base64.encodeToString(bytes, Base64.NO_WRAP)
                    binding.etMedia.text = "Media selected"
                    imageBase64 = base64String
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun extractDateAndTimeSafe(iso: String?): Pair<String, String> {
        return try {
            val zoned = ZonedDateTime.parse(iso)
            val date = zoned.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            val time = zoned.format(DateTimeFormatter.ofPattern("HH:mm"))
            Pair(date, time)
        } catch (e: Exception) {
            Pair("Invalid date", "Invalid time")
        }
    }

    companion object {
        private const val REQUEST_CODE_PICK_MEDIA = 1001
    }
}