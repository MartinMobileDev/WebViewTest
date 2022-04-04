package com.example.webviewtest.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.webviewtest.R
import com.example.webviewtest.WebViewTest
import com.example.webviewtest.adapter.NoticeListAdapter
import com.example.webviewtest.adapter.OnClickListener
import com.example.webviewtest.databinding.ActivityMainBinding
import com.example.webviewtest.domain.model.Notice
import com.example.webviewtest.ui.viewModel.MainViewModel
import com.example.webviewtest.utils.NetworkConnection
import com.example.webviewtest.utils.SwipeGesture
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mlinearLayoutManager: LinearLayoutManager
    private lateinit var madapter: NoticeListAdapter
    private val mainViewModel: MainViewModel by viewModels()
    private val networkConnection = NetworkConnection(WebViewTest.applicationContext())

    private val swipeGesture = object : SwipeGesture(WebViewTest.applicationContext()) {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            when (direction) {
                ItemTouchHelper.LEFT -> {
                    val notice = madapter.currentList[viewHolder.adapterPosition]
                    val list = ArrayList<Notice>(madapter.currentList)
                    list.remove(notice)
                    madapter.submitList(list)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkForInternetConnection()
        initRecyclerView()
        setupViewModel()
    }

    private fun checkForInternetConnection() {
        networkConnection.observe(this) { isConnected ->
            if (isConnected) {
                mainViewModel.onCreateApi()
                Toast.makeText(this, "Connectado", Toast.LENGTH_SHORT).show()
            } else {
                mainViewModel.onCreateDatabase()
                Toast.makeText(this, "Desconnectado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupViewModel() {
        mainViewModel.isLoading.observe(this) {
            binding.progress.isVisible = it
        }
        mainViewModel.noticeListModel.observe(this) { noticeList ->
            madapter.submitList(noticeList)
        }
    }

    @SuppressLint("ResourceAsColor", "NotifyDataSetChanged")
    private fun initRecyclerView() {

        madapter = NoticeListAdapter(this)
        mlinearLayoutManager = LinearLayoutManager(this)
        binding.rvNews.apply {
            layoutManager = mlinearLayoutManager
            adapter = madapter
        }
        binding.swipeContainer.setColorSchemeResources(R.color.purple_500)
        binding.swipeContainer.setOnRefreshListener {
            mainViewModel.clearNews()
            checkForInternetConnection()
            madapter.notifyDataSetChanged()
            binding.swipeContainer.isRefreshing = false
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(binding.rvNews)
    }

    override fun onClick(notice: Notice) {
        Intent(this, WebViewActivity::class.java).also {
            it.putExtra("HTML_TEXT", notice.webViewText)
            startActivity(it)
        }
    }
}