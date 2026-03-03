package com.example.aushadhiplus.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import com.example.aushadhiplus.data.local.TokenManager
import kotlinx.coroutines.flow.first

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        // Get token synchronously
        val token = runBlocking {
            tokenManager.token.first()
        }

        val requestBuilder = chain.request().newBuilder()

        token?.let {
            requestBuilder.addHeader(
                "Authorization",
                "Bearer $it"
            )
        }

        return chain.proceed(requestBuilder.build())
    }

}