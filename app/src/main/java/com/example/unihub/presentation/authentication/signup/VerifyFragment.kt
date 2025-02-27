package com.example.unihub.presentation.authentication.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.unihub.databinding.FragmentVerifyBinding

class VerifyFragment : Fragment() {

    private lateinit var binding: FragmentVerifyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            btnVerify.setOnClickListener {
                findNavController().navigate(VerifyFragmentDirections.actionVerifyFragment2ToSuccessFragment2())
            }

            tvDifferentEmail.setOnClickListener {
                findNavController().navigate(VerifyFragmentDirections.actionVerifyFragmentToSignInFragment())
            }
        }
    }
}