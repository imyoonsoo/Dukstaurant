package com.example.projecttest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class OrderedListAdapter(private val dataList : List<OrderedListData>) : RecyclerView.Adapter<OrderedListAdapter.ViewHolder>() {
    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView = itemView.findViewById<MaterialCardView>(R.id.recyclerView)
        val storeImage = itemView.findViewById<ImageView>(R.id.storeImage)
        val orderedStore = itemView.findViewById<TextView>(R.id.orderedStore)
        val orderedDate_detail = itemView.findViewById<TextView>(R.id.orderedDate_datail)
        val orderedNumber = itemView.findViewById<TextView>(R.id.orderedNumber)
        val orderedMenu = itemView.findViewById<TextView>(R.id.orderedMenu)
        val orderedMoney = itemView.findViewById<TextView>(R.id.orderedMoney)
        val divider = itemView.findViewById<View>(R.id.divider) // 구분선 추가
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_orderedlist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.orderedStore.text = item.orderedStore
        holder.orderedDate_detail.text = item.orderedDate_deatail
        holder.orderedNumber.text = item.orderedNumber
        holder.orderedMenu.text = item.orderedMenu
        holder.orderedMoney.text = item.orderedMoney

        // 이미지 리소스를 ImageView에 설정
        holder.storeImage.setImageResource(item.storeImage)

        // 마지막 항목이 아닌 경우 구분선을 표시
        if (position < dataList.size - 1) {
            holder.divider.visibility = View.VISIBLE
        } else {
            // 마지막 항목인 경우 구분선을 숨김
            holder.divider.visibility = View.GONE
        }
    }
    override fun getItemCount() = dataList.size
}