package com.example.projecttest

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class GiftAdapter (private val dataList : List<GiftData>) : RecyclerView.Adapter<GiftAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val giftstoreImage = itemView.findViewById<ImageView>(R.id.giftstoreImage)
        val giftMenu = itemView.findViewById<TextView>(R.id.giftMenu)
        val giftPrice = itemView.findViewById<TextView>(R.id.giftMenuPrice)
        val giftPayStatus = itemView.findViewById<Button>(R.id.giftPay)
        val divider_Gift = itemView.findViewById<View>(R.id.divider_Gift)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiftAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_gift, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: GiftAdapter.ViewHolder, position: Int) {
        val item = dataList[position]
        holder.giftMenu.text = item.giftMenu
        holder.giftPrice.text = item.giftPrice
        // 이미지 리소스를 ImageView에 설정
        holder.giftstoreImage.setImageResource(item.giftstoreImage)

        //마지막 항목이 아닌 경우 구분선을 표시
        if (position < dataList.size - 1) {
            holder.divider_Gift.visibility = View.VISIBLE
        } else {
            //마지막 항목인 경우 구분선을 숨김
            holder.divider_Gift.visibility = View.GONE
        }

        //선물하기 버튼 클릭 시
        holder.giftPayStatus.setOnClickListener {
            itemClickListener?.onClick(it, position)

//            //전용 xml을 가져와서 AlertDialog 생성
//            val alertDialog = AlertDialog.Builder(holder.itemView.context).create()
//            val dialogView = LayoutInflater.from(holder.itemView.context).inflate(R.layout.check_giftpay_design, null)
//
//            // "아니오" 버튼 클릭 시
//            val NoButton_giftPay = dialogView.findViewById<Button>(R.id.noButton_GiftPay)
//            NoButton_giftPay.setOnClickListener {
//                Toast.makeText(holder.itemView.context, "선물 결제하기 취소", Toast.LENGTH_SHORT).show()
//                // 다이얼로그를 닫음
//                alertDialog.dismiss()
//            }
//
//            //"예" 버튼 클릭 시
//            val YesButton_giftPay = dialogView.findViewById<Button>(R.id.yesButton_GiftPay)
//            YesButton_giftPay.setOnClickListener {
//                Toast.makeText(holder.itemView.context, "선물 결제하기 완료!", Toast.LENGTH_SHORT).show()
//            }
//            alertDialog.setView(dialogView)
//            alertDialog.show()
        }
    }

    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun getItemCount() = dataList.size
}