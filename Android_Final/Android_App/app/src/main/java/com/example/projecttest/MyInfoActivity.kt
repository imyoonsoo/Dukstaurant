package com.example.projecttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.projecttest.retrofit.*
import kotlinx.android.synthetic.main.activity_my_info.*
import kotlinx.android.synthetic.main.activity_order_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.concurrent.schedule

class MyInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_my_info)

        val orderStatusIcon = findViewById<ImageView>(R.id.orderstatusIcon)
        var orderStatusText = findViewById<TextView>(R.id.orderstatusText)

        orderStatusIcon.setImageResource(R.drawable.cooking)
        orderStatusText.setText("음식 준비 중...")
        ordeMenu.setText("")

        val UserService = RetrofitClient.getClient()?.create(UserService::class.java)

        fun info_show() {
            lateinit var info_list: List<User>

            val call = UserService!!.getUserInf(LoginUser.email)
            call.enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    info_list = response.body()!!
                    for (info in info_list) {
                        var content = ""
                        content += """
                            email : ${info.email}
                            nickname : ${info.nickname}
                            password : ${info.password}
                        """.trimIndent()
                        //textViewInfo.append(content)
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }
        info_show()

        var order_id: Int = 0
        var order_date: String
        val OrderService = RetrofitClient.getClient()?.create(OrderService::class.java)
        val localDate: LocalDate = LocalDate.now()
        //Log.d("localDate",localDate.toString())
        //Toast.makeText(this@MyInfoActivity, localDate.toString(), Toast.LENGTH_SHORT).show()


        //알림 기능
        //fun uOrder_show() {   //email 보내고 해당 유저의 주문내역 불러오기
        lateinit var order_list: List<Order>

        val call = OrderService!!.getUOrder(LoginUser.email)
        call.enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (!response.isSuccessful) {
                    return
                }
                order_list = response.body()!!
                if(order_list.isEmpty()){
                    orderStatusText.setText("주문한 음식이 없습니다...")
                }
                else {
                    var length = order_list.size
                    order_id = order_list[length - 1].id //주문내역 중에 가장 최근 것만 불러오기
                    order_date =  order_list[length - 1].time
                    var order_id_str = order_id.toString()
                    if(order_date != localDate.toString()){     /////////////////////////
                        orderStatusText.setText("주문한 음식이 없습니다...")
                    }
                    else {
                        //order_id 확인 Toast
                        //Toast.makeText(this@MyInfoActivity, order_id_str, Toast.LENGTH_SHORT).show()
                        val timer = Timer()
                        val task = object : TimerTask() {
                            override fun run() {
                                val call2 = OrderService!!.getDone(order_id)
                                lateinit var order_list2: List<Order>
                                //println("running a job: " + LocalDateTime.now())
                                call2.enqueue(object : Callback<List<Order>> {
                                    override fun onResponse(
                                        call: Call<List<Order>>,
                                        response: Response<List<Order>>
                                    ) {
                                        if (!response.isSuccessful) {
                                            return
                                        }
                                        order_list2 = response.body()!!

                                        //var text = order_list[0].done.toString()
                                        //Toast.makeText(this@MyInfoActivity, text, Toast.LENGTH_SHORT).show()
                                        for (order in order_list2) {
                                            if (order.id == order_id) {
                                                if (order.done == true) {
                                                    //alarmText.setText("주문한 메뉴가 완료되었습니다")
                                                    orderStatusIcon.setImageResource(R.drawable.meal)
                                                    ordeMenu.setText(order.menu)
                                                    orderStatusText.setText("음식 조리 완료...")
                                                    timer.cancel()  //주문한 음식 완료되면 멈추기
                                                    return
                                                } else {
                                                    orderStatusIcon.setImageResource(R.drawable.cooking)
                                                    ordeMenu.setText(order.menu)
                                                    orderStatusText.setText("음식 준비 중...")
                                                    //Toast.makeText(this@MyInfoActivity, "done : false", Toast.LENGTH_SHORT).show()
                                                    return
                                                }
                                            }
                                        }
                                    }

                                    override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                                        //Toast.makeText(this@MyInfoActivity, "onFailure 실행", Toast.LENGTH_SHORT).show()
                                    }
                                })
                            }
                        }
                        timer.scheduleAtFixedRate(task, 0, 7000)  //7초 마다
                    }
                }
                /*
                button_back5.setOnClickListener { //뒤로가기 버튼
                    timer.cancel()   //알림창에서 나가면 멈추기
                    finish()
                }

                 */
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Log.d("Debug", "onFailure 실행$t")
            }


        })
        val backButton : ImageButton = findViewById(R.id.backButton_gift)
        backButton.setOnClickListener {
            finish()
        }

        /*
        button_back5.setOnClickListener { //뒤로가기 버튼
            finish()
        }

         */

    }
}