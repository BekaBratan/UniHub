package com.example.unihub.presentation.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.unihub.R
import com.example.unihub.databinding.FragmentRequestsListBinding

class RequestsListFragment : Fragment() {

    private lateinit var binding: FragmentRequestsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequestsListBinding.inflate(inflater, container, false)
        return binding.root
    }

}