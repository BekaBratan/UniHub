package com.example.unihub.presentation.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.unihub.R
import com.example.unihub.databinding.FragmentEditUserBinding
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import kotlin.getValue

class EditUserFragment : Fragment() {

    private lateinit var binding: FragmentEditUserBinding
    private val adminViewModel: AdminViewModel by viewModels()
    private val args: EditUserFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedProvider = SharedProvider(requireContext())
        provideNavigationHost()?.hideBottomNavigationBar(true)

        adminViewModel.getUserProfile(sharedProvider.getToken(), args.userId)

        binding.run {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnSave.setOnClickListener {
                val role = when (etStatus.text.toString()) {
                    getString(R.string.head_of_club) -> "head_admin"
                    getString(R.string.admin) -> "super_admin"
                    getString(R.string.student) -> "student"
                    else -> "user"
                }
                adminViewModel.updateUserRole(sharedProvider.getToken(), args.userId, role)
            }

            adminViewModel.getUserProfile.observe(viewLifecycleOwner) { user ->
                tvUserName.setText(user.name + " " + user.surname)
                etClubName.setText(user.clubInfo?.name)
                etPhone.setText(user.phone.toString())
                etBirthDate.setText(user.birthDate.toString())
                etActivity.setText(user.updatedAt)

                if (user.role?.contains("head") == true) {
                    etStatus.setText(getString(R.string.head_of_club))
                } else if (user.role?.contains("super") == true) {
                    etStatus.setText(getString(R.string.admin))
                } else if (user.role?.contains("student") == true) {
                    etStatus.setText(getString(R.string.student))
                } else {
                    etStatus.setText(getString(R.string.user))
                }

                val genders = listOf(getString(R.string.head_of_club), getString(R.string.admin), getString(R.string.student), getString(R.string.user))
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, genders)
                etStatus.setAdapter(adapter)
                etStatus.setOnClickListener {
                    etStatus.showDropDown()
                }
            }

            adminViewModel.messageResponse.observe(viewLifecycleOwner) {
                findNavController().popBackStack()
            }
        }
    }

}