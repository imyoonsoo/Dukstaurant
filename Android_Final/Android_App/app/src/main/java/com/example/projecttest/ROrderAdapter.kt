package com.example.projecttest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ROrderAdapter(private val dataList : List<ROrderData>) : RecyclerView.Adapter<ROrderAdapter.ViewHolder>() {

    //val menu = mList[position]

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderedDate_detail = itemView.findViewById<TextView>(R.id.orderedDate_datail)
        val orderedNumber = itemView.findViewById<TextView>(R.id.orderedNumber)
        val orderedMenu = itemView.findViewById<TextView>(R.id.orderedMenu)
        val cookDoneButton= itemView.findViewById<Button>(R.id.cookDone)
        val divider = itemView.findViewById<View>(R.id.divider) // 구분선 추가
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_r_order, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.orderedDate_detail.text = item.orderedDate_deatail
        holder.orderedNumber.text = item.orderedNumber
        holder.orderedMenu.text = item.orderedMenu
        val isExpandable: Boolean = item.isExpandable

        // 마지막 항목이 아닌 경우 구분선을 표시
        if (position < dataList.size - 1) {
            holder.divider.visibility = View.VISIBLE
        } else {
            // 마지막 항목인 경우 구분선을 숨김
            holder.divider.visibility = View.GONE
        }

        // 조리 완료 버튼 클릭 이벤트 처리
        holder.cookDoneButton.setOnClickListener {
            var data = dataList[position]
            //Toast.makeText(holder.itemView.context, "조리 완료되었습니다!", Toast.LENGTH_SHORT).show()

            isAnyItemExpanded(position)
            data.isExpandable = !data.isExpandable
            notifyItemChanged(position , Unit)
            itemClickListener.onClick(it, position)
        }
    }
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    private lateinit var itemClickListener : OnItemClickListener



    override fun getItemCount() = dataList.size

    private fun isAnyItemExpanded(position: Int){
        val temp = dataList.indexOfFirst {
            it.isExpandable
        }
        if (temp >= 0 && temp != position){
            dataList[temp].isExpandable = false
            notifyItemChanged(temp , 0)
        }
    }
}