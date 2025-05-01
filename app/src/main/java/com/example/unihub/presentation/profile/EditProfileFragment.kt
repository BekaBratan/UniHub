package com.example.unihub.presentation.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

            tvMainName.text = sharedProvider.getNameFromProfile() + " " + sharedProvider.getSurnameFromProfile()
            tvMainEmail.text = sharedProvider.getEmailFromProfile()

            etName.text = Editable.Factory.getInstance().newEditable(sharedProvider.getNameFromProfile())
            etSurname.text = Editable.Factory.getInstance().newEditable(sharedProvider.getSurnameFromProfile())
            etPhone.text = Editable.Factory.getInstance().newEditable(sharedProvider.getPhoneFromProfile())
            etGender.text = Editable.Factory.getInstance().newEditable(sharedProvider.getGenderFromProfile())
            etBirthDate.text = Editable.Factory.getInstance().newEditable(sharedProvider.getBirthDateFromProfile())
        }
    }
}