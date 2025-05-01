package com.example.unihub.presentation.club

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.unihub.R
import com.example.unihub.databinding.FragmentCreateEventBinding

class CreateEventFragment : Fragment() {

    private lateinit var binding: FragmentCreateEventBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = listOf("Request for event", "Create new post", "Create booking poster for event")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, items)
        binding.acTemplate.setAdapter(adapter)
        binding.acTemplate.setText("Choose template", false)
        binding.acTemplate.setOnClickListener {
            binding.acTemplate.showDropDown()
        }
    }
}