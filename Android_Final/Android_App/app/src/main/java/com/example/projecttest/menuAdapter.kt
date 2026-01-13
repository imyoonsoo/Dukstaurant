package com.example.projecttest

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttest.retrofit.Nutrition
import com.example.projecttest.retrofit.NutritionService
import com.example.projecttest.retrofit.RetrofitClient
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Call


class menuAdapter (var mList: List<menuList>) :
    RecyclerView.Adapter<menuAdapter.menuViewHolder>() {

    inner class menuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val logo: ImageView = itemView.findViewById(R.id.menuPic)
        val menu: TextView = itemView.findViewById(R.id.menu)
        val menuDesc: TextView = itemView.findViewById(R.id.menuDesc)
        val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.constraintLayout)

        val price: TextView = itemView.findViewById(R.id.menuPrice)

        fun collapseExpandedView() {
            menuDesc.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): menuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item, parent, false)
        return menuViewHolder(view)
    }

    override fun onBindViewHolder(holder: menuViewHolder, position: Int) {

        val menu = mList[position]
        holder.logo.setImageResource(menu.menuPic)
        holder.menu.text = menu.menu
        //holder.menuDesc.text = menu.desc
        holder.price.text = menu.desc + " 원"
        val NutritionService = RetrofitClient.getClient()?.create(NutritionService::class.java)
        lateinit var nutrition_list : List<Nutrition>
        val call2 = NutritionService!!.getNutrition(menu.menu)  //메뉴 이름 넣어서 조회
        call2.enqueue(object : Callback<List<Nutrition>> {
            override fun onResponse(call: Call<List<Nutrition>>, response: Response<List<Nutrition>>) {
                if (!response.isSuccessful) {
                    return
                }
                nutrition_list = response.body()!!
                for (nutrition in nutrition_list) {
                    val kcal = nutrition.kcal.toInt().toString()+" Kcal"
                    holder.menuDesc.text = kcal
                }
            }
            override fun onFailure(call: Call<List<Nutrition>>, t: Throwable) {
                Log.d("Debug", "onFailure 실행$t")
            }
        })



        val isExpandable: Boolean = menu.isExpandable
        holder.menuDesc.visibility = if (isExpandable) View.VISIBLE else View.GONE

        holder.constraintLayout.setOnClickListener {
            isAnyItemExpanded(position)
            menu.isExpandable = !menu.isExpandable
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

    private fun isAnyItemExpanded(position: Int){
        val temp = mList.indexOfFirst {
            it.isExpandable
        }
        if (temp >= 0 && temp != position){
            mList[temp].isExpandable = false
            notifyItemChanged(temp , 0)
        }
    }

    override fun onBindViewHolder(
        holder: menuViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {

        if(payloads.isNotEmpty() && payloads[0] == 0){
            holder.collapseExpandedView()
        }else{
            super.onBindViewHolder(holder, position, payloads)

        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}


