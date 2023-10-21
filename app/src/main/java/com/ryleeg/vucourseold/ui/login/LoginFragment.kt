package com.ryleeg.vucourseold.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.ryleeg.vucourseold.R
import com.ryleeg.vucourseold.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by activityViewModels()
    private var _binding: FragmentLoginBinding? = null
    
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val loginButton = root.findViewById<Button>(R.id.loginButton)
        val userNameInput = root.findViewById<TextInputEditText>(R.id.TextUsername)
        val passwordInput = root.findViewById<TextInputEditText>(R.id.TextPassword)

        viewModel.observeLoginStatus(this) { loginSuccessful ->
            if (loginSuccessful) {
                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                root.findNavController().navigate(R.id.navigation_courses)
            } else {
                Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show()
            }
        }

        loginButton.setOnClickListener {
            this.context?.let { it1 ->
                run {
                    viewModel.login(
                        userNameInput.text.toString(),
                        passwordInput.text.toString(),
                    )
                }
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}