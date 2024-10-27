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
import com.example.incomelistapp2024.databinding.FragmentIncomeDetailsBinding
import java.util.Calendar

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

        val args = IncomeDetailsFragmentArgs.fromBundle(requireArguments())
        val date = args.selectedDate
        val amount = args.incomeAmount
        val position = args.position

        binding.dateTextView.text = date
        binding.amountTextView.text = amount

        binding.editButton.setOnClickListener {
            showEditIncomeDialog(date, amount, position)
        }

        binding.deleteButton.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set("deletedPosition", position)
            findNavController().popBackStack()
        }
    }

    private fun showEditIncomeDialog(date: String, amount: String, position: Int) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_income, null)
        val datePicker = dialogView.findViewById<DatePicker>(R.id.datePicker)
        val amountInput = dialogView.findViewById<EditText>(R.id.editTextAmount)

        val calendar = Calendar.getInstance()
        datePicker.minDate = calendar.timeInMillis
        val (day, month, year) = date.split(".").map { it.toInt() }
        datePicker.updateDate(year, month - 1, day)
        amountInput.setText(amount)

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Редактировать доход")
        builder.setView(dialogView)
        builder.setPositiveButton("Сохранить") { _, _ ->
            val newDate = "${datePicker.dayOfMonth}.${datePicker.month + 1}.${datePicker.year}"
            val newAmount = amountInput.text.toString()
            if (newAmount.isNotEmpty()) {
                findNavController().previousBackStackEntry?.savedStateHandle?.set("editedPosition", position)
                findNavController().previousBackStackEntry?.savedStateHandle?.set("editedDate", newDate)
                findNavController().previousBackStackEntry?.savedStateHandle?.set("editedAmount", newAmount)
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
