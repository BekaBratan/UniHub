package com.example.unihub.presentation.authentication.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentLogInBinding

class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            btnBack.setOnClickListener{
                findNavController().navigateUp()
            }

            btnLogin.setOnClickListener{
                findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToHomeFragment())
            }

            tvForgotPassword.setOnClickListener{
                findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToResetFragment())
            }
        }

    }

}