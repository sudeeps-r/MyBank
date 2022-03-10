package com.app.mybank.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.mybank.R
import com.app.mybank.databinding.ItemHistoryBinding
import com.app.mybank.ui.base.inflate
import com.app.mybank.ui.home.service.HistoryViewResponse

class TransactionHistoryAdapter() :
    RecyclerView.Adapter<TransactionHistoryItemViewHolder>() {

    val response = mutableListOf<HistoryViewResponse>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionHistoryItemViewHolder {
        val binder = parent.inflate<ItemHistoryBinding>(R.layout.item_history)
        return TransactionHistoryItemViewHolder(binder)
    }

    override fun onBindViewHolder(holder: TransactionHistoryItemViewHolder, position: Int) {
        holder.bind(response[position])
    }

    override fun getItemCount(): Int = response.size

    fun addData(data: List<HistoryViewResponse>) {
        response.addAll(data)
        notifyDataSetChanged()
    }
}


class TransactionHistoryItemViewHolder(private val binding: ItemHistoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(date: HistoryViewResponse) {
        binding.data = date
        binding.executePendingBindings()
    }
}