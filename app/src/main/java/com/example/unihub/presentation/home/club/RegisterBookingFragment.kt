package com.example.unihub.presentation.home.club

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentRegisterBookingBinding
import com.example.unihub.utils.provideNavigationHost

class RegisterBookingFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBookingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)

        binding.run {
            btnRegister.setOnClickListener {
                findNavController().navigate(R.id.action_registerBookingFragment_to_calendarFragment)
            }
        }
    }
}