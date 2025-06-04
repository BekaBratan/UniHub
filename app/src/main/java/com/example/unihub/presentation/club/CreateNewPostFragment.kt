package com.example.unihub.presentation.club

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentCreateNewPostBinding
import com.example.unihub.presentation.profile.ProfileViewModel
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import java.io.ByteArrayOutputStream
import kotlin.getValue

class CreateNewPostFragment : Fragment() {

    private lateinit var binding: FragmentCreateNewPostBinding
    private val clubViewModel: ClubViewModel by viewModels()
    private var imageBase64: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateNewPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(false)
        val sharedProvider = SharedProvider(requireContext())



        binding.run {
            val items = listOf("Request for event", "Create new post", "Create booking poster for event")
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, items)
            acTemplate.setAdapter(adapter)
//            acTemplate.setText("Choose template", false)
            acTemplate.setOnClickListener {
                acTemplate.showDropDown()
            }

            acTemplate.setOnItemClickListener { _, _, position, _ ->
                when (items[position]) {
                    "Request for event" -> {
                        findNavController().navigate(CreateNewPostFragmentDirections.actionCreateNewPostFragmentToCreateEventFragment())
                    }
                    "Create new post" -> {
                        // Действие для "Create new post"
                    }
                    "Create booking poster for event" -> {
                        findNavController().navigate(CreateNewPostFragmentDirections.actionCreateNewPostFragmentToCreatePosterFragment())
                    }
                }
            }

            btnAddMedia.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK).apply {
                    type = "image/*"
                }
                startActivityForResult(intent, REQUEST_CODE_PICK_MEDIA)
            }

            btnPost.setOnClickListener {
                clubViewModel.createPost(
                    token = sharedProvider.getToken(),
                    content = etDescription.text.toString(),
                    image = imageBase64,
                    title = etTitle.text.toString()
                )
            }
        }


        clubViewModel.createPostResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                findNavController().popBackStack()
            }
        }

        clubViewModel.errorMessage.observe(viewLifecycleOwner) {
            binding.tvError.text = it.message
            binding.tvError.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_MEDIA && resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImageUri = data?.data
            if (selectedImageUri != null) {
                val inputStream = requireContext().contentResolver.openInputStream(selectedImageUri)
                val bytes = inputStream?.readBytes()
                inputStream?.close()

                if (bytes != null) {
                    val base64String = Base64.encodeToString(bytes, Base64.NO_WRAP)
                    binding.btnAddMedia.text = "Media selected"
                    imageBase64 = base64String
                    Toast.makeText(requireContext(), base64String, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_PICK_MEDIA = 1001
    }
}