package com.example.incomelistapp2024

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class IncomeAdapter(
    private val incomeList: List<Income>,
    private val onClick: (Income, Int) -> Unit
) : RecyclerView.Adapter<IncomeAdapter.IncomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
        return IncomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: IncomeViewHolder, position: Int) {
        val income = incomeList[position]
        holder.dateTextView.text = income.date
        holder.amountTextView.text = income.amount

        holder.itemView.setOnClickListener {
            onClick(income, position)
        }
    }

    override fun getItemCount() = incomeList.size

    class IncomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(android.R.id.text1)
        val amountTextView: TextView = itemView.findViewById(android.R.id.text2)
    }
}

