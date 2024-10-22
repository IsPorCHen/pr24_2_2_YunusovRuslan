package com.example.incomelistapp2024

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.incomelistapp2024.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _username: String = "ipch"
    private var _password: String = "1234"
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (isLoginValid(username, password)) {
                val action = LoginFragmentDirections.actionLoginFragmentToDateListFragment()
                findNavController().navigate(action)
            } else {
                if (username != _username)
                    binding.usernameEditText.error = "Неверный логин"
                if (password != _password)
                    binding.passwordEditText.error = "Неверный пароль"
            }
        }
    }

    private fun isLoginValid(username: String, password: String): Boolean {
        return username == _username && password == _password
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
