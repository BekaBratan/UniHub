package com.example.unihub.presentation.profile

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentCreateRequestBinding
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost

class CreateRequestFragment : Fragment() {

    private lateinit var binding: FragmentCreateRequestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)

        binding.run {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            tvRequests.setOnClickListener {
                llRequests.visibility = View.VISIBLE
                rvMyRequests.visibility = View.GONE
                idRequests.visibility = View.VISIBLE
                idMyRequests.visibility = View.INVISIBLE
            }

            tvMyRequests.setOnClickListener {
                llRequests.visibility = View.GONE
                rvMyRequests.visibility = View.VISIBLE
                idRequests.visibility = View.INVISIBLE
                idMyRequests.visibility = View.VISIBLE
            }

            btnSend.setOnClickListener {
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

        val tvTitle: TextView = dialog.findViewById(R.id.tvTitle)
        val btnDismiss: TextView = dialog.findViewById(R.id.btnNo)
        val btnLogout: TextView = dialog.findViewById(R.id.btnYes)

        tvTitle.text = "Do you want to send?"

        btnDismiss.setOnClickListener {
            dialog.dismiss()
        }

        btnLogout.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}