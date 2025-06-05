package com.example.unihub.presentation.admin

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.unihub.R
import com.example.unihub.databinding.FragmentAdminPageBinding
import com.example.unihub.utils.RcViewItemClickIdCallback
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost

class AdminPageFragment : Fragment() {

    private lateinit var binding: FragmentAdminPageBinding
    private val adminViewModel: AdminViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)
        val sharedProvider = SharedProvider(requireContext())

        adminViewModel.getUsersList(sharedProvider.getToken())
        adminViewModel.getClubsList(sharedProvider.getToken())

        val adapterUsers = UsersListItemAdapter()
        val adapterClubs = ClubsListItemAdapter()
        var usersCount = adapterUsers.itemCount
        var clubsCount = adapterClubs.itemCount

        adminViewModel.getUsersListResponse.observe(viewLifecycleOwner) { usersList ->
            adapterUsers.submitList(usersList)
            usersCount = usersList.size
            binding.run {
                tvUsersCount.text = usersCount.toString()
            }
        }

        adminViewModel.getClubList.observe(viewLifecycleOwner) { clubsList ->
            adapterClubs.submitList(clubsList)
            clubsCount = clubsList.size
            binding.run {
                tvClubsCount.text = clubsCount.toString()
            }
        }

        adminViewModel.errorMessage.observe(viewLifecycleOwner) {
            binding.tvWelcomeMsg.text = it.message
        }

        adapterUsers.setOnUserNameClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int?) {
                    findNavController().navigate(AdminPageFragmentDirections.actionAdminPageFragmentToEditUserFragment(id!!))
                }
            }
        )

        adapterClubs.setOnDeleteClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int?) {
                    showCustomDialogBox(id!!)
                }
            }
        )

        adapterClubs.setOnUserNameClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int?) {
                    findNavController().navigate(AdminPageFragmentDirections.actionAdminPageFragmentToEditUserFragment(id!!))
                }
            }
        )

        binding.run {
            btnAddUser.setOnClickListener {
                findNavController().navigate(AdminPageFragmentDirections.actionAdminPageFragmentToAddUserFragment())
            }

            btnMenu.setOnClickListener {
                sidebar.visibility = View.VISIBLE
            }

            llHome.setOnClickListener {
                sidebar.visibility = View.GONE
            }

            llProfile.setOnClickListener {
                findNavController().navigate(AdminPageFragmentDirections.actionAdminPageFragmentToProfileFragment())
            }

            llRequests.setOnClickListener {
                findNavController().navigate(AdminPageFragmentDirections.actionAdminPageFragmentToRequestsListFragment())
            }

            tvClubRequests.setOnClickListener {
                idClubRequests.visibility = View.VISIBLE
                idEventRequests.visibility = View.INVISIBLE

                rvUsersList.visibility = View.VISIBLE
                rvClubsList.visibility = View.GONE
            }

            tvEventRequests.setOnClickListener {
                idClubRequests.visibility = View.INVISIBLE
                idEventRequests.visibility = View.VISIBLE

                rvUsersList.visibility = View.GONE
                rvClubsList.visibility = View.VISIBLE
            }

            rvUsersList.adapter = adapterUsers
            rvClubsList.adapter = adapterClubs
        }
    }

    private fun showCustomDialogBox(id: Int) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_log_out)
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)

        val btnNo: TextView = dialog.findViewById(R.id.btnNo)
        val btnYes: TextView = dialog.findViewById(R.id.btnYes)

        val tvTitle: TextView = dialog.findViewById(R.id.tvTitle)

        tvTitle.text = "Are you sure you want to delete this user?"

        btnNo.setOnClickListener {
            dialog.dismiss()
        }

        btnYes.setOnClickListener {
            adminViewModel.deleteClub(SharedProvider(requireContext()).getToken(), id)
            dialog.dismiss()
        }

        dialog.show()
    }
}