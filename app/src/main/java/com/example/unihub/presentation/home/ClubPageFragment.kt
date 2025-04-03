package com.example.unihub.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unihub.R
import com.example.unihub.data.Posts
import com.example.unihub.databinding.FragmentClubPageBinding
import com.example.unihub.databinding.FragmentSearchBinding
import com.example.unihub.utils.CustomDividerItemDecoration
import com.example.unihub.utils.RcViewItemClickIdCallback
import com.example.unihub.utils.provideNavigationHost

class ClubPageFragment : Fragment() {

    private lateinit var binding: FragmentClubPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClubPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(false)

        val postsAdapter = PostsAdapter()
        postsAdapter.submitList(List(10) { Posts() })

        postsAdapter.setOnLikeClickListener(
            object : RcViewItemClickIdCallback {
                override fun onClick(id: Int) {

                }
            }
        )

        val verticalLinearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.run {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            rvPosts.layoutManager = verticalLinearLayoutManager
            rvPosts.adapter = postsAdapter
            rvPosts.addItemDecoration(
                CustomDividerItemDecoration(
                    getDrawable(
                        requireContext(),
                        R.drawable.divider_1dp
                    )!!
                )
            )
        }
    }

}