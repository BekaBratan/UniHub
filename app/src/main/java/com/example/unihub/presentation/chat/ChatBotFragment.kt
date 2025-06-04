package com.example.unihub.presentation.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unihub.databinding.FragmentChatBotBinding
import com.example.unihub.utils.provideNavigationHost

class ChatBotFragment : Fragment() {

    private lateinit var binding: FragmentChatBotBinding
    private lateinit var chatAdapter: ChatAdapter
    private val viewModel: ChatBotViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        provideNavigationHost()?.hideBottomNavigationBar(false)

        // Set up RecyclerView
        chatAdapter = ChatAdapter()
        binding.rvMessages.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.run {
            tvFirstHelp.setOnClickListener {
                llSuggestions.visibility = View.GONE
                etMessage.setText(tvFirstHelp.text)
            }
            tvSecondHelp.setOnClickListener {
                llSuggestions.visibility = View.GONE
                etMessage.setText(tvSecondHelp.text)
            }
            tvThirdHelp.setOnClickListener {
                etMessage.setText(tvThirdHelp.text)
                llSuggestions.visibility = View.GONE
            }
        }

        binding.btnSend.setOnClickListener {
            val userMessage = binding.etMessage.text.toString()
            if (userMessage.isNotBlank()) {
                chatAdapter.submitMessage(ChatMessage(userMessage, true))
                binding.etMessage.text.clear()
                binding.llSuggestions.visibility = View.GONE
                binding.rvMessages.visibility = View.VISIBLE
                viewModel.sendMessageToBot(userMessage)
            }
        }

        viewModel.botMessage.observe(viewLifecycleOwner) { botReply ->
            chatAdapter.submitMessage(ChatMessage(botReply, false))
        }

    }


}
