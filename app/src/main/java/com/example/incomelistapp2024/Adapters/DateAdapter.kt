package com.example.incomelistapp2024.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DateAdapter(
    private val dates: List<String>,
    private val onDateClicked: (String) -> Unit
) : RecyclerView.Adapter<DateAdapter.DateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return DateViewHolder(view)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val date = dates[position]
        holder.bind(date, position)
        holder.itemView.setOnClickListener {
            onDateClicked(date)
        }
    }

    override fun getItemCount() = dates.size

    class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(date: String, index: Int) {
            textView.text = date
        }
    }
}