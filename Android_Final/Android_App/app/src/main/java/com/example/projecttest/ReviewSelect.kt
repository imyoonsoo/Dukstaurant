package com.example.projecttest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttest.retrofit.Order
import com.example.projecttest.retrofit.OrderService
import com.example.projecttest.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_review_select.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewSelect : AppCompatActivity() {
    lateinit var backButton : ImageButton
    private var dataList   = ArrayList<ReviewManagementData>()
    private lateinit var adapter: ReviewManagementAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_select)

        userNickname.text = LoginUser.nickname

        // 어댑터 생성 및 연결
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this@ReviewSelect)
        adapter = ReviewManagementAdapter(dataList)
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
                    var res_name = ""
                    var res_img = R.drawable.tteokbokki_img_radius
                    order_list = response.body()!!
                    for (order in order_list) {
                        res_name = res_idToName(order.restaurant)
                        res_img = res_idToImage(order.restaurant)
                        dataList.add(ReviewManagementData(res_img, order.time,
                            res_name, order.id.toString(), order.menu))
                    }
                    adapter.notifyDataSetChanged()

                    adapter.setItemClickListener(object : ReviewManagementAdapter.OnItemClickListener {
                        override fun onClick(v: View, position: Int) {
                            val dataWriting = dataList[position]
                            //Toast.makeText(v.context, "${dataList[position].orderedNumber}", Toast.LENGTH_SHORT).show()
                            //Toast.makeText(this@ReviewSelect, "자세한 리뷰 작성 창으로 이동 중...", Toast.LENGTH_SHORT).show()

                            // 아이템 클릭 시 해당 아이템 데이터를 WritingActivity로 전달
                            val intent = Intent(this@ReviewSelect, ReviewWriteActivity::class.java)
                            intent.putExtra("storeImage", dataWriting.storeImage)
                            intent.putExtra("orderedStore", dataWriting.orderedStore)
                            intent.putExtra("orderedMenu", dataWriting.orderedMenu)
                            intent.putExtra("orderId", dataWriting.orderedNumber.toInt())
                            startActivity(intent)
                        }
                    })
                }
                override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }
        uOrder_show()

        //뒤로가기 클릭 시
        backButton = findViewById(R.id.backButton_main)
        backButton.setOnClickListener {
            finish()
        }

    }
}