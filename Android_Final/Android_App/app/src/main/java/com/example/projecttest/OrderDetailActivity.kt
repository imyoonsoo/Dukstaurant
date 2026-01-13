package com.example.projecttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttest.retrofit.Order
import com.example.projecttest.retrofit.OrderService
import com.example.projecttest.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_order_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderDetailActivity : AppCompatActivity() {
    lateinit var backButton : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        val orderedDataList = mutableListOf<OrderedListData>()
        //어댑터 생성 및 연결
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this@OrderDetailActivity)
        recyclerView.adapter = OrderedListAdapter(orderedDataList)
        // 어댑터 인스턴스 생성
        val adapter = OrderedListAdapter(orderedDataList)
        recyclerView.adapter = adapter

        val OrderService = RetrofitClient.getClient()?.create(OrderService::class.java)

        fun uOrder_show() {   //email 보내고 해당 유저의 주문내역 불러오기
            lateinit var order_list: List<Order>

            val call = OrderService!!.getUOrder(LoginUser.email)
            call.enqueue(object : Callback<List<Order>> {
                override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    order_list = response.body()!!
                    /*
                    for (order in order_list) {
                        var content = ""
                        content += """
                            ${order.menu}
                            ${order.time}
                            
                            
                        """.trimIndent()
                        //textViewOrderDetail.append(content)
                    }

                     */

                    for(order in order_list) {
                        orderedDataList.add(
                            OrderedListData(
                                res_idToImage(order.restaurant),
                                "" + res_idToName(order.restaurant),
                                "주문 일시 : " + order.time,
                                "주문번호 : " + order.id.toString(),
                                "주문 메뉴 : " + order.menu,
                                "결제 금액 : " + menuToPrice(order.restaurant, order.menu),
                            )
                        )
                    }

                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }
        uOrder_show()

        /*
        button14.setOnClickListener {   //뒤로가기 버튼
            finish()
        }

         */
        //뒤로가기 버튼 클릭 시
        backButton = findViewById(R.id.backButton_orderedList)
        backButton.setOnClickListener{
            finish()
        }
    }
}