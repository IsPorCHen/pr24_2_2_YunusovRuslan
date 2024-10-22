package com.example.incomelistapp2024

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.incomelistapp2024.databinding.FragmentIncomeDetailsBinding
class IncomeDetailsFragment : Fragment() {

    private var _binding: FragmentIncomeDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIncomeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedDate = arguments?.getString("selectedDate") ?: "Нет даты"
        val incomeValue = arguments?.getInt("incomeValue") ?: 0

        binding.incomeDetailsTextView.text = "Детали дохода за $selectedDate"
        binding.incomeValue.text = "Сумма дохода: $incomeValue"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

