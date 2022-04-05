package com.example.webviewtest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.webviewtest.R
import com.example.webviewtest.databinding.ItemNoticeBinding
import com.example.webviewtest.domain.model.Notice
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class NoticeListAdapter(private var listener: OnClickListener) :
    ListAdapter<Notice, NoticeListAdapter.ViewHolder>(NewsDiffCallback()) {
    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_notice, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notice = getItem(position)
        if (notice.active!!) {
            with(holder) {
                setListener(notice)
                binding.tvTitle.text = notice.title
                binding.tvAuthor.text = notice.author
                notice.createdAt?.let {
                    binding.tvTimeAgo.text = convertTime(it)
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertTime(time: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val emptyString = ""
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        return try {
            val mTime: Long = sdf.parse(time).time
            val now = System.currentTimeMillis()
            val ago = DateUtils.getRelativeTimeSpanString(mTime, now, DateUtils.MINUTE_IN_MILLIS)
            ago as String
        } catch (e: ParseException) {
            e.printStackTrace()
            emptyString
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemNoticeBinding.bind(view)

        fun setListener(notice: Notice) {
            with(binding.root) {
                setOnClickListener { listener.onClick(notice) }
            }
        }
    }

    override fun getItemViewType(position: Int): Int = position

    class NewsDiffCallback : DiffUtil.ItemCallback<Notice>() {
        override fun areItemsTheSame(oldItem: Notice, newItem: Notice): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Notice, newItem: Notice): Boolean {
            return oldItem == newItem
        }

    }
}