package com.ryleeg.vucourseold.ui.login

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryleeg.vucourseold.PreferencesManager
import com.ryleeg.vucourseold.data.LoginBody
import com.ryleeg.vucourseold.data.VuApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val vuApi: VuApi,
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    private val loginStatus = MutableLiveData<Boolean>()
    fun login(
        username: String,
        password: String,
    ) {
        viewModelScope.launch {
            try {
                val loginReq = vuApi.login(LoginBody(username, password))
                // Will always be true if login passes
                preferencesManager.saveData("token", loginReq.token)
                loginStatus.value = preferencesManager.getData("token", "default") != "default"
            } catch (ex: Exception) {
                loginStatus.value = false
            }
        }
    }

    // Function to observe the login status from your fragment
    fun observeLoginStatus(owner: LifecycleOwner, observer: Observer<Boolean>) {
        loginStatus.observe(owner, observer)
    }
}