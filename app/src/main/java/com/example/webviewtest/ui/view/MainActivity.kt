package com.example.webviewtest.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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
import com.example.webviewtest.data.database.NoticeDatabase
import com.example.webviewtest.data.database.entities.toDatabase
import com.example.webviewtest.databinding.ActivityMainBinding
import com.example.webviewtest.domain.model.Notice
import com.example.webviewtest.ui.viewModel.MainViewModel
import com.example.webviewtest.utils.NetworkConnection
import com.example.webviewtest.utils.SwipeGesture
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mLinearLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: NoticeListAdapter
    private val mainViewModel: MainViewModel by viewModels()
    private val networkConnection = NetworkConnection(WebViewTest.applicationContext())

    private val swipeGesture = object : SwipeGesture(WebViewTest.applicationContext()) {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            when (direction) {
                ItemTouchHelper.LEFT -> {
                    val notice = mAdapter.currentList[viewHolder.adapterPosition]
                    notice.active = false
                    mainViewModel.updateNotice(notice.toDatabase().id)

                    val list = ArrayList<Notice>(mAdapter.currentList)
                    list.remove(notice)
                    mAdapter.submitList(list)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        NoticeDatabase.createDatabase(WebViewTest.applicationContext())
        checkForInternetConnection()
        initRecyclerView()
        setupViewModel()
    }


    private fun checkForInternetConnection() {
        networkConnection.observe(this) { isConnected ->
            if (isConnected) {
                mainViewModel.onCreateApi()
            } else {
                mainViewModel.onCreateDatabase()
            }
        }
    }

    private fun setupViewModel() {
        mainViewModel.isLoading.observe(this) {
            binding.progress.isVisible = it
        }
        mainViewModel.noticeListModel.observe(this) { noticeList ->
            mAdapter.submitList(noticeList)
        }
    }

    @SuppressLint("ResourceAsColor", "NotifyDataSetChanged")
    private fun initRecyclerView() {

        mAdapter = NoticeListAdapter(this)
        mLinearLayoutManager = LinearLayoutManager(this)
        binding.rvNews.apply {
            layoutManager = mLinearLayoutManager
            adapter = mAdapter
        }
        binding.swipeContainer.setColorSchemeResources(R.color.purple_500)
        binding.swipeContainer.setOnRefreshListener {
            mainViewModel.clearNews()
            checkForInternetConnection()
            mAdapter.notifyDataSetChanged()
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