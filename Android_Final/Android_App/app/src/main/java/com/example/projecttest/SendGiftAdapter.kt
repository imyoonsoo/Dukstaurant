package com.example.projecttest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

    class SendGiftAdapter (private val dataList : List<SendGiftData>) : RecyclerView.Adapter<SendGiftAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val sendstoreImage = itemView.findViewById<ImageView>(R.id.sendstoreImage)
            val sendTo = itemView.findViewById<TextView>(R.id.sendTo)
            val sendMenu = itemView.findViewById<TextView>(R.id.sendMenu)
            val divider_sendGift = itemView.findViewById<View>(R.id.divider_sendGift)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SendGiftAdapter.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_send_gift, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: SendGiftAdapter.ViewHolder, position: Int) {
            val item = dataList[position]
            holder.sendTo.text = item.sendTo
            holder.sendMenu.text = item.sendMenu

            // 이미지 리소스를 ImageView에 설정
            holder.sendstoreImage.setImageResource(item.sendstoreImage)

            //마지막 항목이 아닌 경우 구분선을 표시
            if (position < dataList.size - 1) {
                holder.divider_sendGift.visibility = View.VISIBLE
            } else {
                //마지막 항목인 경우 구분선을 숨김
                holder.divider_sendGift.visibility = View.GONE
            }
        }
        override fun getItemCount() = dataList.size

        fun change() {
            notifyDataSetChanged() // 데이터가 변경되었음을 어댑터에 알립니다.
        }
    }