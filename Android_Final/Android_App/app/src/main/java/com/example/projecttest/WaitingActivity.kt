package com.example.projecttest

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttest.retrofit.Order
import com.example.projecttest.retrofit.OrderService
import com.example.projecttest.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_waiting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WaitingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting)

        //setContentView(R.layout.completeIcon)
        val orderDoneIcon = findViewById<ImageView>(R.id.completeIcon)
        orderDoneIcon.setImageResource(R.drawable.tray_img_32px)

        // 이미지에 색상 지정
        val colorFilter = PorterDuffColorFilter(
            ContextCompat.getColor(this, R.color.burgundy), // 원하는 색상 리소스를 지정
            PorterDuff.Mode.SRC_ATOP
        )
        orderDoneIcon.colorFilter = colorFilter

        val OrderService = RetrofitClient.getClient()?.create(OrderService::class.java)

        var last_order_res : Int = 0
        fun uOrder_show() {   //email 보내고 해당 유저의 주문내역 불러오기
            lateinit var order_list: List<Order>

            val call = OrderService!!.getUOrder(LoginUser.email)
            call.enqueue(object : Callback<List<Order>> {
                override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    order_list = response.body()!!
                    last_order_res = order_list.last().restaurant
                    //Toast.makeText(this@WaitingActivity, "마지막 식당" +  order_list.last().restaurant.toString(), Toast.LENGTH_SHORT).show()

                    var wait = -1  //자신의 주문빼고 남은 대기자수 보여주기
                    fun waiting(restaurant_num : Int) {     //대기자수 보여주는 함수
                        lateinit var order_list: List<Order>

                        val call = OrderService!!.getROrder(restaurant_num)
                        call.enqueue(object : Callback<List<Order>> {
                            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                                if (!response.isSuccessful) {
                                    return
                                }
                                order_list = response.body()!!
                                for (order in order_list) {
                                    if(!order.done) {
                                        wait++
                                    }
                                }
                                checkWaitingNumber.setText("대기자수 : " + wait.toString())

                                //Toast.makeText(this@WaitingActivity, "대기자 수 "+wait.toString(), Toast.LENGTH_SHORT).show()
                            }

                            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                                Log.d("Debug", "onFailure 실행$t")
                            }
                        })
                    }
                    waiting(last_order_res)
                }

                override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }
       uOrder_show()

        homeButton.setOnClickListener {
            val intent = Intent(this@WaitingActivity, OrderActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}