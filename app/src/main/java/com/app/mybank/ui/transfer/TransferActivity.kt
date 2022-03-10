package com.app.mybank.ui.transfer

import android.os.Bundle
import android.widget.AdapterView
import androidx.activity.viewModels
import com.app.mybank.R
import com.app.mybank.core.usecase.Resource
import com.app.mybank.core.util.CoreConstant
import com.app.mybank.databinding.TransferActivityBinding
import com.app.mybank.ui.base.BaseActivity
import com.app.mybank.ui.base.hideKeyBoard
import com.app.mybank.ui.base.inflate
import com.app.mybank.ui.base.showToolbar

class TransferActivity : BaseActivity() {
    private val viewModel: TransferViewModel by viewModels()
    lateinit var binder: TransferActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = inflate(R.layout.transfer_activity)
        showToolbar(title = R.string.m_transfer, mToolbar = binder.toolbar, backEnabled = true)
        init()
    }

    private fun init() {
        binder.lifecycleOwner = this
        binder.vm = viewModel
        viewModel.didTransfer.observe(this, {
            apiDefaultHandler(it) { resource ->
                hideKeyBoard()
                when (resource.status) {

                    Resource.Status.ERROR -> {
                        showSnackBar(it.message.orEmpty())
                    }
                    Resource.Status.SUCCESS -> {

                        if (it.data?.status == CoreConstant.API_SUCCESS) {
                            showSnackBar(getString(R.string.transaction_completed))
                        } else {
                            showSnackBar(getString(R.string.transaction_failed))
                        }
                    }
                    else -> {}
                }

            }
        })

        viewModel.payeeList.observe(this, {
            apiDefaultHandler(it) { response ->
                when (response.status) {
                    Resource.Status.SUCCESS -> {
                        binder.data = it.data?.list?.first()
                        binder.payeeList.setText(binder.data?.name, false)
                        val spinnerAdapter = PayeeAdapter(this, it.data?.list.orEmpty())
                        binder.payeeList.setAdapter(spinnerAdapter)
                        binder.payeeList.run {
                            onItemClickListener =
                                AdapterView.OnItemClickListener { _, _, p2, _ ->
                                    binder.data = it.data?.list?.get(p2)
                                }
                        }
                    }
                    else -> {}
                }
            }
        })

    }
}