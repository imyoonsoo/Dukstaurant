package com.example.projecttest

import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class cartAdapter (var cList: ArrayList<cartList>) :   
    RecyclerView.Adapter<cartAdapter.cartViewHolder>() {

    inner class cartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cartItemMenu:TextView = itemView.findViewById(R.id.cartMenuName)
        val cartItemPrice:TextView = itemView.findViewById<TextView>(R.id.cartMenuPrice)
        val cartItemCount:TextView = itemView.findViewById<TextView>(R.id.cartMenuCount)
        val increase:TextView = itemView.findViewById<Button>(R.id.increment)
        val decrease:TextView = itemView.findViewById<Button>(R.id.decrement)
        //val linearLayout: LinearLayout = itemView.findViewById(R.id.content2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return cartViewHolder(view)
    }

    override fun onBindViewHolder(holder: cartViewHolder, position: Int) {

        val selectedMenu = cList[position]
        holder.cartItemMenu.text = selectedMenu.menu
        holder.cartItemPrice.text = selectedMenu.menuPrice.toString()
        holder.cartItemCount.text = selectedMenu.menuCount.toString()
        holder.increase.setOnClickListener{
            cList[position].menuCount++
            notifyDataSetChanged()
        }

        holder.decrease.setOnClickListener{
            if(cList[position].menuCount == 1){
                cList[position].menuCount--
                cList.removeAt(position)
            }
            else
                cList[position].menuCount--
            notifyDataSetChanged()
        }

        //holder.linearLayout.setOnClickListener {
        //    notifyItemChanged(position , Unit)
        //}
    }
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)

    }
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener

    }

    private lateinit var itemClickListener : OnItemClickListener

    override fun getItemCount(): Int {
        return cList.size
    }


    //카트 수량 업데이트 반영
    /*
    interface OnCartListChangfeListener{
        fun onCartListChanged()
    }
    private var onCartListChangefListener: OnCartListChangfeListener? = null
    fun setOnCartListChangeListener(listener: OnCartListChangfeListener){
        this.onCartListChangefListener = listener
    }

     */
}


