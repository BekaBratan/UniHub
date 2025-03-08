package com.example.unihub.presentation.authentication.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.unihub.databinding.FragmentSignInBinding
import com.example.unihub.utils.provideNavigationHost

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)

        binding.run {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnCreate.setOnClickListener {
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToVerifyFragment2())
            }

            btnAdmin.setOnClickListener {
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignInAdminFragment())
            }
        }
    }

}