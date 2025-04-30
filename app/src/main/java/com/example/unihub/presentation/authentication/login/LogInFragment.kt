package com.example.unihub.presentation.authentication.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentLogInBinding
import com.example.unihub.presentation.authentication.AuthViewModel
import com.example.unihub.presentation.home.club.ClubViewModel
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import kotlin.getValue

class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)
        val sharedProvider = SharedProvider(requireContext())

        binding.run {
            btnBack.setOnClickListener{
                findNavController().popBackStack()
            }

            btnLogin.setOnClickListener{
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()
                Log.d("Login", "Email: $email, Password: $password")
                authViewModel.login(email = email, password = password)
                findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToHomeFragment())
            }

            etEmail.addTextChangedListener {
                tvError.visibility = View.INVISIBLE
            }

            etPassword.addTextChangedListener {
                tvError.visibility = View.INVISIBLE
            }

            authViewModel.loginResponse.observe(viewLifecycleOwner) {
                sharedProvider.saveToken(it.token)
                findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToHomeFragment())
            }

            authViewModel.errorMessage.observe(viewLifecycleOwner) {
                tvError.text = it.message
                tvError.visibility = View.VISIBLE
            }

            tvForgotPassword.setOnClickListener{
                findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToResetFragment())
            }
        }

    }

}