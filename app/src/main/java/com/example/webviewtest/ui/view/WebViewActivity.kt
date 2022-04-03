package com.example.webviewtest.ui.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.webviewtest.R
import com.example.webviewtest.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.back_arrow)
        setupWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.webView.settings.loadWithOverviewMode = true;
        binding.webView.settings.useWideViewPort = true;
        binding.webView.settings.javaScriptEnabled = true;
        binding.webView.settings.javaScriptCanOpenWindowsAutomatically = true;
        binding.webView.loadUrl(intent.getStringExtra("HTML_TEXT").toString())
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed();
        return true;
    }
}