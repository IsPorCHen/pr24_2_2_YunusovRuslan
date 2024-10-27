package com.example.incomelistapp2024

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.incomelistapp2024.databinding.FragmentDateListBinding
import java.util.Calendar

data class Income(var date: String, var amount: String)

class DateListFragment : Fragment() {

    private var _binding: FragmentDateListBinding? = null
    private val binding get() = _binding!!

    private val incomeList = mutableListOf<Income>()
    private lateinit var adapter: IncomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDateListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = IncomeAdapter(incomeList) { income, position ->
            val action = DateListFragmentDirections
                .actionDateListFragmentToIncomeDetailsFragment(income.date, income.amount, position)
            findNavController().navigate(action)
        }

        binding.dateRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.dateRecyclerView.adapter = adapter

        binding.addIncomeButton.setOnClickListener {
            showAddIncomeDialog()
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>("deletedPosition")
            ?.observe(viewLifecycleOwner) { deletedPosition ->
                if (deletedPosition != null && deletedPosition in incomeList.indices) {
                    incomeList.removeAt(deletedPosition)
                    adapter.notifyDataSetChanged()
                }
            }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>("editedPosition")
            ?.observe(viewLifecycleOwner) { editedPosition ->
                val editedDate = findNavController().currentBackStackEntry?.savedStateHandle?.get<String>("editedDate")
                val editedAmount = findNavController().currentBackStackEntry?.savedStateHandle?.get<String>("editedAmount")
                if (editedPosition != null && editedPosition in incomeList.indices) {
                    val income = incomeList[editedPosition]
                    income.date = editedDate ?: income.date
                    income.amount = editedAmount ?: income.amount
                    adapter.notifyDataSetChanged()
                }
            }

    }

    private fun showAddIncomeDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_income, null)
        val datePicker = dialogView.findViewById<DatePicker>(R.id.datePicker)
        val amountInput = dialogView.findViewById<EditText>(R.id.editTextAmount)

        val calendar = Calendar.getInstance()
        datePicker.minDate = calendar.timeInMillis

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Добавить доход")
        builder.setView(dialogView)
        builder.setPositiveButton("Добавить") { _, _ ->
            val date = "${datePicker.dayOfMonth}.${datePicker.month + 1}.${datePicker.year}"
            val amount = amountInput.text.toString()
            if (amount.isNotEmpty()) {
                incomeList.add(Income(date, amount))
                adapter.notifyDataSetChanged()
                Toast.makeText(requireContext(), "Доход добавлен", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Отмена", null)
        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
