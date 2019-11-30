package com.vaibhavdhunde.app.friendlychat.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.vaibhavdhunde.app.friendlychat.R
import com.vaibhavdhunde.app.friendlychat.databinding.ActivityMainBinding
import com.vaibhavdhunde.app.friendlychat.util.EventObserver
import com.vaibhavdhunde.app.friendlychat.util.toast

class MainActivity : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 4684
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupEvents()
        checkUserAuthentication()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_logout -> {
                logoutUser()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                binding.viewmodel?.initUser()
                binding.viewmodel?.fetchMessages()
            } else if (resultCode == Activity.RESULT_CANCELED) {
                val response = IdpResponse.fromResultIntent(data)
                if (response != null) {
                    binding.viewmodel?.handleErrors(response)
                }
                finish()
            }
        }
    }

    private fun setupBinding() {
        viewModel = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        binding.apply {
            viewmodel = this@MainActivity.viewModel
            adapter = MessagesListAdapter()
            lifecycleOwner = this@MainActivity
        }
    }

    private fun setupEvents() {
        binding.viewmodel?.authenticateUser?.observe(this, EventObserver {
            authenticateUser()
        })

        binding.viewmodel?.showMessage?.observe(this, EventObserver { message ->
            toast(message)
        })
    }

    private fun checkUserAuthentication() {
        binding.viewmodel?.checkUserAuthentication()
    }

    private fun authenticateUser() {
        AuthUI.getInstance().createSignInIntentBuilder()
            .setIsSmartLockEnabled(false)
            .setAvailableProviders(getSignInProviders())
            .build().also {
                startActivityForResult(it, RC_SIGN_IN)
            }
    }

    private fun getSignInProviders(): List<AuthUI.IdpConfig> {
        return listOf(
            AuthUI.IdpConfig.EmailBuilder()
                .setRequireName(true)
                .build(),
            AuthUI.IdpConfig.GoogleBuilder()
                .build()
        )
    }

    private fun logoutUser() {
        binding.viewmodel?.logoutUser()
    }
}
