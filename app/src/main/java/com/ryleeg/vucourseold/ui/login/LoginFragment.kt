package com.ryleeg.vucourseold.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.ryleeg.vucourseold.R
import com.ryleeg.vucourseold.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by activityViewModels()
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val loginButton = root.findViewById<Button>(R.id.loginButton)
        val userNameInput = root.findViewById<TextInputEditText>(R.id.TextEmailAddress)
        val passwordInput = root.findViewById<TextInputEditText>(R.id.TextPassword)

        /*        // Navigate to dashboard if token set and valid
                if (viewModel.isLoggedIn()){
                    root.findNavController().navigate(R.id.navigation_dashboard)
                }*/

        loginButton.setOnClickListener {
            this.context?.let { it1 ->
                run {
                    val login = viewModel.login(
                        it1,
                        userNameInput.text.toString(),
                        passwordInput.text.toString()
                    )
                    if (login) {
                        root.findNavController().navigate(R.id.navigation_courses)
                    }
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