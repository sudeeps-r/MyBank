package com.app.mybank.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.mybank.R
import com.app.mybank.core.usecase.Resource
import com.app.mybank.databinding.HomeActivityBinding
import com.app.mybank.ui.base.BaseActivity
import com.app.mybank.ui.base.inflate
import com.app.mybank.ui.base.showToolbar

class HomeActivity : BaseActivity() {
    private val viewModel: HomeViewModel by viewModels()
    private val rootAdapter = TransactionHistoryRootAdapter()
    lateinit var binder: HomeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = inflate(R.layout.home_activity)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.m_logout -> showAlertDialog(
                getString(R.string.app_name),
                getString(R.string.logout_confirmation),
                {
                    viewModel.logout.observe(this, Observer {
                        navigator.navigateToLogin(this)
                    })
                }
            ) {}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        showToolbar(R.string.label_dashboard, binder.toolbar)
        binder.vm = viewModel
        binder.rvlist.adapter = rootAdapter
        binder.lifecycleOwner = this
        viewModel.balance.observe(this, {
            apiDefaultHandler(it) {}
        })
        viewModel.txnHistory.observe(this, {
            apiDefaultHandler(it) { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> it.data?.let { data ->
                        rootAdapter.addData(data)
                    }
                    else -> {}
                }
            }
        })
    }

    private fun makeHomeCall() {
        viewModel.refreshHome()
    }

    fun openTransfer(view: View) {
        navigator.navigateToTransfer(this)
    }

    override fun onResume() {
        super.onResume()
        makeHomeCall()
    }

}