package com.example.projecttest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReviewManagementAdapter(private val dataList: List<ReviewManagementData>) : RecyclerView.Adapter<ReviewManagementAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val storeImage = itemView.findViewById<ImageView>(R.id.storeImage)
        val orderedDate = itemView.findViewById<TextView>(R.id.orderedDate)
        val orderedStore = itemView.findViewById<TextView>(R.id.orderedStore)
        val orderedNumber = itemView.findViewById<TextView>(R.id.orderedNumber)
        val orderedMenu = itemView.findViewById<TextView>(R.id.orderedMenu)
        val reviewWrite = itemView.findViewById<Button>(R.id.reviewWirte)
        val divider = itemView.findViewById<View>(R.id.divider) // 구분선 추가
    }

    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_reviewmanagement, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.orderedDate.text = "주문 날짜 : " + item.orderedDate
        holder.orderedStore.text = item.orderedStore
        holder.orderedMenu.text = "주문 메뉴 : " + item.orderedMenu
        holder.orderedNumber.text = "주문 번호 : " + item.orderedNumber

        // 이미지 리소스를 ImageView에 설정
        holder.storeImage.setImageResource(item.storeImage)

        // 자세한 리뷰 작성하기 클릭 시 이벤트 처리
        holder.reviewWrite.setOnClickListener {
            itemClickListener?.onClick(it, position)
        }

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
