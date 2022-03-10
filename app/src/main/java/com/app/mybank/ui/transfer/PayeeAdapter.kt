package com.app.mybank.ui.transfer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.app.mybank.R
import com.app.mybank.ui.transfer.service.PayeeData

class PayeeAdapter(ctx: Context, countries: List<PayeeData>) :
    ArrayAdapter<PayeeData>(ctx, 0, countries) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    private fun createItemView(position: Int, recycledView: View?, parent: ViewGroup): View {
        val payeeData = getItem(position)

        val view = recycledView ?: LayoutInflater.from(context).inflate(
            R.layout.item_payee_list,
            parent,
            false
        )

        payeeData?.let {
            view.findViewById<TextView>(R.id.tv_accnt_holder).text = it.name
            view.findViewById<TextView>(R.id.tv_accnt_no).text = it.accountNo
        }
        return view
    }
}