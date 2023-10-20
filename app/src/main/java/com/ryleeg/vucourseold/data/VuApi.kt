package com.ryleeg.vucourseold.data

import com.ryleeg.vucourseold.PreferencesManager
import com.ryleeg.vucourseold.data.model.VuCoursesResponse
import com.ryleeg.vucourseold.data.model.VuLoginResponse
import com.ryleeg.vucourseold.data.model.VuScheduleResponse
import com.ryleeg.vucourseold.data.model.VuUserResponse
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface VuApi {
    @GET("user/course")
    suspend fun getCourses(): VuCoursesResponse

    @GET("user/info")
    suspend fun getUserInfo(): VuUserResponse

    @GET("user/schedule")
    suspend fun getSchedule(): VuScheduleResponse

    @POST("auth/login")
    suspend fun login(@Body requestBody: LoginBody): VuLoginResponse
}

data class LoginBody(val name: String, val password: String)


class AuthInterceptor(private val preferencesManager: PreferencesManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val token = preferencesManager.getData("token", "default")
        if (token != "default") {
            // If token has been saved, add it to the request
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }


        return chain.proceed(requestBuilder.build())
    }
}