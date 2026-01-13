package com.example.projecttest

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.projecttest.Cart.cartReset
import com.example.projecttest.retrofit.Order
import com.example.projecttest.retrofit.OrderService
import com.example.projecttest.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.activity_r_order_detail.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)


        val loadingView = findViewById<ImageView>(R.id.paymentLoadingIcon)
        loadingView.setImageResource(R.drawable.loading_icon2_24px)

        val rotateAnimation = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotateAnimation.duration = 2500 // 2.5초 동안 회전
        rotateAnimation.repeatCount = Animation.INFINITE // 무한 반복

        loadingView.startAnimation(rotateAnimation)

        val loadingText = findViewById<TextView>(R.id.loadingText)
        loadingText.text = "주문 처리 중..."

        Handler().postDelayed({
            //showOrderDoneScreen()
            val intent = Intent(this@PaymentActivity, WaitingActivity::class.java)
            startActivity(intent)
            finish()
        }, 2500) // 2.5초 후에 주문 완료 창으로 전환


        //Toast.makeText(this@PaymentActivity, "", Toast.LENGTH_SHORT).show()

        /*  //다른 Activity로 이동
        fun cartList_show() {
            for(i in 0..(Cart.num-1)) {
                var content = ""
                content += """
                    메뉴 : ${Cart.menu[i]}
                    가격 : ${Cart.price[i]} 
                    
                    
                """.trimIndent()
                //textViewPayment.append(content)
            }
            //textViewPayment.append("총액 : "+Cart.total_price.toString())
        }
        //cartList_show()

         */

        /*  //다른 Activity로 이동
        val OrderService = RetrofitClient.getClient()?.create(OrderService::class.java)

        var wait = 0
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
                }

                override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }

        //waiting(Cart.rest[Cart.num-1])

         */

        /*  //  -> orderActivity로 이동
        fun menuOrder() {    //메뉴 이름 & 식당 id & 사용자 이메일 넘겨주면 서버에 주문 db에 저장하는 함수   *장바구니에 담긴대로 주문
            for(i in 0..(Cart.num-1)) {
                val call = OrderService!!.postOrder(Cart.menu[i], Cart.rest[i], LoginUser.email)
                call.enqueue(object : Callback<JSONObject> {
                    override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                        if (!response.isSuccessful) {
                            return
                        }
                        val data = response.body()
                    }

                    override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                        Log.d("Debug", "onFailure 실행$t")
                    }
                })
            }
            Toast.makeText(this@PaymentActivity, "주문 완료! 대기자수 : $wait", Toast.LENGTH_SHORT).show()
            cartReset()
        }

         */
    }

}