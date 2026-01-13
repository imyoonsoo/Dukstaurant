package com.example.projecttest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttest.NoticeData
import com.example.projecttest.R
import com.google.android.material.card.MaterialCardView

class NoticeAdapter(private val dataList: List<NoticeData>) : RecyclerView.Adapter<NoticeAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView = itemView.findViewById<MaterialCardView>(R.id.recyclerView)
        val storeImage = itemView.findViewById<ImageView>(R.id.storeImage)
        val noticeTitle = itemView.findViewById<TextView>(R.id.noticeTitle)
        val noticeMessage = itemView.findViewById<TextView>(R.id.noticeMessage)
        val timeAgo = itemView.findViewById<TextView>(R.id.timeAgo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.noticeTitle.text = item.noticeTitle
        holder.noticeMessage.text = item.noticeContent
        holder.timeAgo.text = item.timeAgo

        // 이미지 리소스를 ImageView에 설정
        holder.storeImage.setImageResource(item.storeImage)
    }

    override fun getItemCount() = dataList.size

}


