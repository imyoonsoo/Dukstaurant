package com.example.projecttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttest.LoginUser.Companion.set_restaurant_num
import com.example.projecttest.retrofit.Order
import com.example.projecttest.retrofit.OrderService
import com.example.projecttest.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_r_order_detail.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ROrderDetailActivity : AppCompatActivity() {
    lateinit var backButton2 : ImageView
    private var dataList   = ArrayList<ROrderData>()
    private lateinit var adapter: ROrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_r_order_detail)

        //어댑터 생성 및 연결
        val recyclerView: RecyclerView = findViewById(R.id.rv)
        recyclerView.layoutManager = LinearLayoutManager(this@ROrderDetailActivity)
        adapter = ROrderAdapter(dataList)
        recyclerView.adapter = adapter

        val OrderService = RetrofitClient.getClient()?.create(OrderService::class.java)

        fun rOrder_show(restaurant_num : Int) {
            lateinit var order_list: List<Order>

            val call = OrderService!!.getROrder(restaurant_num)
            call.enqueue(object : Callback<List<Order>> {
                override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    order_list = response.body()!!

                    for (order in order_list) {
                        dataList.add(ROrderData("주문 일시 : " + order.time, "주문 번호 : " +order.id,"주문 메뉴 : " + order.menu))
                    }
                    adapter.notifyDataSetChanged()
                    //

                    var id_str : String
                    var id_str_sub : String
                    var done_order_id : Int = 0



                    fun doneTrue(order_id : Int) {
                        //버튼 눌렀을 때 주문 번호 얻어오기
                        var done_menu = ""
                        var done_res = 0
                        var done_cus = ""
                        for (order in order_list) {
                            if(order.id == done_order_id){
                                done_menu = order.menu
                                done_res = order.restaurant
                                done_cus = order.customer
                            }
                        }
                        val call = OrderService!!.setDone(done_order_id, done_menu, done_res, done_cus,true)     //DB에 id가 13번인 order가 있어야 동작 가능
                        call.enqueue(object : Callback<JSONObject> {
                            override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                                if (!response.isSuccessful) {
                                    return
                                }
                                var data = response.body()!!
                                dataList.clear()
                                rOrder_show(LoginUser.restaurant_num)
                            }

                            override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                                Log.d("Debug", "onFailure 실행$t")
                            }
                        })
                    }

                    adapter.setItemClickListener(object : ROrderAdapter.OnItemClickListener {
                        override fun onClick(v: View, position: Int) {
                            //Toast.makeText(v.context, "${dataList[position].orderedNumber} 완료", Toast.LENGTH_SHORT).show()
                            id_str = dataList[position].orderedNumber
                            id_str_sub = id_str.substring(8)
                            done_order_id = id_str_sub.toInt()
                            doneTrue(done_order_id)

                        }
                    })
                }

                override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }


        var restaurant_input = LoginUser.restaurant_num  //restaurant_num -> set_restaurant_num()해주고 사용 -> 로그인, 회원가입할 때 함수 호출
        rOrder_show(restaurant_input)

        backButton2 = findViewById(R.id.backButton2)
        backButton2.setOnClickListener{
            finish()
        }
    }
}