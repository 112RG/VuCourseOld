package com.ryleeg.vucourseold.ui.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryleeg.vucourseold.PreferencesManager
import com.ryleeg.vucourseold.data.LoginBody
import com.ryleeg.vucourseold.data.VuApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val vuApi: VuApi,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    fun isLoggedIn(): Boolean {
        return if (preferencesManager.getData("token", "default") == "default") {
            false
        } else {
            println("Has token")
            true
        }
    }

    fun login(
        context: Context,
        username: String,
        password: String,
    ): Boolean {

        viewModelScope.launch {
            try {
                val loginReq = vuApi.login(LoginBody(username, password))
                preferencesManager.saveData("token", loginReq.token)
            } catch (ex: Exception) {
                Toast.makeText(
                    context,
                    "An error occurred: ${ex.message}",
                    Toast.LENGTH_SHORT
                )
                    .show()

            }
        }
        return preferencesManager.getData("token", "default") != "default"
    }
}