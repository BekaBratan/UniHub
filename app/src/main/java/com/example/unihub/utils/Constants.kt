package com.example.unihub.utils

import com.example.unihub.BuildConfig

class Constants {
    companion object {
        const val BASE_URL = "http://192.168.172.126:3000/api/"
//        const val BASE_URL = "http://192.168.0.11:5000/api/"
//        const val BASE_URL = "https://unihub-backend.onrender.com/api/"
        const val ChatGPT_URL = "https://api.openai.com/"
        const val ChatGPTApi = BuildConfig.CHAT_GPT_API_KEY
    }
}