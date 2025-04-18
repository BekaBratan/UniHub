package com.example.unihub.presentation.profile

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentProfileBinding
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)

        binding.run {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnMain.setOnClickListener {
                findNavController().popBackStack()
            }

            llMyAccount.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
            }

            llChangePassword.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToChangePasswordFragment())
            }

            llLogOut.setOnClickListener {
                showCustomDialogBox()
            }
        }
    }

    private fun showCustomDialogBox() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_log_out)
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)

        val btnDismiss: TextView = dialog.findViewById(R.id.btnNo)
        val btnLogout: TextView = dialog.findViewById(R.id.btnYes)

        btnDismiss.setOnClickListener {
            dialog.dismiss()
        }

        btnLogout.setOnClickListener {
            dialog.dismiss()
            SharedProvider(requireContext()).clearShared()
            findNavController().navigate(R.id.welcomeFragment)
        }

        dialog.show()
    }
}