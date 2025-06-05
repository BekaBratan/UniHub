package com.example.unihub.presentation.profile

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentLanguageBinding
import com.example.unihub.utils.OnLanguageSelectedListener
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import java.util.Locale
import kotlin.getValue

class LanguageFragment : Fragment() {

    private lateinit var binding: FragmentLanguageBinding
    private val viewModel: ProfileViewModel by viewModels()
    private var languageSelectedListener: OnLanguageSelectedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(true)
        val defaultLanguage: String = SharedProvider(requireContext()).getLanguage()

        when(defaultLanguage){
            "en" -> {
                selectedIcon(true, false, false)
            }
            "kk" -> {
                selectedIcon(false, true, false)
            }
            "ru" -> {
                selectedIcon(false, false, true)
            }
        }

        binding.run {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            llEnglish.setOnClickListener {
                selectedIcon(true, false, false)
                changeLanguage("English")
            }
            llKazakh.setOnClickListener {
                selectedIcon(false, true, false)
                changeLanguage("Қазақша")
            }
            llRussian.setOnClickListener {
                selectedIcon(false, false, true)
                changeLanguage("Русcкий")
            }
        }
    }

    private fun changeLanguage(language: String) {
        when(language){
            "English" -> {
                systemLanguageChange("en")
                languageSelectedListener?.onLanguageSelected("English")
            }
            "Қазақша" -> {
                systemLanguageChange("kk")
                languageSelectedListener?.onLanguageSelected("Қазақша")
            }
            "Русcкий" -> {
                systemLanguageChange("ru")
                languageSelectedListener?.onLanguageSelected("Русcкий")
            }
        }
    }

    fun setOnLanguageSelectedListener(listener: OnLanguageSelectedListener) {
        languageSelectedListener = listener
    }

    private fun selectedIcon(iconEnglish: Boolean, iconKazakh: Boolean, iconRussian: Boolean) {
        binding.idEnglish.visibility = if (iconEnglish) View.VISIBLE else View.GONE
        binding.idKazakh.visibility = if (iconKazakh) View.VISIBLE else View.GONE
        binding.idRussian.visibility = if (iconRussian) View.VISIBLE else View.GONE
    }

    private fun systemLanguageChange(codeLanguage: String) {
        SharedProvider(requireContext()).saveLanguage(codeLanguage)

        // Change application interface language
        val locale = Locale(codeLanguage)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        requireContext().resources.updateConfiguration(config, requireContext().resources.displayMetrics)

        // This code navigates to the profile Fragment while clearing the navigation stack to that fragment
        // This approach is useful to prevent fragments from accumulating on the stack and to avoid reopening the same fragment
        findNavController().navigate(R.id.profileFragment, arguments, NavOptions.Builder().setPopUpTo(R.id.profileFragment, true).build())
    }
}