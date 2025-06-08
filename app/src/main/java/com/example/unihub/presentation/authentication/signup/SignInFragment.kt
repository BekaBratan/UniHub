package com.example.unihub.presentation.authentication.signup

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentSignInBinding
import com.example.unihub.presentation.authentication.AuthViewModel
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import kotlin.getValue

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)
        val sharedProvider = SharedProvider(requireContext())
        sharedProvider.clearShared()

        binding.run {
            btnBack.setOnClickListener {
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToWelcomeFragment())
            }

            etName.addTextChangedListener {
                tvError.visibility = View.INVISIBLE
            }

            etSurname.addTextChangedListener {
                tvError.visibility = View.INVISIBLE
            }

            etEmail.addTextChangedListener {
                tvError.visibility = View.INVISIBLE
            }

            etPassword1.addTextChangedListener {
                tvError.visibility = View.INVISIBLE
            }

            etPassword2.addTextChangedListener {
                tvError.visibility = View.INVISIBLE
            }

            btnCreate.setOnClickListener {
                if (
                    etPassword1.text.toString().isEmpty() ||
                    etPassword2.text.toString().isEmpty() ||
                    etEmail.text.toString().isEmpty() ||
                    etName.text.toString().isEmpty() ||
                    etSurname.text.toString().isEmpty()
                ) {
                    tvError.text = getString(R.string.please_fill_all_fields)
                    tvError.visibility = View.VISIBLE
                    return@setOnClickListener
                }

                val email = etEmail.text.toString().trim()
                val password1 = etPassword1.text.toString().trim()
                val password2 = etPassword2.text.toString().trim()
                val name = etName.text.toString().trim()
                val surname = etSurname.text.toString().trim()

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    tvError.text = getString(R.string.invalid_email)
                    tvError.visibility = View.VISIBLE
                    return@setOnClickListener
                }

                if (password1.length < 8) {
                    tvError.text = getString(R.string.password_too_short)
                    tvError.visibility = View.VISIBLE
                    return@setOnClickListener
                }

                if (password1 != password2) {
                    tvError.text = getString(R.string.passwords_are_not_same)
                    tvError.visibility = View.VISIBLE
                    return@setOnClickListener
                }

                if (name.length < 2 || surname.length < 2) {
                    tvError.text = getString(R.string.name_too_short)
                    tvError.visibility = View.VISIBLE
                    return@setOnClickListener
                }

                tvError.visibility = View.INVISIBLE
                authViewModel.signup(email = email, password = password1, name = name, surname = surname)
                btnCreate.visibility = View.INVISIBLE
                btnLoad.visibility = View.VISIBLE
            }

            authViewModel.signupResponse.observe(viewLifecycleOwner) {
                Log.d("Login Response", it.toString())
                sharedProvider.saveEmail(it.email.toString())
                btnCreate.visibility = View.VISIBLE
                btnLoad.visibility = View.INVISIBLE
//                sharedProvider.saveToken(it.token.toString())
//                sharedProvider.saveEmail(it.user.email.toString())
//                sharedProvider.saveName(it.user.name.toString())
//                sharedProvider.saveSurname(it.user.surname.toString())
//                sharedProvider.saveRole(it.user.role.toString())
//                sharedProvider.saveID(it.user.id)
//                sharedProvider.saveIsAuthorized(true)
//                provideNavigationHost()?.setupBottomNavForRole(it.user.role.toString().contains("admin", ignoreCase = true))
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToVerifyFragment2())
            }

            authViewModel.errorMessage.observe(viewLifecycleOwner) {
                btnCreate.visibility = View.VISIBLE
                btnLoad.visibility = View.INVISIBLE
                tvError.text = it.message
                tvError.visibility = View.VISIBLE
            }

            authViewModel.signUpErrorMessage.observe(viewLifecycleOwner) {
                btnCreate.visibility = View.VISIBLE
                btnLoad.visibility = View.INVISIBLE
                tvError.text = it.error
                tvError.visibility = View.VISIBLE
            }
        }
    }

}