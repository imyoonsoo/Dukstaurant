package com.example.projecttest

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ReceivedGiftAdapter (private val dataList : List<ReceivedGiftData>) : RecyclerView.Adapter<ReceivedGiftAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receivedstoreImage = itemView.findViewById<ImageView>(R.id.storeImage)
        val giftSender = itemView.findViewById<TextView>(R.id.giftSender)
        val receivedMenu = itemView.findViewById<TextView>(R.id.receivedMenu)
        val giftUsingStatus = itemView.findViewById<Button>(R.id.receivedUse)
        val divider_receivedGift = itemView.findViewById<View>(R.id.divider_receivedGift)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_received_gift, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.giftSender.text = item.giftSender
        holder.receivedMenu.text = item.receivedMenu

        // 이미지 리소스를 ImageView에 설정
        holder.receivedstoreImage.setImageResource(item.receivedstoreImage)

        //마지막 항목이 아닌 경우 구분선을 표시
        if (position < dataList.size - 1) {
            holder.divider_receivedGift.visibility = View.VISIBLE
        } else {
            //마지막 항목인 경우 구분선을 숨김
            holder.divider_receivedGift.visibility = View.GONE
        }

        //선물하기 버튼 클릭 시
        holder.giftUsingStatus.setOnClickListener {
            itemClickListener?.onClick(it, position)

//            //선물하기 팝업 전용 xml을 가져와서 AlertDialog 생성
//            val alertDialog = AlertDialog.Builder(holder.itemView.context).create()
//            val dialogView = LayoutInflater.from(holder.itemView.context).inflate(R.layout.check_giftusing_design, null)
//
//            // "아니오" 버튼 클릭 시
//            val usingNoButton_gift = dialogView.findViewById<Button>(R.id.noButton_usingGift)
//            usingNoButton_gift.setOnClickListener {
//                Toast.makeText(holder.itemView.context, "선물 사용 취소", Toast.LENGTH_SHORT).show()
//                // 다이얼로그를 닫음
//                alertDialog.dismiss()
//            }
//
//            //"예" 버튼 클릭 시
//            val usingYesButton_gift = dialogView.findViewById<Button>(R.id.yesButton_usingGift)
//            usingYesButton_gift.setOnClickListener {
//                Toast.makeText(holder.itemView.context, "선물 사용 완료!\n 주문 확인 창으로 이동 중...", Toast.LENGTH_SHORT).show()
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