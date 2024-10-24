package com.example.incomelistapp2024

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.incomelistapp2024.Adapters.IncomeAdapter
import com.example.incomelistapp2024.databinding.FragmentDateListBinding
import java.text.SimpleDateFormat
import java.util.Locale

data class Income(val date: String, val amount: String)

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

        adapter = IncomeAdapter(incomeList) { position ->
            showEditIncomeDialog(position)
        }
        binding.dateRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.dateRecyclerView.adapter = adapter

        binding.addIncomeButton.setOnClickListener {
            showAddIncomeDialog()
        }
    }

    private fun isValidDate(date: String, format: String = "dd.MM.yyyy"): Boolean {
        return try {
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            sdf.isLenient = false
            sdf.parse(date) != null
        } catch (e: Exception) {
            false
        }
    }

    private fun showAddIncomeDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_income, null)
        val dateInput = dialogView.findViewById<EditText>(R.id.editTextDate)
        val amountInput = dialogView.findViewById<EditText>(R.id.editTextAmount)

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Добавить доход")
        builder.setView(dialogView)
        builder.setPositiveButton("Добавить") { _, _ ->
            val date = dateInput.text.toString()
            val amount = amountInput.text.toString()

            if (isValidDate(date) && amount.isNotEmpty()) {
                incomeList.add(Income(date, amount))
                adapter.notifyDataSetChanged()
                Toast.makeText(requireContext(), "Доход добавлен", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Пожалуйста, введите корректную дату и заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Отмена", null)
        builder.show()
    }

    private fun showEditIncomeDialog(position: Int) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_income, null)
        val dateInput = dialogView.findViewById<EditText>(R.id.editTextDate)
        val amountInput = dialogView.findViewById<EditText>(R.id.editTextAmount)

        val currentIncome = incomeList[position]
        dateInput.setText(currentIncome.date)
        amountInput.setText(currentIncome.amount)

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Редактировать доход")
        builder.setView(dialogView)
        builder.setPositiveButton("Сохранить") { _, _ ->
            val date = dateInput.text.toString()
            val amount = amountInput.text.toString()
            if (date.isNotEmpty() && amount.isNotEmpty()) {
                incomeList[position] = Income(date, amount)
                adapter.notifyDataSetChanged()
                Toast.makeText(requireContext(), "Доход обновлен", Toast.LENGTH_SHORT).show()
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
