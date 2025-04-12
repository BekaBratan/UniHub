package com.example.unihub.presentation.home.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unihub.data.model.Clubs
import com.example.unihub.databinding.FragmentSearchBinding
import com.example.unihub.utils.RcViewItemClickIdCallback
import com.example.unihub.utils.RcViewItemClickIdStringCallback
import com.example.unihub.utils.provideNavigationHost

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(false)

        val clubsAdapter = RecInlineAdapter()
        clubsAdapter.submitList(List(6) { Clubs() })

        clubsAdapter.setOnFollowClickListener(
            object : RcViewItemClickIdStringCallback {
                override fun onClick(id: String) {

                }
            }
        )

        val verticalLinearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val horizontalLinearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.run {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            rvClubs.layoutManager = verticalLinearLayoutManager
            rvClubs.adapter = clubsAdapter
        }
    }

}