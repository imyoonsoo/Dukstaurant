package com.example.projecttest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class ReviewAdapter(private val dataList: List<ReviewData>) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val cardView = itemView.findViewById<MaterialCardView>(R.id.recyclerView_re)
        val profileImage = itemView.findViewById<ImageView>(R.id.profileImage)
        val reviewTitle = itemView.findViewById<TextView>(R.id.reviewTitle)
        val reviewContent = itemView.findViewById<TextView>(R.id.reviewContent)
        //val timeAgo = itemView.findViewById<TextView>(R.id.timeAgo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_review, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.reviewTitle.text = item.noticeTitle
        holder.reviewContent.text = item.noticeContent
        //holder.timeAgo.text = item.timeAgo

        // 이미지 리소스를 ImageView에 설정
        holder.profileImage.setImageResource(item.storeImage)
    }

    override fun getItemCount() = dataList.size


    fun change() {
        //dataList.add(newItem)
        notifyDataSetChanged() // 데이터가 변경되었음을 어댑터에 알립니다.
    }
}
