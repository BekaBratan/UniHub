package com.example.unihub.presentation.club

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentCreateEventBinding
import com.example.unihub.utils.provideNavigationHost

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
        provideNavigationHost()?.hideBottomNavigationBar(false)

        binding.run {
            val items =
                listOf("Request for event", "Create new post", "Create booking poster for event")
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, items)
            acTemplate.setAdapter(adapter)
            acTemplate.setText("Choose template", false)
            acTemplate.setOnClickListener {
                acTemplate.showDropDown()
            }
        }
    }
}