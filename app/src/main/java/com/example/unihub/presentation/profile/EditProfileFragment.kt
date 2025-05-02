package com.example.unihub.presentation.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentEditProfileBinding
import com.example.unihub.databinding.FragmentEditUserBinding
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost

class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)
        val sharedProvider = SharedProvider(requireContext())

        binding.run {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            val genders = listOf("Male", "Female")
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, genders)
            etGender.setAdapter(adapter)
            etGender.setText("Female", false)
            etGender.setOnClickListener {
                etGender.showDropDown()
            }

            tvMainName.text = sharedProvider.getName() + " " + sharedProvider.getSurname()
            tvMainEmail.text = sharedProvider.getEmail()

            etName.setText(sharedProvider.getName())
            etSurname.setText(sharedProvider.getSurname())
            etPhone.setText(sharedProvider.getPhone())
            etGender.setText(sharedProvider.getGender(), false)
            etBirthDate.setText(sharedProvider.getBirthDate())
        }
    }
}