package com.vaibhavdhunde.app.friendlychat.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.vaibhavdhunde.app.friendlychat.R
import com.vaibhavdhunde.app.friendlychat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
    }

    private fun setupBinding() {
        viewModel = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        binding.apply {
            viewmodel = this@MainActivity.viewModel
            lifecycleOwner = this@MainActivity
        }
    }
}
