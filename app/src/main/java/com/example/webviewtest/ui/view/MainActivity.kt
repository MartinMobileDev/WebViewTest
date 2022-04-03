package com.example.webviewtest.ui.view

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.webviewtest.R
import com.example.webviewtest.adapter.NoticeListAdapter
import com.example.webviewtest.adapter.OnClickListener
import com.example.webviewtest.databinding.ActivityMainBinding
import com.example.webviewtest.data.model.Notice
import com.example.webviewtest.ui.viewModel.MainViewModel
import com.example.webviewtest.utils.SwipeGesture
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mlinearLayoutManager: LinearLayoutManager
    private lateinit var madapter: NoticeListAdapter
    private val mainViewModel: MainViewModel by viewModels()
    private val swipeGesture = object : SwipeGesture(this@MainActivity) {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            when (direction) {
                ItemTouchHelper.LEFT -> {
                    val notice = madapter.currentList[viewHolder.adapterPosition]

                    //creating new list from previous one
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
        mainViewModel.onCreate()
        initRecyclerView()
        setupViewModel()
    }

    private fun setupViewModel() {
        mainViewModel.isLoading.observe(this, Observer {
            binding.progress.isVisible = it
        })
        mainViewModel.noticeListModel.observe(this, Observer { noticeList ->
            madapter.submitList(noticeList)
        })
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
            mainViewModel.onCreate()
            madapter.notifyDataSetChanged()
            binding.swipeContainer.isRefreshing = false
        }
        //ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.rvNews)
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(binding.rvNews)
    }


/*    private val itemTouchHelperCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Log.d(TAG, "onSwiped: placesList ${madapter.currentList.size}")

                val notice = madapter.currentList[viewHolder.adapterPosition]

                //creating new list from previous one
                val list = ArrayList<Notice>(madapter.currentList)
                list.remove(notice)
                madapter.submitList(list)
            }
        }*/

    override fun onClick(notice: Notice) {
        Intent(this, WebViewActivity::class.java).also {
            it.putExtra("HTML_TEXT", notice.webViewText)
            startActivity(it)
        }
    }
}