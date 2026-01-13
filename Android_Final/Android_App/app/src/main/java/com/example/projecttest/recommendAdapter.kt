package com.example.projecttest

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class recommendAdapter (var mList: List<menuList>) :
    RecyclerView.Adapter<recommendAdapter.recViewHolder>() {

        inner class recViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val logo: ImageView = itemView.findViewById(R.id.menuPic)
            val menu: TextView = itemView.findViewById(R.id.menu)
            val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.constraintLayout)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.recommend_item, parent, false)
            return recViewHolder(view)
        }

        override fun onBindViewHolder(holder: recViewHolder, position: Int) {
            val menuList = mList[position]
            holder.logo.setImageResource(menuList.menuPic)
            holder.menu.text = menuList.menu

        }


        override fun getItemCount(): Int {
            return mList.size
        }

    }


