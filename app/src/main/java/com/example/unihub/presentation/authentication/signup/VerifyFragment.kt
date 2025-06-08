package com.example.unihub.presentation.authentication.signup

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentVerifyBinding
import com.example.unihub.presentation.authentication.AuthViewModel
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import kotlin.getValue

class VerifyFragment : Fragment() {

    private lateinit var binding: FragmentVerifyBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)
        val sharedProvider = SharedProvider(requireContext())

        binding.run {
            btnBack.setOnClickListener {
                findNavController().navigate(VerifyFragmentDirections.actionVerifyFragmentToSignInFragment())
            }

            tvTitle.text = getString(R.string.we_just_sent_6_digit_code_to) + " ${sharedProvider.getEmail()} " + getString(R.string.enter_it_bellow)

            btnVerify.setOnClickListener {
                val email = sharedProvider.getEmail()
                val code = etCode.text.toString()
                authViewModel.verifyEmail(email, code)
                btnVerify.visibility = View.GONE
                btnLoad.visibility = View.VISIBLE
                Log.d("Verify", "Email: $email, Code: $code")
            }

            etCode.addTextChangedListener {
                tvError.visibility = View.INVISIBLE
            }

            authViewModel.verifyEmailResponse.observe(viewLifecycleOwner) {
                Log.d("Login Response", it.toString())
                sharedProvider.saveToken(it.token.toString())
                sharedProvider.saveEmail(it.user?.email.toString())
                sharedProvider.saveName(it.user?.name.toString())
                sharedProvider.saveSurname(it.user?.surname.toString())
                sharedProvider.saveRole(it.user?.role.toString())
                sharedProvider.saveID(it.user?.id ?: 0)
                btnVerify.visibility = View.VISIBLE
                btnLoad.visibility = View.GONE
                findNavController().navigate(VerifyFragmentDirections.actionVerifyFragment2ToSuccessFragment2())
            }

            authViewModel.errorMessage.observe(viewLifecycleOwner) {
                btnVerify.visibility = View.VISIBLE
                btnLoad.visibility = View.GONE
                tvError.text = it.message
                tvError.visibility = View.VISIBLE
            }

            tvDifferentEmail.setOnClickListener {
                findNavController().navigate(VerifyFragmentDirections.actionVerifyFragmentToSignInFragment())
            }
        }
    }
}