package com.gy25m.android_coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.gy25m.android_coroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    private val viewModel: MyViewModel by viewModels()
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView
    private lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        progressBar = findViewById(R.id.progressBar)
        textView = findViewById(R.id.textView)
        btn = findViewById(R.id.btn)
//
//        binding.btn.setOnClickListener { viewModel.aa() }
        binding.btn2.setOnClickListener { viewModel._uiState.value=UiState.Success("민임ㄴㅇㅁ  ㄴㅇㅁㄴㅇ")  }
        binding.btn3.setOnClickListener { viewModel.cc()  }
        binding.btn4.setOnClickListener { viewModel.dd() }
        // Observe the uiState
        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        progressBar.visibility = View.VISIBLE
                        textView.visibility = View.GONE
                    }
                    is UiState.Success -> {
                        Log.e("gyeom","zzzㅋzzz")
                        progressBar.visibility = View.GONE
                        textView.visibility = View.VISIBLE
                        binding.textView2.text=uiState.data
                    }
                    is UiState.Error -> {
                        progressBar.visibility = View.GONE
                        textView.visibility = View.VISIBLE
                        textView.text = uiState.message
                    }
                }
            }
        }

        btn.setOnClickListener {

            viewModel.loadData()
        }
        // Load data
    }
}

sealed class UiState {
    object Loading : UiState()
    data class Success(val data: String) : UiState()
    data class Error(val message: String) : UiState()
}