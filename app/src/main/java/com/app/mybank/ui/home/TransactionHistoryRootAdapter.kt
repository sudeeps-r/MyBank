package com.app.mybank.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.mybank.R
import com.app.mybank.databinding.ItemHistoryRootRowBinding
import com.app.mybank.ui.base.inflate
import com.app.mybank.ui.home.service.HistoryViewResponse

class TransactionHistoryRootAdapter() :
    RecyclerView.Adapter<TransactionHistoryViewHolder>() {

    val response = mutableMapOf<String, List<HistoryViewResponse>>()
    val keys = mutableListOf<String>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionHistoryViewHolder {
        val binder = parent.inflate<ItemHistoryRootRowBinding>(R.layout.item_history_root_row)
        return TransactionHistoryViewHolder(binder)
    }

    override fun onBindViewHolder(holder: TransactionHistoryViewHolder, position: Int) {
        holder.bind(keys[position], response[keys[position]].orEmpty())
    }

    override fun getItemCount(): Int = response.size

    fun addData(data: Pair<List<String>?, Map<String, List<HistoryViewResponse>>?>) {
        keys.addAll(data.first.orEmpty())
        response.putAll(data.second.orEmpty())
        notifyDataSetChanged()
    }
}


class TransactionHistoryViewHolder(private val binding: ItemHistoryRootRowBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(date: String, response: List<HistoryViewResponse>) {
        val childAdapter = TransactionHistoryAdapter()
        binding.date = date
        binding.sublist.adapter = childAdapter
        childAdapter.addData(response)
        binding.executePendingBindings()
    }
}